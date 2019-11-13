/**
 * 因为用strassen法，要求是2的n次方的方阵，所以这里全用方阵
 */
public class Matrix {
    private static final int LEFT_UP = 0;
    private static final int RIGHT_UP = 1;
    private static final int LEFT_DOWN = 2;
    private static final int RIGHT_DOWN = 3;
    int n;
    public int[][] my;

    public Matrix(int n) {
        this.n = n;
        my = new int[n][n];
    }

    public Matrix normalMultiply(Matrix m) {
        int[][] r = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                r[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    r[i][j] += my[i][k] * m.my[k][j];
                }
            }
        }
        Matrix matrix = new Matrix(n);
        matrix.my = r;
        return matrix;
    }

    public Matrix strassenMultiply(Matrix m) {
        // 终止条件
        if (m.n == 2) {
            int[][] r = new int[2][2];
            int a = my[0][0];
            int b = my[0][1];
            int c = my[1][0];
            int d = my[1][1];
            int e = m.my[0][0];
            int f = m.my[0][1];
            int h = m.my[1][1];
            int g = m.my[1][0];
            int p1 = a * (f - h);
            int p2 = (a + b) * h;
            int p3 = (c + d) * e;
            int p4 = d * (g - e);
            int p5 = (a + d) * (e + h);
            int p6 = (b - d) * (g + h);
            int p7 = (a - c) * (e + f);
            r[0][0] = p5 + p4 - p2 + p6;
            r[0][1] = p1 + p2;
            r[1][0] = p3 + p4;
            r[1][1] = p5 + p1 - p3 - p7;
            Matrix matrix = new Matrix(n);
            matrix.my = r;
            return matrix;
        }
        // 递归，分成4个n/2矩阵
        Matrix A = new Matrix(n / 2);
        inflateMatrix(my, A, LEFT_UP);
        Matrix B = new Matrix(n / 2);
        inflateMatrix(my, B, RIGHT_UP);
        Matrix C = new Matrix(n / 2);
        inflateMatrix(my, C, LEFT_DOWN);
        Matrix D = new Matrix(n / 2);
        inflateMatrix(my, D, RIGHT_DOWN);
        // 递归，分成4个n/2矩阵
        Matrix E = new Matrix(n / 2);
        inflateMatrix(m.my, E, LEFT_UP);
        Matrix F = new Matrix(n / 2);
        inflateMatrix(m.my, F, RIGHT_UP);
        Matrix G = new Matrix(n / 2);
        inflateMatrix(m.my, G, LEFT_DOWN);
        Matrix H = new Matrix(n / 2);
        inflateMatrix(m.my, H, RIGHT_DOWN);

        Matrix P1 = A.strassenMultiply(F.subtract(H));
        Matrix P2 = A.add(B).strassenMultiply(H);
        Matrix P3 = C.add(D).strassenMultiply(E);
        Matrix P4 = D.strassenMultiply(G.subtract(E));
        Matrix P5 = A.add(D).strassenMultiply(E.add(H));
        Matrix P6 = B.subtract(D).strassenMultiply(G.add(H));
        Matrix P7 = A.subtract(C).strassenMultiply(E.add(F));

        Matrix R = P5.add(P4).subtract(P2).add(P6);
        Matrix S = P1.add(P2);
        Matrix T = P3.add(P4);
        Matrix U = P5.add(P1).subtract(P3).subtract(P7);
        // 合并结果
        Matrix result = new Matrix(n);
        for (int i = 0; i < R.n; i++) {
            for (int j = 0; j < R.n; j++) {
                result.my[i][j] = R.my[i][j];
            }
        }
        for (int i = 0; i < S.n; i++) {
            for (int j = 0; j < S.n; j++) {
                result.my[i][j + S.n] = S.my[i][j];
            }
        }
        for (int i = 0; i < T.n; i++) {
            for (int j = 0; j < T.n; j++) {
                result.my[i + T.n][j] = T.my[i][j];
            }
        }
        for (int i = 0; i < U.n; i++) {
            for (int j = 0; j < U.n; j++) {
                result.my[i + U.n][j + U.n] = U.my[i][j];
            }
        }
        return result;
    }

    private void inflateMatrix(int[][] my, Matrix m, int w) {
        if (LEFT_UP == w) {
            for (int i = 0; i < m.n; i++) {
                for (int j = 0; j < m.n; j++) {
                    m.my[i][j] = my[i][j];
                }
            }
        } else if (RIGHT_UP == w) {
            for (int i = 0; i < m.n; i++) {
                for (int j = 0; j < m.n; j++) {
                    m.my[i][j] = my[i][m.n + j];
                }
            }
        } else if (LEFT_DOWN == w) {
            for (int i = 0; i < m.n; i++) {
                for (int j = 0; j < m.n; j++) {
                    m.my[i][j] = my[m.n + i][j];
                }
            }
        } else if (RIGHT_DOWN == w) {
            for (int i = 0; i < m.n; i++) {
                for (int j = 0; j < m.n; j++) {
                    m.my[i][j] = my[m.n + i][m.n + j];
                }
            }
        } else {
            throw new IllegalArgumentException("w is wrong number");
        }
    }

    private Matrix add(Matrix b) {
        Matrix m = new Matrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m.my[i][j] = my[i][j] + b.my[i][j];
            }
        }
        return m;
    }

    private Matrix subtract(Matrix b) {
        Matrix m = new Matrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m.my[i][j] = my[i][j] - b.my[i][j];
            }
        }
        return m;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                builder.append(my[i][j]);
                builder.append(", ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
