/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv.examples.book.chap2;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 *
 * @author dav
 */
public class Ex26CannyEdgeDetector {

    public static final String EXAMPLE_NAME = "Example 2.6";
    public static final String EXAMPLE_OUT = EXAMPLE_NAME + "_OUT";

    public static void doCanny(IplImage image, double lowThresh, double highThresh, int aperture) {


        IplImage gray = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
//        cvMerge(gray, null, null, null, image);
        cvCvtColor(image, gray,CV_RGB2GRAY); 

        System.out.println("Channels=" + gray.nChannels());
        if (gray.nChannels() != 1) {
            System.out.println("Canny only handles gray scale images.");
            return;
        }

        // Input image
        cvNamedWindow(EXAMPLE_NAME);

        // Output image
        cvNamedWindow(EXAMPLE_OUT);

        // Show the input image
        cvShowImage(EXAMPLE_NAME, gray);

        // Create a new image beffore canny
        IplImage out = cvCreateImage(cvGetSize(gray), IPL_DEPTH_8U, 1);

        // Do canny
        cvCanny(gray, out, lowThresh, highThresh, aperture);

        // Show the smoothed image
        // Show the input image
        cvShowImage(EXAMPLE_OUT, out);

    }

    public static void main(String[] args) {
        // if no params provided, compute the defaut image
        String fileName = args.length >= 1 ? args[0] : "flor.jpg";
        IplImage img = cvLoadImage(fileName);

        doCanny(img, 200, 200, 3);

        cvWaitKey(0);
        cvReleaseImage(img);
        cvDestroyWindow(EXAMPLE_NAME);

    }
}
