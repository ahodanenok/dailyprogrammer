import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * Dailyprogrammer: 18 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qit4p/352012_challenge_18_difficult/
 */
public class Difficult {

    public static void main(String[] args) throws Exception {
        int w = Integer.parseInt(args[0]);
        int h = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[2]);

        BufferedImage image = drawSpiral(w, h, n);
        ImageIO.write(image, "png", new File("spiral.png"));
    }

    private static BufferedImage drawSpiral(int w, int h, int n) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, w, h);
        graphics.translate(w / 2.0, h / 2.0);
        new SquareSpiral().draw(n, graphics);
        //new ArchimedeanSpiral().draw(n, graphics);
        graphics.dispose();

        return image;
    }

    private interface Spiral {
        void draw(int n, Graphics2D graphics);
    }

    private static class SquareSpiral implements Spiral {

        private static final double LENGTH_INCREMENT = 25.0;
        private static final Color SPIRAL_COLOR = Color.BLACK;
        private static double[][] DIRECTIONS = new double[][] {
            {  1,  0 },
            {  0,  1 },
            { -1,  0 },
            {  0, -1 }
        };

        @Override
        public void draw(int n, Graphics2D graphics) {
            double r = 0.0;
            double x = 0.0;
            double y = 0.0;

            graphics.setPaint(SPIRAL_COLOR);
            for (int i = 1; i <= n; i++) {
                for (double[] dir : DIRECTIONS) {
                    double x2 = x + dir[0] * r;
                    double y2 = y + dir[1] * r;
                    graphics.draw(new Line2D.Double(x, y, x2, y2));
                    x = x2;
                    y = y2;
                    r += LENGTH_INCREMENT;
                }
            }
        }
    }

    private static class ArchimedeanSpiral implements Spiral {

        private static final double A = 0.0;
        private static final double B = 10.0;
        private static final double ANGLE_INCREMENT = 0.0001;
        private static final double DISTANCE_INCREMENT = 0.0;
        private static final Color SPIRAL_COLOR = Color.BLACK;

        @Override
        public void draw(int n, Graphics2D graphics) {
            double x = 0.0;
            double y = 0.0;
            double q = 0.0;
            double b = B;
            double r;

            graphics.setPaint(SPIRAL_COLOR);
            for (int i = 1; i <= n; i++) {
                while (q < 2 * i * Math.PI) {
                    r = A + b * q;
                    double x2 = x(q, r);
                    double y2 = y(q, r);
                    graphics.draw(new Line2D.Double(x, y, x2, y2));
                    x = x2;
                    y = y2;
                    b += DISTANCE_INCREMENT;
                    q += ANGLE_INCREMENT;
                }
            }
        }

        private static double x(double q, double r) {
            return r * Math.cos(q);
        }

        private static double y(double q, double r) {
            return r * Math.sin(q);
        }
    }
}
