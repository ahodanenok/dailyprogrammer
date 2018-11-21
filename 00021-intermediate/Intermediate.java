import java.util.Random;

/**
 * Dailyprogrammer: 21 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qp407/392012_challenge_21_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        //Matrix numbers = new Matrix(new float[][] {{ 1f }, { 2f }, { 3f }, { 4f }});

        Matrix numbers = new Matrix(k, 1);
        Random random = new Random(System.currentTimeMillis());
        for (int row = 0; row < numbers.height; row++) {
            numbers.set(row, 0, (float) random.nextInt(100));
        }

        System.out.println("Number :");
        System.out.println(numbers);
        System.out.println();

        Matrix h = haar(k);
        System.out.println("Haar Matrix:");
        System.out.println(h);
        System.out.println();

        Matrix t = h.product(numbers).multiply((float) (1.0 / Math.sqrt(k)));
        System.out.println("Haar Transform:");
        System.out.println(t);
        System.out.println();
    }

    private static Matrix haar(int k) {
        if (k <= 2) {
            return new Matrix(new float[][] {{1, 1}, {1, -1}});
        }

        Matrix h = haar(k / 2);
        Matrix h_k = h.kron(new Matrix(new float[][] {{1, 1}}));
        Matrix h_i = Matrix.identity(h.height)
            .kron(new Matrix(new float[][] {{1, -1}}))
            .multiply((float) Math.sqrt(k / 2));
        return h_k.append(h_i);
    }

    private static class Matrix {

        static Matrix identity(int n) {
            Matrix id = new Matrix(n, n);
            for (int i = 0; i < n; i++) {
                id.set(i, i, 1f);
            }

            return id;
        }
    
        final int width;
        final int height;
        private final float[][] data;

        Matrix(int height, int width) {
            this.height = height;
            this.width = width;
            this.data = new float[height][width];
        }

        Matrix(float[][] data) {
            this(data.length, data[0].length);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    set(row, col, data[row][col]);
                }
            }
        }

        void set(int row, int col, float value) {
            data[row][col] = value;
        }

        float get(int row, int col) {
            return data[row][col];
        }

        Matrix kron(Matrix other) {
            Matrix result = new Matrix(height, width * other.width);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    for (int colOther = 0; colOther < other.width; colOther++) {
                        result.set(row, other.width * col + colOther, get(row, col) * other.get(0, colOther));
                    }
                }
            }

            return result;
        }

        Matrix append(Matrix other) {
            Matrix result = new Matrix(height + other.height, width);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    result.set(row, col, get(row, col));
                }
            }

            for (int row = 0; row < other.height; row++) {
                for (int col = 0; col < other.width; col++) {
                    result.set(height + row, col, other.get(row, col));
                }
            }

            return result;
        }

        Matrix multiply(float b) {
            Matrix result = new Matrix(height, width);
            for (int row = 0; row < height; row++ ) {
                for (int col = 0; col < width; col++) {
                    result.set(row, col, get(row, col) * b);
                }
            }

            return result;
        }

        Matrix product(Matrix other) {
            Matrix result = new Matrix(height, other.width);
            for (int row = 0; row < result.height; row++) {
                for (int col = 0; col < result.width; col++) {
                    float res = 0.0f;
                    for (int m = 0; m < width; m++) {
                        res += get(row, m) * other.get(m, col);
                    }

                    result.set(row, col, res);
                }
            }

            return result;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    sb.append(String.format(" %+.2f ", get(row, col)));
                }
                sb.append("\n");
            }

            return sb.toString();
        }
    }
}
