import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.geom.Path2D;
import java.io.File;
import java.util.Random;

/**
 * Dailyprogrammer: 7 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pr265/2152012_challenge_7_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int w = Integer.parseInt(args[0]);
        int h = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[2]);

        SierpinskiTriangle sierpinski = new SierpinskiTriangle(w, h);
        BufferedImage image = sierpinski.draw(n);

        try {
            ImageIO.write(image, "png", new File("triangle.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SierpinskiTriangle {

        private int w;
        private int h;

        SierpinskiTriangle(int w, int h) {
            this.w = w;
            this.h = h;
        }

        public BufferedImage draw(int n) {
            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setPaint(Color.WHITE);
            graphics.fillRect(0, 0, w, h);
            draw(0, 0, w, h, n, graphics);
            graphics.dispose();

            return image;
        }

        private void draw(int x, int y, int w, int h, int n, Graphics2D graphics) {
            //System.out.println(String.format("x=%d, y=%d, w=%d, h=%d", x, y, w, h));
            if (n == 0) {
                graphics.setPaint(getColor(n));
                graphics.draw(triangle(
                    x, y + h - 1,
                    x + w / 2, y,
                    x + w, y + h - 1));
            } else {

                graphics.setPaint(getFill(n));
                graphics.fill(triangle(
                    x + w / 4, y + h / 2,
                    x + w - w / 4, y + h / 2,
                    x + w / 2, y + h));

                draw(x, y + h / 2, w / 2, h / 2, n - 1, graphics);
                draw(x + w / 4, y, w / 2, h / 2, n - 1, graphics);
                draw(x + w / 2, y + h / 2, w / 2, h / 2, n - 1, graphics);
            }
        }

        private Shape triangle(double x1, double y1,
                               double x2, double y2,
                               double x3, double y3) {
            Path2D.Double t = new Path2D.Double();
            t.moveTo(x1, y1);
            t.lineTo(x2, y2);
            t.lineTo(x3, y3);
            t.lineTo(x1, y1);

            return t;
        }

        private Color getColor(int n) {
            return Color.BLACK;
        }

        private Color getFill(int n) {
            Random rnd = new Random(n);
            return new Color(rnd.nextInt(0xFF), rnd.nextInt(0xFF), rnd.nextInt(0xFF));
        }
    }
}
