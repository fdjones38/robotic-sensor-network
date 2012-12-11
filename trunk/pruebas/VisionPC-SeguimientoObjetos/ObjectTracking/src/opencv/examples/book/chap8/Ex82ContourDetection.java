/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv.examples.book.chap8;

import com.googlecode.javacpp.Loader;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 *
 * @author David Saldaña
 */
public class Ex82ContourDetection {

    public static final int G_TRESH = 100;

    public static void main(String[] args) {
        //
        IplImage img = cvLoadImage("flechas.jpg");

        cvNamedWindow("Contours", 1);
        //        cvCreateTrackbar("Threshold", "Contours", new int[]{100}, 255, null);
        IplImage grayImg = cvCreateImage(cvGetSize(img), 8, 1);


        CvMemStorage storage = cvCreateMemStorage(0);

        // Transform to a gray scale.
        cvCvtColor(img, grayImg, CV_BGR2GRAY);

        
        // Modification of the example
        cvSmooth(grayImg, grayImg, CV_GAUSSIAN, 3);
        
        
        // Apply threshold.
          cvAdaptiveThreshold(grayImg, grayImg, 255, CV_ADAPTIVE_THRESH_GAUSSIAN_C,
                CV_THRESH_BINARY, 91, 30.0);
        

        CvSeq squares = new CvContour();

        cvFindContours(grayImg, storage, squares, Loader.sizeof(CvContour.class),
                CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE);

        
        IplImage contImg = cvCreateImage(cvGetSize(img), 8, 3);
         
        cvDrawContours(
                contImg,
                squares, CvScalar.WHITE, CvScalar.YELLOW, -1, 1, CV_AA);

        cvShowImage("Contours", contImg);

        cvSaveImage("contours.jpg", contImg);
        //        on_trackbar(0);
        cvWaitKey();

    }
}
