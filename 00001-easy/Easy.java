import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Dailyprogrammer: 1 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pih8x/easy_challenge_1/
 */
public class Easy {

    private static File LOG_FILE = new File("log.txt");

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsageAndExit();
        }

        String cmd = args[0];
        if ("ask".equals(cmd)) {
            ask();
        } else if ("log".equals(cmd)) {
            log();
        } else {
            printUsageAndExit();
        }
    }

    private static void printUsageAndExit() {
        System.out.println("java Easy [ ask | log ]");
        System.exit(-1);
    }

    private static void ask() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Name:");
        String name = scanner.next();

        System.out.print("Years:");
        int years = scanner.nextInt();

        System.out.print("Username:");
        String userName = scanner.next();

        System.out.format("your name is %s, you are %d years old, and your username is %s", name, years, userName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
            writer.write(name, 0, name.length());
            writer.write(';');
            String yearsStr = years + "";
            writer.write(yearsStr, 0, yearsStr.length());
            writer.write(';');
            writer.write(userName, 0, userName.length());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Can't save data: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch(IOException e) { }
            }
        }
    }

    private static void log() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(LOG_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(";");
                System.out.print("name=" + scanner.next());
                System.out.print(", years=" + scanner.nextInt());
                System.out.print(", userName=" + scanner.next());
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Can't load data: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) { }
            }
        }
    }
}
