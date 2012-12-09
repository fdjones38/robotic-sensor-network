/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv.examples.book.chap5;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 *
 * @author David Saldaña
 */
public class Ex54Threshold {

    public static final int THRESHOLD = CV_THRESH_BINARY;
    public static final int THRESHOLD_TYPE = CV_THRESH_BINARY;
    public static final int ADAPTATIVE_METHOD = CV_ADAPTIVE_THRESH_GAUSSIAN_C;
    public static final int BLOCK_SIZE = 3;
    public static final int OFFSET = -30;
    public static final String IMAGE_FILE = "flechas.jpg";

    public static void main(String[] args) {

        //Read in gray image
        IplImage grayImag = cvLoadImage(IMAGE_FILE, CV_LOAD_IMAGE_GRAYSCALE);
        IplImage theshImag = cvCreateImage(cvSize(grayImag.width(),
                grayImag.height()), IPL_DEPTH_8U, 1);
        IplImage adapTheshImag = cvCreateImage(cvSize(grayImag.width(),
                grayImag.height()), IPL_DEPTH_8U, 1);


        //Threshold
        cvThreshold(grayImag, theshImag, THRESHOLD, 255, THRESHOLD_TYPE);
        cvAdaptiveThreshold(grayImag, adapTheshImag, 255, ADAPTATIVE_METHOD,
                THRESHOLD_TYPE, BLOCK_SIZE, OFFSET);


        //PUT UP 2 WINDOWS
        cvNamedWindow("Raw", 1);
        cvNamedWindow("Threshold", 1);
        cvNamedWindow("Adaptive Threshold", 1);

        //Show the results
        cvShowImage("Raw", grayImag);
        cvShowImage("Threshold", theshImag);
        cvShowImage("Adaptive Threshold", adapTheshImag);

        cvWaitKey(0);

        cvReleaseImage(grayImag);
        cvReleaseImage(theshImag);
        cvReleaseImage(adapTheshImag);
        cvDestroyWindow("Raw");
        cvDestroyWindow("Threshold");
        cvDestroyWindow("Adaptive Threshold");
    }
}
