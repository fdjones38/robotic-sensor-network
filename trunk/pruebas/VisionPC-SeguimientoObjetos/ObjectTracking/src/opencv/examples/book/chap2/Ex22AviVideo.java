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
public class Ex22AviVideo {

    public static final String EXAMPLE_NAME = "Example 2.1";

    public static void main(String[] args) {
        // if no params provided, compute the defaut image
        String fileName = "human.avi";

        cvNamedWindow(EXAMPLE_NAME, CV_WINDOW_AUTOSIZE);

        CvCapture capture = cvCreateFileCapture(fileName);
        IplImage frame;

        while (true) {
            frame = cvQueryFrame(capture);
            if (frame == null) {
                break;
            }
            cvShowImage(EXAMPLE_NAME, frame);

            int c = cvWaitKey(33);
            System.out.println("C=" + c);
            if (c == 1179675) {
                break;
            }
        }

        cvReleaseCapture(capture);
        cvDestroyWindow(EXAMPLE_NAME);
    }
}
