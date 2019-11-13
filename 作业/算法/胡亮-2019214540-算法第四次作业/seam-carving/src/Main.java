import edu.princeton.cs.algs4.Picture;

import java.io.File;

public class Main {
    private static final String fn = "./resources/stripes.png";
    private static final String fn1 = "./resources/12x10.png";
    private static final String fn2 = "./resources/logo.png";
    private static final String fn3 = "./resources/HJocean.png";
    private static final String fn4 = "./resources/1x1.png";
    private static final String fn5 = "./resources/1x8.png";
    private static final String fn6 = "./resources/8x1.png";

    public static void main(String[] args) {
        String fnA = args[0];
        File file = new File(fnA);
        Picture picture = new Picture(file);
        picture.show();
        SeamCarver carver = new SeamCarver(picture);
        for (int i = 0; i < picture.width() / 2; i++) {
            int[] verticalSeam = carver.findVerticalSeam();
            carver.removeVerticalSeam(verticalSeam);
        }
        carver.transpose();
        for (int i = 0; i < picture.height() / 2; i++) {
            int[] verticalSeam = carver.findHorizontalSeam();
            carver.removeHorizontalSeam(verticalSeam);
        }
        carver.transpose();
        carver.picture().show();
    }
}
