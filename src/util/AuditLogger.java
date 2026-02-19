package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditLogger {

    private static final String DIR = "data";
    private static final String FILE = "data/audit.log";

    public static void log(String user, String action, String status) {

        try {
            // Ensure directory exists
            File directory = new File(DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileWriter fw = new FileWriter(FILE, true);

            String logLine =
                    LocalDateTime.now() +
                    " | USER=" + user +
                    " | ACTION=" + action +
                    " | STATUS=" + status;

            fw.write(logLine + System.lineSeparator());
            fw.close();

        } catch (IOException e) {
            System.out.println("Audit logging failed!");
            e.printStackTrace();
        }
    }
}
