package com.amex.scheduler;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommandScheduler {
    private final String commandFilePath;

    public CommandScheduler(String commandFilePath) {
        this.commandFilePath = commandFilePath;
    }

    public void start() {
        System.out.println("Scheduler started. Monitoring commands...");
        while (true) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(commandFilePath));
                LocalDateTime now = LocalDateTime.now();

                for (String line : lines) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;

                    if (line.startsWith("*/")) {
                        // Recurring command
                        String[] parts = line.split(" ", 2);
                        if (parts.length < 2) continue;
                        int interval = Integer.parseInt(parts[0].substring(2));
                        if (interval <= 0) continue;
                        if (now.getMinute() % interval == 0) {
                            String command = parts[1];
                            CommandExecutor.execute(command);
                        }
                    } else {
                        // One-time command
                        String[] parts = line.split(" ", 6);
                        if (parts.length < 6) continue;

                        int minute = Integer.parseInt(parts[0]);
                        int hour = Integer.parseInt(parts[1]);
                        int day = Integer.parseInt(parts[2]);
                        int month = Integer.parseInt(parts[3]);
                        int year = Integer.parseInt(parts[4]);
                        String command = parts[5];

                        if (now.getMinute() == minute &&
                            now.getHour() == hour &&
                            now.getDayOfMonth() == day &&
                            now.getMonthValue() == month &&
                            now.getYear() == year) {
                            CommandExecutor.execute(command);
                        }
                    }
                }

                // Wait until the start of the next minute
                Thread.sleep(60000 - (System.currentTimeMillis() % 60000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
