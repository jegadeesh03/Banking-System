package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileLogger {

    // Thread-safe queue
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();

    private static final String LOG_DIR = "data";
    private static final String LOG_FILE = "data/audit.log";

    static {
        // 🔹 Ensure log directory exists
        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 🔹 Background logger thread
        Thread loggerThread = new Thread(() -> {
            while (true) {
                try {
                    String log = logQueue.take(); // wait until log available
                    writeToFile(log);
                } catch (InterruptedException e) {
                    // flush remaining logs before exit
                    flushRemainingLogs();
                    break;
                }
            }
        }, "LoggerThread");

        loggerThread.setDaemon(true); // background daemon
        loggerThread.start();
    }

    // 🔹 Public API
    public static void log(String user, String action, String status) {
        String timestamp = LocalDateTime.now().toString();
        String logLine = "[" + timestamp + "] USER=" + user
                + " | ACTION=" + action
                + " | STATUS=" + status;

        logQueue.offer(logLine);
    }

    // 🔹 Write single log
    private static void writeToFile(String logLine) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(logLine + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Flush queue on shutdown
    private static void flushRemainingLogs() {
        while (!logQueue.isEmpty()) {
            writeToFile(logQueue.poll());
        }
    }
}
