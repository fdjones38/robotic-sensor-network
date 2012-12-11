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
        IplImage img = cvLoadImage("adrian.jpg");

        cvNamedWindow("Contours", 1);
        //        cvCreateTrackbar("Threshold", "Contours", new int[]{100}, 255, null);
        IplImage grayImg = cvCreateImage(cvGetSize(img), 8, 1);


        cvCreateImage(cvGetSize(img), 8, 1);

        CvMemStorage storage = cvCreateMemStorage(0);

        cvCvtColor(img, grayImg, CV_BGR2GRAY);


        cvThreshold(grayImg, grayImg, G_TRESH, 255, CV_THRESH_BINARY);

        CvSeq squares = new CvContour();

        cvFindContours(grayImg, storage, squares, Loader.sizeof(CvContour.class), CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);

//        cvZero(grayImg);
//
        cvDrawContours(
                grayImg,
                squares, CvScalar.YELLOW, CvScalar.BLUE, -1, 1, CV_AA);

        cvShowImage("Contours", grayImg);

        //        on_trackbar(0);
        cvWaitKey();

    }
}
