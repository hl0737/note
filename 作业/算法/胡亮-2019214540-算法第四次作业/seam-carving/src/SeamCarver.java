import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture mOriginPicture; // 最开始传入constructor的picture，留个备份
    private Picture mPicture;
    private double d[][]; // 存储(i,j)的破坏值
    private double r[][]; // 存储以(i,j)结尾的像素的最短破坏值
    private int p[][]; // 存储破坏值最短的路径，-1：上一行前一列 0：上一行当列 1：上一行后一列

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        validateNull(picture);
        mPicture = picture;
        mOriginPicture = new Picture(mPicture);
        d = new double[mPicture.height()][mPicture.width()];
        initD();
        r = new double[mPicture.height()][mPicture.width()];
        p = new int[mPicture.height()][mPicture.width()];
    }

    private void initD() {
        for (int i = 0; i < mPicture.width(); i++) {
            for (int j = 0; j < mPicture.height(); j++) {
                d[j][i] = energy(i, j);
            }
        }
    }

    // current picture
    public Picture picture() {
        return mPicture;
    }

    // width of current picture
    public int width() {
        return mPicture.width();
    }

    // height of current picture
    public int height() {
        return mPicture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateCoordinate(x, y);
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
            return 1000;
        else {
            int Rx = getR(x + 1, y) - getR(x - 1, y);
            int Gx = getG(x + 1, y) - getG(x - 1, y);
            int Bx = getB(x + 1, y) - getB(x - 1, y);
            int Ry = getR(x, y + 1) - getR(x, y - 1);
            int Gy = getG(x, y + 1) - getG(x, y - 1);
            int By = getB(x, y + 1) - getB(x, y - 1);
            int deltaX = Rx * Rx + Gx * Gx + Bx * Bx;
            int deltaY = Ry * Ry + Gy * Gy + By * By;
            return Math.sqrt(deltaX + deltaY);
        }
    }

    private int getR(int x, int y) {
        validateCoordinate(x, y);
        return mPicture.get(x, y).getRed();
    }

    private int getG(int x, int y) {
        validateCoordinate(x, y);
        return mPicture.get(x, y).getGreen();
    }

    private int getB(int x, int y) {
        validateCoordinate(x, y);
        return mPicture.get(x, y).getBlue();
    }

    private void validateCoordinate(int x, int y) {
        int w = width();
        int h = height();
        if (x > w || y > h) {
            throw new IllegalArgumentException(x > w ? "x exceeds the width of" +
                    " the picture" : "y exceeds the height of the picture");
        }
    }

    /**
     * 好像可以转置一下，然后调用Vertical来求，再转置回去
     * 转置交给外面
     *
     * @return
     */
    public int[] findHorizontalSeam() {
        int[] verticalSeam = findVerticalSeam();
        return verticalSeam;
    }

    /**
     * 动态规划求解
     *
     * @return
     */
    public int[] findVerticalSeam() {
        int[] result = new int[mPicture.height()];
        for (int i = 0; i < mPicture.height(); i++) {
            for (int j = 0; j < mPicture.width(); j++) {
                if (i == 0) {
                    // 第一行
                    r[i][j] = d[i][j];
                    p[i][j] = -1; // 第一行的前面没有行了，赋值为-1
                } else if (j == 0) {
                    // 第一列
                    if (r[i - 1][j] <= r[i - 1][j + 1]) {
                        r[i][j] = r[i - 1][j] + d[i][j];
                        p[i][j] = j;
                    } else {
                        r[i][j] = r[i - 1][j + 1] + d[i][j];
                        p[i][j] = j + 1;
                    }
                } else if (j == mPicture.width() - 1) {
                    // 最后一列
                    if (r[i - 1][j - 1] <= r[i - 1][j]) {
                        r[i][j] = r[i - 1][j - 1] + d[i][j];
                        p[i][j] = j - 1;
                    } else {
                        r[i][j] = r[i - 1][j] + d[i][j];
                        p[i][j] = j;
                    }
                } else {
                    // 内点
                    double min;
                    int minIndex;
                    if (r[i - 1][j - 1] <= r[i - 1][j]) {
                        min = r[i - 1][j - 1];
                        minIndex = j - 1;
                    } else {
                        min = r[i - 1][j];
                        minIndex = j;
                    }
                    if (r[i - 1][j + 1] < min) {
                        min = r[i - 1][j + 1];
                        minIndex = j + 1;
                    }
                    r[i][j] = min + d[i][j];
                    p[i][j] = minIndex;
                }
            }
        }
        // 这里不是找接缝的最小值，而是找这条路径，构造result
        double min = Double.MAX_VALUE;
        int minJ = -1;
        for (int j = 0; j < mPicture.width(); j++) {
            if (r[mPicture.height() - 1][j] < min) {
                min = r[mPicture.height() - 1][j];
                minJ = j;
            }
        }
        int k = 0;
        result[k++] = minJ;
        for (int i = mPicture.height() - 1; i > 0; i--) {
            minJ = p[i][minJ];
            result[k++] = minJ;
        }
        // 把result转置
        for (int i = 0, j = result.length - 1; i < result.length / 2; i++, j--) {
            int temp = result[i];
            result[i] = result[j];
            result[j] = temp;
        }
        return result;
    }

    /**
     * 把图片转置，然后按vertical的删完，再转置回去
     *
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam);
        removeVerticalSeam(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam);
        Picture r = new Picture(mPicture.width() - 1, mPicture.height());
        for (int i = 0; i < r.height(); i++) {
            int js = seam[i];
            for (int j = 0, npj = 0; j < r.width(); j++) {
                // 若j == js，则当前列要被remove，npj不自增
                if (j != js) {
                    r.setRGB(npj++, i, mPicture.getRGB(j, i));
                }
            }
        }
        mPicture = r;
        // 重新计算d数组, 懒得分辨哪些该变哪些不变了，我算力牛逼，全给你算一遍
        initD();
    }

    private void validateSeam(int[] seam) {
        validateNull(seam);
        if (width() <= 1)
            throw new IllegalArgumentException("the width or height of the picture is not greater than 1, cant be carved");
        for (int i = 0; i < seam.length; i++) {
            int x = i;
            int y = seam[i];
            validateCoordinate(y, x);
        }
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("two adjacent entries differ by more than 1");
        }
    }

    private void validateNull(Object o) {
        if (o == null)
            throw new IllegalArgumentException("argument cant be null");
    }

    public void transpose() {
        Picture transpose = new Picture(mPicture.height(), mPicture.width());
        for (int i = 0; i < mPicture.height(); i++) {
            for (int j = 0; j < mPicture.width(); j++) {
                transpose.setRGB(i, j, mPicture.getRGB(j, i));
            }
        }
        mPicture = transpose;
        d = new double[mPicture.height()][mPicture.width()];
        initD();
        r = new double[mPicture.height()][mPicture.width()];
        p = new int[mPicture.height()][mPicture.width()];
    }
}
