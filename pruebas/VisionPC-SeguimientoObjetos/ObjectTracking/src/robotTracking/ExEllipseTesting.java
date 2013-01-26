/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robotTracking;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacpp.Pointer;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 *
 * @author David Saldaña
 */
public class ExEllipseTesting {

    public static void main(String[] args) {
        cvNamedWindow("Contours", 1);

        //
        IplImage originalImg = cvLoadImage("flechas.jpg");
        IplImage grayImg = cvCreateImage(cvGetSize(originalImg), 8, 1);
        IplImage img8uc3 = cvCreateImage(cvGetSize(originalImg), 8, 3);

        // Apply smoothing is important to remove small particles.
        cvSmooth(originalImg, originalImg, CV_GAUSSIAN, 3);

        cvCvtColor(originalImg, grayImg, CV_BGR2GRAY);

        // Threshold for imgEdge.
//        cvThreshold(imgEdge, imgEdge, 128, 255, CV_THRESH_BINARY);
        cvAdaptiveThreshold(grayImg, grayImg, 255, CV_ADAPTIVE_THRESH_GAUSSIAN_C,
                CV_THRESH_BINARY, 91, 30.0);

        // Storage
        CvMemStorage storage = cvCreateMemStorage(0);

        // First contour
        CvSeq fistCont = new CvContour();

        // Find contours and return the number of contours.
        int numContours = cvFindContours(grayImg, storage, fistCont, Loader.sizeof(CvContour.class),
                CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);

        System.out.println("Contours detected:" + numContours);

        int n = 0;
        // for each contourn
        for (CvSeq c = fistCont; c != null; c = c.h_next()) {
//            cvCvtColor(img8uc1, img8uc3, CV_GRAY2BGR);

            // Draw contourns
            cvDrawContours(
                    img8uc3,
                    c, CvScalar.RED, CvScalar.BLUE, 0, 2, CV_AA);
            System.out.println("contour=" + n++ + "with elements=" + c.total());
            cvShowImage("Contours", img8uc3);



            if(c.total()>5){
            CvBox2D e = cvFitEllipse2(c);

            cvDrawEllipse(img8uc3, cvPoint((int) e.center().x(), (int) e.center().y()),
                    cvSize((int) e.size().width(), (int) e.size().height()), e.angle(),
                    0, 360, CvScalar.YELLOW, 1, CV_AA,0);

            



            cvWaitKey(0);
            }



//            ellipse.
        }

        System.out.println("Finished all contours.");
//        cvCvtColor(img8uc1,img8uc3,CV_GRAY2BGR);
        cvShowImage("Contours", img8uc3);

        cvSaveImage("contours.jpg", img8uc3);
        cvWaitKey(0);

        // Destroy and release        
        cvDestroyWindow("Contours");
        cvReleaseImage(img8uc3);
        cvReleaseImage(originalImg);
        cvReleaseImage(grayImg);

    }
}
