package com.amex.scheduler;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandExecutor {
    private static final String LOG_FILE = "C:\\\\Users\\\\S-TECH\\\\Documents\\\\workspace-amex\\\\ScheduledCommands\\\\tmp\\\\command_output.log";

    public static void execute(String command) {
        try {
            // Ensure the directory exists
            File logDir = new File("C:\\\\Users\\\\S-TECH\\\\Documents\\\\workspace-amex\\\\ScheduledCommands\\\\tmp\\command_output.log");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            System.out.println("able to read output file");

            File logFile = new File(LOG_FILE);
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true))
            ) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                logWriter.write("\n[" + timestamp + "] Executing: " + command + "\n");

                String line;
                while ((line = reader.readLine()) != null) {
                    logWriter.write(line + "\n");
                    System.out.println("[OUTPUT] " + line);
                }

                logWriter.flush();
                process.waitFor();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
