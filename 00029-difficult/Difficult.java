import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Dailyprogrammer: 29 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/r8a7x/3222012_challenge_29_difficult/
 */
public class Difficult extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.scale(2.0, 2.0);

        drawLine(100, 10, 200, 300, gc);
        drawLine(200, 300, 100, 10, gc);

        Group root = new Group();
        root.getChildren().add(canvas);

        stage.setTitle("Dailyprogrammer: 29 - difficult");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void drawLine(int xStart, int yStart, int xEnd, int yEnd, GraphicsContext gc) {
        double dx = xEnd - xStart;
        double dy = yEnd - yStart;
        for (int x = xStart; x <= xEnd; x++) {
            double y = yStart + dy * (x - xStart) / dx;
            point(x, y, gc);
        }
    }

    private void point(double x, double y, GraphicsContext gc) {
        gc.strokeLine(x, y, x, y);
    }
}
