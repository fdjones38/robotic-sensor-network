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
public class Ex27CombiningPyDownCanny {

    public static final String EXAMPLE_NAME = "Example 2.7";
    public static final String EXAMPLE_OUT = EXAMPLE_NAME + "_OUT";
    public static final String EXAMPLE_PYR = "Example Canny";
    public static final String EXAMPLE_PYR_OUT = EXAMPLE_PYR + "_OUT";

    
    
    
    public static IplImage doPyrDown(IplImage image, int filter) {

        if (image.width() % 2.0 != 0 || image.height() % 2.0 != 0) {
            System.out.println("Not divisible by 2");
            return null;
        }

        // Input image
        cvNamedWindow(EXAMPLE_PYR);


        // Show the input image
        cvShowImage(EXAMPLE_PYR, image);

        // Create a new image beffore reducing
        IplImage out = cvCreateImage(cvSize(image.width() / 2, image.height()
                / 2), image.depth(), image.nChannels());

        // Do the smoothing
        cvPyrDown(image, out, filter);


        return out;

    }
    public static IplImage doCanny(IplImage image, double lowThresh, double highThresh, int aperture) {


        IplImage gray = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
//        cvMerge(gray, null, null, null, image);
        cvCvtColor(image, gray,CV_RGB2GRAY); 

        System.out.println("Channels=" + gray.nChannels());
        

        // Input image
        cvNamedWindow(EXAMPLE_NAME);


        // Show the input image
        cvShowImage(EXAMPLE_NAME, gray);

        // Create a new image beffore canny
        IplImage out = cvCreateImage(cvGetSize(gray), IPL_DEPTH_8U, 1);

        // Do canny
        cvCanny(gray, out, lowThresh, highThresh, aperture);

        return out;

    }

    public static void main(String[] args) {
        // if no params provided, compute the defaut image
        String fileName = args.length >= 1 ? args[0] : "flor.jpg";
        IplImage img = cvLoadImage(fileName);
        
        // Output image
        cvNamedWindow(EXAMPLE_OUT);
        
        IplImage img1 = doPyrDown(img, CV_GAUSSIAN_5x5);
        IplImage img2 = doPyrDown(img1, CV_GAUSSIAN_5x5);
        IplImage img3 = doCanny(img2, 10, 100, 3);
        cvShowImage(EXAMPLE_OUT, img3);

        cvWaitKey(0);
        cvReleaseImage(img);
        cvReleaseImage(img1);
        cvReleaseImage(img2);
        cvReleaseImage(img3);
        cvDestroyWindow(EXAMPLE_NAME);
        cvDestroyWindow(EXAMPLE_OUT);

    }
}
