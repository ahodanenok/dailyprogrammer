import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 10 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pv92x/2182012_challenge_10_difficult/
 */
public class Difficult extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private HangmanGame game;

    private BorderPane root;
    private HBox openLettersBox;
    private HBox missedLettersBox;

    private Canvas canvas;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        showChooseWord();

        primaryStage.setTitle("Hangman Game");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void clearRoot() {
        root.setCenter(null);
        root.setLeft(null);
    }

    private void beginGame(String word) {
        this.game = new HangmanGame(word);

        clearRoot();

        canvas = new Canvas(250, 420);
        gc = canvas.getGraphicsContext2D();

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);

        pane.add(new Label("Word:"), 0, 0);
        openLettersBox = new HBox();
        openLettersBox.setSpacing(10);
        pane.add(openLettersBox, 1, 0);

        pane.add(new Label("Missed:"), 0, 1);
        missedLettersBox = new HBox();
        missedLettersBox.setSpacing(10);
        pane.add(missedLettersBox, 1, 1);

        final TextField letterInput = new TextField();

        Button guessLetterBtn = new Button("Guess");
        guessLetterBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String str = letterInput.getText();
                if (str == null || str.trim().length() != 1) {
                    showValidationErrorDialog("Please enter a letter to check");
                    Platform.runLater(new Runnable() {
                        public void run() {
                            letterInput.requestFocus();
                        }
                    });
                    return;
                }

                char letter = str.charAt(0);
                HangmanGame.GuessResult result = game.makeGuess(letter);
                if (result == HangmanGame.GuessResult.YES) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            letterInput.setText(null);
                            showGameInfo();
                        }
                    });

                    if (game.won()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Game over");
                        alert.setHeaderText("You won!");
                        alert.setContentText(String.format("The word is '%s'", game.getWord()));
                        alert.showAndWait();
                        showChooseWord();
                    } else {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                letterInput.requestFocus();
                            }
                        });
                    }
                } else if (result == HangmanGame.GuessResult.NO) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            drawHangman();
                            letterInput.setText(null);
                            showGameInfo();
                        }
                    });

                    if (game.lost()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Game over");
                        alert.setHeaderText("You lost :(");
                        alert.setContentText(String.format("The word is '%s'", game.getWord()));
                        alert.showAndWait();
                        showChooseWord();
                    } else {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                letterInput.requestFocus();
                            }
                        });
                    }
                } else if (result == HangmanGame.GuessResult.ALREADY_TRIED) {
                    showValidationErrorDialog(String.format("You have already tried a letter '%s', please use another letter", letter));
                    Platform.runLater(new Runnable() {
                        public void run() {
                            letterInput.requestFocus();
                        }
                    });
                } else {
                    throw new IllegalStateException("unknown result=" + result);
                }
            }
        });

        pane.add(new Label("Letter: "), 0, 2);
        HBox guessBox = new HBox();
        guessBox.setSpacing(10);
        guessBox.getChildren().addAll(letterInput, guessLetterBtn);
        pane.add(guessBox, 1, 2);

        showGameInfo();
        drawHangman();

        root.setLeft(canvas);
        root.setCenter(pane);
    }

    private void showGameInfo() {
        openLettersBox.getChildren().clear();
        for (int i = 0; i < game.getWord().length(); i++) {
            if (!game.isOpen(i)) {
                openLettersBox.getChildren().add(new Label("_"));
            } else {
                openLettersBox.getChildren().add(new Label(game.getOpenLetter(i) + ""));
            }
        }

        missedLettersBox.getChildren().clear();
        for (char ch : game.getMissedLetters()) {
            missedLettersBox.getChildren().add(new Label(ch + ""));
        }
    }

    private void drawHangman() {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.strokeLine(170, 380, 220, 380);
        gc.strokeLine(195, 380, 195, 50);
        gc.strokeLine(195, 50, 95, 50);
        gc.strokeLine(95, 50, 95, 100);

        int wrongGuesses = game.wrongGuessesCount();
        if (wrongGuesses > 0) {
            // head
            gc.strokeOval(70, 105, 50, 50);
        }

        if (wrongGuesses > 1) {
            // body
            gc.strokeLine(95, 155, 95, 260);
        }

        if (wrongGuesses > 2) {
            // left arm
            gc.strokeLine(95, 175, 45, 215);
        }

        if (wrongGuesses > 3) {
            // right arm
            gc.strokeLine(95, 175, 140, 215);
        }

        if (wrongGuesses > 4) {
            // left leg
            gc.strokeLine(95, 260, 45, 300);
        }
            
        if (wrongGuesses > 5) {
            // right leg
            gc.strokeLine(95, 260, 140, 300);
        }
    }

    private void showChooseWord() {
        clearRoot();

        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        TextField wordInput = new TextField();
        wordInput.setPrefWidth(250);

        Button startBtn = new Button("Start");
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        String word = wordInput.getText();
                        if (word == null || word.trim().isEmpty()) {
                            showValidationErrorDialog("Please enter a word");
                            return;
                        }

                        beginGame(word.trim());
                    }
                });
            }
        });

        box.getChildren().addAll(new Label("Choose a word to guess"), wordInput, startBtn);
        root.setCenter(box);
    }

    private void showValidationErrorDialog(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class HangmanGame {

        enum GuessResult { YES, NO, ALREADY_TRIED };

        private String word;
        private char[] openLetters;
        private List<Character> missedLetters;
        private int wrongGuesses;

        HangmanGame(String word) {
            this.word = word.toUpperCase();
            this.openLetters = new char[word.length()];
            this.missedLetters = new ArrayList<Character>(6); // never more than six wrong guesses
            this.wrongGuesses = 0;
        }

        String getWord() {
            return word;
        }

        GuessResult makeGuess(char letter) {
            if (lost() || won()) {
                throw new IllegalStateException("Game is already over");
            }

            letter = Character.toUpperCase(letter);

            for (int i = 0; i < missedLetters.size(); i++) {
                if (missedLetters.get(i) == letter) {
                    return GuessResult.ALREADY_TRIED;
                }
            }

            for (int i = 0; i < openLetters.length; i++) {
                if (openLetters[i] == letter) {
                    return GuessResult.ALREADY_TRIED;
                }
            }

            boolean opened = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    openLetters[i] = letter;
                    opened = true;
                }
            }

            if (opened) {
                return GuessResult.YES;
            }

            missedLetters.add(letter);
            wrongGuesses++;
            return GuessResult.NO;
        }

        boolean won() {
            if (lost()) {
                return false;
            }

            for (int i = 0; i < word.length(); i++) {
                if (!isOpen(i)) {
                    return false;
                }
            }

            return true;
        }

        boolean lost() {
            return wrongGuesses >= 6;
        }

        int wrongGuessesCount() {
            return wrongGuesses;
        }

        boolean isOpen(int pos) {
            return openLetters[pos] != '\0';
        }

        char getOpenLetter(int pos) {
            if (!isOpen(pos)) {
                throw new IllegalStateException(String.format("Letters at pos %d is not opened yet", pos));
            }

            return openLetters[pos];
        }

        List<Character> getMissedLetters() {
            return Collections.unmodifiableList(missedLetters);
        }
    }
}
