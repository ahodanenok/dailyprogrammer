import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Dailyprogrammer: 35 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/rr5rq/432012_challenge_35_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int commandIndex = -1;
        List<Command> commands = new ArrayList<Command>();
        List<String> strings = new ArrayList<String>();
        while (true) {
            System.out.println();
            System.out.print("Enter command ('A'dd, 'E'dit, 'D'elete, 'U'ndo, 'R'edo): ");
            if (!scanner.hasNext()) break;
            String cmd = scanner.next().trim().toUpperCase();
            if ("A".equals(cmd) || "E".equals(cmd) || "D".equals(cmd)) {
                Command command = null;
                if ("A".equals(cmd)) {
                    System.out.print("Enter text to add: ");
                    if (!scanner.hasNext()) break;
                    command = new AddCommand(scanner.next().trim());
                } else if ("E".equals(cmd)) {
                    System.out.print("Enter index to edit: ");
                    if (!scanner.hasNext()) break;
                    int index = Integer.parseInt(scanner.next().trim());
                    System.out.print("Enter text to edit: ");
                    if (!scanner.hasNext()) break;
                    String str = scanner.next().trim();
                    command = new EditCommand(index, str);
                } else if ("D".equals(cmd)) {
                    System.out.print("Enter index to delete: ");
                    if (!scanner.hasNext()) break;
                    command = new DeleteCommand(Integer.parseInt(scanner.next().trim()));
                } else {
                    throw new IllegalStateException("can't happen");
                }

                while (!commands.isEmpty() && commandIndex < commands.size()) {
                    commands.remove(commands.size() - 1);
                }

                command.run(strings);
                commands.add(command);
                commandIndex = commands.size();
            } else if ("U".equals(cmd)) {
                if (commandIndex > 0) {
                    commands.get(--commandIndex).undo(strings);
                }
            } else if ("R".equals(cmd)) {
                if (commandIndex < commands.size()) {
                    commands.get(commandIndex++).run(strings);
                }
            } else {
                System.out.println("Unknown command: " + cmd);
                continue;
            }

            if (strings.size() > 0) {
                for (String str : strings) {
                    System.out.println(str);
                }
            } else {
                System.out.println("-- empty --");
            }
        }
    }

    private interface Command {

        void run(List<String> strings);
        void undo(List<String> strings);
    }

    private static class AddCommand implements Command {

        private String str;

        AddCommand(String str) {
            this.str = str;
        }

        @Override
        public void run(List<String> strings) {
            strings.add(str);
        }

        @Override
        public void undo(List<String> strings) {
            strings.remove(strings.size() - 1);
        }
    }

    private static class EditCommand implements Command {

        private int index;
        private String newValue;
        private String oldValue;

        EditCommand(int index, String newValue) {
            this.index = index;
            this.newValue = newValue;
        }

        @Override
        public void run(List<String> strings) {
            oldValue = strings.get(index);
            strings.set(index, newValue);
        }

        @Override
        public void undo(List<String> strings) {
            strings.set(index, oldValue);
        }
    }

    private static class DeleteCommand implements Command {

        private int index;
        private String deletedValue;

        DeleteCommand(int index) {
            this.index = index;
        }

        @Override
        public void run(List<String> strings) {
            deletedValue = strings.remove(index);
        }

        @Override
        public void undo(List<String> strings) {
            strings.add(index, deletedValue);
        }
    }
}
