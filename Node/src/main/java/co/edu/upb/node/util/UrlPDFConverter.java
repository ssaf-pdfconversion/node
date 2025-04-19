package co.edu.upb.node.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UrlPDFConverter {

    private final String chromePath;


    public UrlPDFConverter(String chromePath) {
        this.chromePath = chromePath;
    }


    public void convertUrlToPdf(String url, String outputPdfPath)
            throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                chromePath,
                "--headless",
                "--disable-gpu",
                "--print-to-pdf=" + outputPdfPath,
                url
        );
        pb.redirectErrorStream(true);
        Process p = pb.start();
        int exit = p.waitFor();
        if (exit != 0) {
            throw new RuntimeException("Chrome exited with code " + exit);
        }
    }


    public Path convertUrlToPdfTemp(String url)
            throws IOException, InterruptedException {
        Path out = Files.createTempFile("urlpdf-", ".pdf");
        convertUrlToPdf(url, out.toString());
        return out;
    }




}
