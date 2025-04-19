package co.edu.upb.node.util;

import co.edu.upb.node.domain.models.File;
import java.time.Instant;
import java.util.Base64;

public class OfficeExecutor {

    private final PdfConverter converter;
    public OfficeExecutor(String sofficePath) {
        this.converter = new PdfConverter(sofficePath);
    }

    public File execute(String base64Input, String originalFilename) throws Exception {
        Instant start = Instant.now();
        String pdfBase64 = converter.convertBase64ToPdfBase64(
                base64Input, originalFilename
        );
        Instant end = Instant.now();

        byte[] pdfBytes = Base64.getDecoder().decode(pdfBase64);
        return new File(
                pdfBase64,
                (long) pdfBytes.length,
                Instant.now().toString(),
                1,
                originalFilename
        );
    }


}
