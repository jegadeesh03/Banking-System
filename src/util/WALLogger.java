package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WALLogger {

    private static final String WAL_DIR  = "wal";
    private static final String WAL_FILE = "wal/wal.log";

    public static synchronized void write(
            int from, int to, double amt, String status) {

        try {
            File dir = new File(WAL_DIR);

            // 📁 Folder create message
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("📁 WAL directory created");
            }

            File file = new File(WAL_FILE);

            // 📄 File create message
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("📄 WAL log file created: wal/wal.log");
            }

            // ✍ Write log
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(System.currentTimeMillis() + "," +
                         from + "," + to + "," + amt + "," + status +
                         System.lineSeparator());
            }

            // ✅ Success message
            System.out.println("📝 WAL log written: " + status);

        } catch (IOException e) {
            System.out.println("❌ WAL logging failed");
            e.printStackTrace();
        }
    }
}
