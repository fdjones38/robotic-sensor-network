/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv.examples.book.chap8;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacpp.Pointer;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 *
 * @author David Saldaña
 */
public class Ex83AdvContourDetection {

    public static final int G_TRESH = 100;

    public static void main(String[] args) {
        cvNamedWindow("Contours", 1);
        
        //
        IplImage img8uc1 = cvLoadImage("flechas.jpg");
        IplImage imgEdge = cvCreateImage(cvGetSize(img8uc1), 8, 1);
        IplImage img8uc3 = cvCreateImage(cvGetSize(img8uc1), 8, 3);

        cvCvtColor(img8uc1, imgEdge, CV_BGR2GRAY);
        
        // Threshold for imgEdge.
        cvThreshold(imgEdge, imgEdge, 128, 255, CV_THRESH_BINARY);

        // Storage
        CvMemStorage storage = cvCreateMemStorage(0);

        // First contour
        CvSeq fistCont = new CvContour();

        // Find contours and return the number of contours.
        int numContours = cvFindContours(imgEdge, storage, fistCont, Loader.sizeof(CvContour.class),
                CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE);

        System.out.println("Contours detected:" + numContours);

        int n = 0;
        // for each contourn
        for (CvSeq c = fistCont; c != null; c = c.h_next()) {
//            cvCvtColor(img8uc1, img8uc3, CV_GRAY2BGR);

            // Draw contourns
            cvDrawContours(
                    img8uc3,
                    c, CvScalar.RED, CvScalar.BLUE, 0, 2, CV_AA);
            System.out.println("contour=" + n++ +"with elements="+c.total());
            cvShowImage("Contours", img8uc3);

            for(int i=0;i<c.total();i++){                
                //                CvPoint p = (CvPoint)
                Pointer p = cvGetSeqElem(c,i);

                System.out.println("("+p.toString()+","+")");                
            }
            cvWaitKey(0);
        }

        System.out.println("Finished all contours.");
        cvCvtColor(img8uc1,img8uc3,CV_GRAY2BGR);
        cvShowImage("Contours", img8uc3);
       
        cvWaitKey(0);
        
        // Destroy and release        
        cvDestroyWindow("Contours");
        cvReleaseImage(img8uc3);
        cvReleaseImage(img8uc1);
        cvReleaseImage(imgEdge);

    }
}
