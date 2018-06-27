import java.util.Date;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Dailyprogrammer: 2 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pjsdx/difficult_challenge_2/
 */
public class Difficult {

    private static final String START_CMD = "start";
    private static final String STOP_CMD = "stop";
    private static final String LAP_CMD = "lap";
    private static final String LAST_CMD = "last";

    private static final int MILLIS_IN_SECOND = 1000;
    private static final int MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    private static final int MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60;
    private static final int MILLIS_IN_DAY = MILLIS_IN_HOUR * 24;

    private static final String LOG_FILE = "log.txt";

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        Scanner scanner = new Scanner(System.in);
        String log = "";;
        while (true) {
            String cmd = scanner.next();
            if (START_CMD.equals(cmd)) {
                stopwatch.start();
                log = printAndAddToLog("Started: " + stopwatch.getStartDate(), log);
            } else if (STOP_CMD.equals(cmd)) {
                stopwatch.stop();
                writeLog(log);
                break;
            } else if (LAP_CMD.equals(cmd)) {
                log = printAndAddToLog("Lap: " + formatElapsed(stopwatch.lap()), log);
            } else if (LAST_CMD.equals(cmd)) {
                System.out.print(readLog());
            } else {
                throw new IllegalArgumentException("Unknown cmd: " + cmd);
            }
        }
    }

    private static String printAndAddToLog(String msg, String log) {
        log += msg + System.getProperty("line.separator");
        System.out.println(msg);
        return log;
    }

    private static String readLog() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(LOG_FILE));
            String log = "";
            String line;
            String sep = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                log += line + sep;
            }

            return log;
        } catch (IOException e) {
            System.out.println("Can't read log");
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) { }
            }
        }
    }

    private static void writeLog(String log) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE));
            writer.write(log, 0, log.length());
        } catch (IOException e) {
            System.out.println("Can't write log");
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) { }
            }
        }
    }

    private static String formatElapsed(long elapsedMs) {
        long days = elapsedMs / MILLIS_IN_DAY;
        elapsedMs %= MILLIS_IN_DAY;

        long hours = elapsedMs / MILLIS_IN_HOUR;
        elapsedMs %= MILLIS_IN_HOUR;

        long minutes = elapsedMs / MILLIS_IN_MINUTE;
        elapsedMs %= MILLIS_IN_MINUTE;

        long seconds = elapsedMs / MILLIS_IN_SECOND;
        elapsedMs %= MILLIS_IN_SECOND;

        return String.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds);
    }

    private static class Stopwatch {

        private Date startDate;

        public boolean isStarted() {
            return startDate != null;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void start() {
            if (isStarted()) {
                throw new IllegalStateException("Already started");
            }

            startDate = new Date();
        }

        public void stop() {
            if (!isStarted()) {
                throw new IllegalStateException("Not started");
            }

            startDate = null;
        }

        public long lap() {
            if (!isStarted()) {
                throw new IllegalStateException("Not started");
            }

            Date currentDate = new Date();
            return currentDate.getTime() - startDate.getTime();
        }
    }
}