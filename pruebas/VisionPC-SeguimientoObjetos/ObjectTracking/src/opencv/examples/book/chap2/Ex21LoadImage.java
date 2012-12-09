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
public class Ex21LoadImage {
    public static final String EXAMPLE_NAME = "Example 2.1";

    public static void main(String[] args) {
        // if no params provided, compute the defaut image
        String fileName = args.length >= 1 ? args[0] : "pic1.png";
        IplImage img = cvLoadImage(fileName);
//        img.
        cvNamedWindow(EXAMPLE_NAME, CV_WINDOW_AUTOSIZE);
        cvShowImage(EXAMPLE_NAME, img);
        
        cvWaitKey(0);
        cvReleaseImage(img);
        cvDestroyWindow(EXAMPLE_NAME);
        
    }
}
