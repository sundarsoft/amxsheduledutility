package com.amex.scheduler;

   
  

        public class Main {
            public static void main(String[] args) {
                String commandFilePath = "C:\\\\Users\\\\S-TECH\\\\Documents\\\\workspace-amex\\\\ScheduledCommands\\\\tmp\\\\commands.txt";
                CommandScheduler scheduler = new CommandScheduler(commandFilePath);
                System.out.println("able to read input file");
                scheduler.start();
            }
        

    
}
