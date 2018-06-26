import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Scanner;

/**
 * Dailyprogrammer: 2 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pjbuj/intermediate_challenge_2/
 */
public class Intermediate {

    private static final String EXIT_MOVE = "exit";

    private static final String STATE_DEF = "@";
    private static final String DESCRIPTION_DEF = ">";
    private static final String TRANSITION_DEF = "=";
    private static final String MOVE_DEF = "-";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        State currentState = loadStory("test.txt");
        if (currentState == null) {
            System.out.println("Can't load story");
            System.exit(-1);
        }

        main: while (true) {
            System.out.println();
            System.out.println(currentState.description);
            if (currentState.transitions.isEmpty()) {
                break;
            }

            System.out.println();
            for (Transition t : currentState.transitions) {
                if (t.letter != null) {
                    System.out.format("(%s) %s%n", t.letter, t.description);
                }
            }

            input: while (true) {
                System.out.print(">");
                String move = scanner.next();
                if (EXIT_MOVE.equals(move)) {
                    break main;
                }

                for (Transition t : currentState.transitions) {
                    if (t.letter != null && t.letter.equals(move)) {
                        currentState = t.state;
                        break input;
                    }
                }

                System.out.println("There is no transition for: '" + move + "'");
            }

            for (Transition t : currentState.transitions) {
                if (t.letter == null) {
                    currentState = t.state;
                }
            }
        }

        System.exit(0);
    }

    private static State loadStory(String path) {
        Map<String, State> states = new LinkedHashMap<>();
        State state = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(STATE_DEF)) {
                    state = new State();
                    state.description = "";
                    state.transitions = new ArrayList<Transition>();
                    states.put(line.substring(STATE_DEF.length()).trim(), state);
                } else if (line.startsWith(DESCRIPTION_DEF)) {
                    state.description += line.substring(DESCRIPTION_DEF.length()).trim();
                } else if (line.startsWith(TRANSITION_DEF)) {
                    Transition t = new Transition();
                    t.stateName = line.substring(TRANSITION_DEF.length()).trim();
                    state.transitions.add(t);
                } else if (line.startsWith(MOVE_DEF)) {
                    int firstDash = line.indexOf('-', TRANSITION_DEF.length());
                    int secondDash = line.indexOf('-', firstDash + 1);
                    //System.out.println(line + ", firstDash=" + firstDash + ", secondDash=" + secondDash);
                    Transition t = new Transition();
                    t.letter = line.substring(TRANSITION_DEF.length(), firstDash).trim();
                    t.stateName = line.substring(firstDash + 1, secondDash).trim();
                    t.description = line.substring(secondDash + 1).trim();
                    state.transitions.add(t);
                    //System.out.println(line.substring(firstDash + 1, secondDash).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) { }
            }
        }

        for (State s : states.values()) {
            for (Transition t : s.transitions) {
                t.state = states.get(t.stateName);
            }
        }

        //System.out.println(states);
        return states.values().iterator().next();

        /*State s1 = new State();
        s1.description = "Praesent pretium, ex pretium congue pretium, orci lacus ullamcorper tellus, nec imperdiet tortor risus ac nulla. Nunc id leo vulputate, fringilla turpis a, vulputate metus.";
        s1.transitions = new ArrayList<>();

        State s2 = new State();
        s2.description = "End";
        s2.transitions = Collections.emptyList();

        Transition t1 = new Transition();
        t1.letter = "left";
        t1.description = "Sed elementum lobortis rhoncus. Nullam faucibus risus urna, quis tincidunt ipsum fermentum semper. Aenean cursus ultricies lacinia.";
        t1.state = s2;

        Transition t2 = new Transition();
        t2.letter = "abc";
        t2.description = "Phasellus ac iaculis magna.";
        t2.state = s2;

        s1.transitions.add(t1);
        s1.transitions.add(t2);

        return s1;*/
    }

    private static class State {

        private String description;
        private List<Transition> transitions;
    }

    private static class Transition {

        private String description;
        private String letter;
        private State state;
        private String stateName;

        @Override
        public String toString() {
            return String.format("(%s) %s %s", letter, stateName, description);
        }
    }
}
