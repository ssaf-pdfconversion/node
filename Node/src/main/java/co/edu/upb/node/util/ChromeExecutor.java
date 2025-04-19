package co.edu.upb.node.util;
import co.edu.upb.node.domain.models.File;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

public class ChromeExecutor  {

    private final UrlPDFConverter converter;

    public ChromeExecutor(String chromePath) {
        this.converter = new UrlPDFConverter(chromePath);
    }



    public File execute(String url) throws Exception {
        Instant start = Instant.now();

        Path pdf = converter.convertUrlToPdfTemp(url);
        Instant end = Instant.now();

        byte[] pdfBytes = Files.readAllBytes(pdf);
        String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);


        Files.deleteIfExists(pdf);

        return new File(
                pdfBase64,
                (long) pdfBytes.length,
                Instant.now().toString(),
                2,
                url.replaceAll("[^a-zA-Z0-9]", "_") + ".pdf"
        );
    }
    
   
}
