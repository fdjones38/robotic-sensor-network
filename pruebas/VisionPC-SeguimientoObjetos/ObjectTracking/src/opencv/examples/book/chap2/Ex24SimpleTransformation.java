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
public class Ex24SimpleTransformation {
    
    public static final String EXAMPLE_NAME = "Example 2.4";
public static final String EXAMPLE_OUT = EXAMPLE_NAME+"_OUT";
    
    public static void applySmooth(IplImage image){
        
        // Input image
        cvNamedWindow(EXAMPLE_NAME);
        
        // Output image
        cvNamedWindow(EXAMPLE_OUT);
        
        // Show the input image
        cvShowImage(EXAMPLE_NAME, image);
        
        // Create a new image beffore applying smooth.
        IplImage out= cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 3);
        
        // Do the smoothing
        cvSmooth(image, out, CV_GAUSSIAN, 9);
        
        // Show the smoothed image
            // Show the input image
        cvShowImage(EXAMPLE_OUT,out);
        
    }
    
    public static void main(String[] args) {
        // if no params provided, compute the defaut image
        String fileName = args.length >= 1 ? args[0] : "pic1.png";
        IplImage img = cvLoadImage(fileName);

        applySmooth(img);
        
        cvWaitKey(0);
        cvReleaseImage(img);
        cvDestroyWindow(EXAMPLE_NAME);
        
    }
}
