package opencv.examples.book.chap2;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import javax.swing.JFrame;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class CanvasFrameLoadImage {

	/**
	 * @author Alejandro Gómez
	 */
	public static void main(String[] args) {
		IplImage img = cvLoadImage("image.jpg");
		CanvasFrame canvas = new CanvasFrame("ExampleJava", 1);
		canvas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		canvas.showImage(img);

                
                canvas.waitKey(0);
		cvReleaseImage(img);
		canvas.dispose();
	}

}
