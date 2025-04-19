package co.edu.upb.node.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Comparator;
import java.util.UUID;

public class PdfConverter {
    private final String sofficePath;

    public PdfConverter(String sofficePath) {
        this.sofficePath = sofficePath;
    }

    public String convertBase64ToPdfBase64(String base64Input,
                                           String originalFilename)
            throws IOException, InterruptedException {

        Path tempDir = Files.createTempDirectory("pdfconv-");
        try {

            Path inputFile = tempDir.resolve(originalFilename);
            byte[] inputBytes = Base64.getDecoder().decode(base64Input);
            Files.write(inputFile, inputBytes, StandardOpenOption.CREATE);

            String profileDirUri = tempDir.toUri().toString();
            String uniqueProfile = profileDirUri + "LOProfile_" + UUID.randomUUID();

            ProcessBuilder pb = new ProcessBuilder(
                    sofficePath,
                    "-env:UserInstallation=" + uniqueProfile,
                    "--headless",
                    "--convert-to", "pdf",
                    "--outdir", tempDir.toString(),
                    inputFile.toString()
            );
            pb.redirectErrorStream(true);
            Process p = pb.start();
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException(
                        "LibreOffice exited with code " + exitCode);
            }

            String baseName = originalFilename.contains(".")
                    ? originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                    : originalFilename;
            Path pdfFile = tempDir.resolve(baseName+ ".pdf");
            byte[] pdfBytes = Files.readAllBytes(pdfFile);

            return Base64.getEncoder().encodeToString(pdfBytes);
        } finally {

            deleteRecursively(tempDir);
        }
    }
    private void deleteRecursively(Path path) throws IOException {
        if (Files.notExists(path)) return;

        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try { Files.deleteIfExists(p); }
                    catch (IOException e) {}
                });
    }
}