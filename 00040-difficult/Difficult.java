import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 40 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/schnp/4162012_challenge_40_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Point2D[] points = generatePoints2D(n);

        Point2D[] closestPoints2D;
        //closestPoints2D = findClosest2D(points);
        //System.out.println(String.format("%s <-> %s, distance: %.20f",
        //    closestPoints2D[0], 
        //    closestPoints2D[1], 
        //    closestPoints2D[0].distanceTo(closestPoints2D[1])));

        closestPoints2D = findClosest2D_v2(points);
        System.out.println(String.format("%s <-> %s, distance: %.20f",
            closestPoints2D[0], 
            closestPoints2D[1], 
            closestPoints2D[0].distanceTo(closestPoints2D[1])));
    }

    private static Point2D[] findClosest2D(Point2D[] points) {
        Point2D[] closestPoints = new Point2D[2];
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance = points[i].squareDistanceTo(points[j]);
                if (Double.compare(distance, minDistance) < 0) {
                    closestPoints[0] = points[i];
                    closestPoints[1] = points[j];
                    minDistance = distance;
                }
            }
        }

        return closestPoints;
    }

    private static final Comparator<Point2D> X_COMPARATOR = new Comparator<Point2D>() {
        @Override
        public int compare(Point2D a, Point2D b) {
            return Double.compare(a.x, b.x);
        }
    };

    private static final Comparator<Point2D> Y_COMPARATOR = new Comparator<Point2D>() {
        @Override
        public int compare(Point2D a, Point2D b) {
            return Double.compare(a.y, b.y);
        }
    };

    private static Point2D[] findClosest2D_v2(Point2D[] points) {
        Arrays.sort(points, X_COMPARATOR);
        return findClosest2D_v2(points, 0, points.length);
    }

    private static Point2D[] findClosest2D_v2(Point2D[] points, int from, int to) {
        if (to - from == 1) {
            return new Point2D[] { points[from], null };
        }
        if (to - from == 2) {
            return new Point2D[] { points[from], points[to - 1] };
        }

        int middle = from + (to - from) / 2;
        Point2D middlePoint = points[middle];

        Point2D[] closestLeft = findClosest2D_v2(points, from, middle);
        double ll = closestLeft[1] != null ? closestLeft[0].distanceTo(closestLeft[1]) : Double.MAX_VALUE;

        Point2D[] closestRight = findClosest2D_v2(points, middle, to);
        double lr = closestRight[1] != null ? closestRight[0].distanceTo(closestRight[1]) : Double.MAX_VALUE;

        double d = Math.min(ll, lr);
        List<Point2D> strip = new ArrayList<Point2D>();
        for (int i = from; i < to; i++) {
            if (Double.compare(Math.abs(points[i].x - middlePoint.x), d) <= 0) {
                strip.add(points[i]);
            }
        }

        strip.sort(Y_COMPARATOR);

        Point2D[] closestPoints = new Point2D[2];
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < Math.min(i + 7, strip.size()); j++) {
                double distance = strip.get(i).distanceTo(strip.get(j));
                if (Double.compare(distance, minDistance) < 0) {
                    closestPoints[0] = strip.get(i);
                    closestPoints[1] = strip.get(j);
                    minDistance = distance;
                }
            }
        }

        if (Double.compare(minDistance, d) < 0) {
            return closestPoints;
        } else if (Double.compare(ll, lr) < 0) {
            return closestLeft;
        } else {
            return closestRight;
        }
    }

    private static Point2D[] generatePoints2D(int n) {
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point2D(Math.random(), Math.random());
        }

        return points;
    }

    private static class Point2D {

        private final double x;
        private final double y;

        Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double squareDistanceTo(Point2D other) {
            return Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2);
        }

        double distanceTo(Point2D other) {
            return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
        }

        @Override
        public String toString() {
            return String.format("(%.20f, %.20f)", x, y);
        }
    }
}
