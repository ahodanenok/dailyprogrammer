import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Dailyprogrammer: 33 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/rl24e/3302012_challenge_33_easy/
 */
public class Easy {

    public static void main(String[] args) throws Exception {
        List<Question> questions = loadQuestions();
        Scanner scanner = new Scanner(System.in);
        while (questions.size() > 0) {
            Question question = questions.get((int) Math.floor(Math.random() * questions.size()));
            System.out.println(question.text);
            String answer = scanner.nextLine().trim();
            if (answer.equals("exit")) {
                break;
            } else if (!question.isCorrect(answer)) {
                System.out.println(String.format("Incorrect, correct answer is '%s'", question.answer));
            } else {
                System.out.println("Correct");
            }

            questions.remove(question);
            System.out.println();
        }
    }

    private static List<Question> loadQuestions() throws Exception {
        List<Question> questions = new ArrayList<Question>();

        BufferedReader reader = new BufferedReader(new FileReader("questions.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            questions.add(new Question(parts[0].trim(), parts[1].trim()));
        }
        reader.close();

        return questions;
    }

    private static class Question {

        private final String text;
        private final String answer;

        Question(String text, String answer) {
            this.text = text;
            this.answer = answer;
        }

        boolean isCorrect(String answer) {
            return this.answer.equals(answer);
        }
    }
}
