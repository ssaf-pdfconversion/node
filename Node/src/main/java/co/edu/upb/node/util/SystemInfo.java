package co.edu.upb.node.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
public class SystemInfo {


    public static String getMachineUUID() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        try {
            if (os.contains("win")) {
                return readWindowsUUID().orElse("<unknown‑uuid>");
            }
            else {
                return readLinuxUUID().orElse("<unknown‑uuid>");
            }
        } catch (Exception e) {
            return "<error‑retrieving‑uuid>";
        }
    }

    private static Optional<String> readWindowsUUID() throws IOException, InterruptedException {
        Process p = new ProcessBuilder("wmic", "csproduct", "get", "uuid")
                .redirectErrorStream(true)
                .start();
        try (BufferedReader r = new BufferedReader(new java.io.InputStreamReader(p.getInputStream()))) {
            p.waitFor();
            return r.lines()
                    .skip(1)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .findFirst();
        }
    }


    private static Optional<String> readLinuxUUID() throws IOException {

        Path etc = Path.of("/etc/machine-id");
        if (Files.isReadable(etc)) {
            String id = Files.readString(etc).trim();
            if (!id.isEmpty()) return Optional.of(id);
        }

        Path dmi = Path.of("/sys/class/dmi/id/product_uuid");
        if (Files.isReadable(dmi)) {
            String id = Files.readString(dmi).trim();
            if (!id.isEmpty()) return Optional.of(id);
        }
        return Optional.empty();
    }



}
