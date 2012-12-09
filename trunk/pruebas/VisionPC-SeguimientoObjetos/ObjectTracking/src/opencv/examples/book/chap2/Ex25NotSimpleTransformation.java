/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv.examples.book.chap2;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 *
 * @author dav
 */
public class Ex25NotSimpleTransformation {

    public static final String EXAMPLE_CANNY = "Example Canny";
    public static final String EXAMPLE_OUT = EXAMPLE_CANNY + "_OUT";

    public static void doPyrDown(IplImage image, int filter) {

        if (image.width() % 2.0 != 0 || image.height() % 2.0 != 0) {
            return;
        }

        // Input image
        cvNamedWindow(EXAMPLE_CANNY);

        // Output image
        cvNamedWindow(EXAMPLE_OUT);

        // Show the input image
        cvShowImage(EXAMPLE_CANNY, image);

        // Create a new image beffore reducing
        IplImage out = cvCreateImage(cvSize(image.width() / 2, image.height()
                / 2), image.depth(), image.nChannels());

        // Do the smoothing
        cvPyrDown(image, out, filter);

        // Show the smoothed image
        // Show the input image
        cvShowImage(EXAMPLE_OUT, out);

    }

    public static void main(String[] args) {
        // if no params provided, compute the defaut image
        String fileName = args.length >= 1 ? args[0] : "pic1.png";
        IplImage img = cvLoadImage(fileName);

        doPyrDown(img, CV_GAUSSIAN_5x5);

        cvWaitKey(0);
        cvReleaseImage(img);
        cvDestroyWindow(EXAMPLE_CANNY);

    }
}
