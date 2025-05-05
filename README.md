1.Reads C:\tmp\commands.txt every minute.

For each line:

If format is */n, runs it if currentMinute % n == 0

If format is date-based, runs only if exact time matches

2. Executes command using cmd.exe

3. Appends result to C:\tmp\command_output.log