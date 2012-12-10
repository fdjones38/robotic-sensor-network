package segmentation;

import opencv.examples.*;
import javax.swing.JFrame;
import com.googlecode.javacpp.Pointer;
import com.googlecode.javacv.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 * 
 * @author David Saldaña
 */
public class SegmentationTest {

    /**
     * usage: java SegmentationTest imageDir\imageName TransformType
     */
    public static void main(String[] args) {

        String fileName = args.length >= 1 ? args[0] : "flechas.jpg"; // if no params provided, compute the defaut image
        IplImage src = cvLoadImage(fileName, 0);
        IplImage dst;

        CanvasFrame source = new CanvasFrame("Source");
        CanvasFrame hough = new CanvasFrame("Hough");

        dst = cvCreateImage(cvGetSize(src), src.depth(), 1);

        cvThreshold(src, dst, 166, 255, CV_THRESH_BINARY);
//        cvThreshold(src, dst, 0, 255,  CV_THRESH_TOZERO_INV | CV_THRESH_OTSU);
           
        source.showImage(src);
        hough.showImage(dst);

        cvSaveImage("thresh.jpg", dst);
        
        source.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hough.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
