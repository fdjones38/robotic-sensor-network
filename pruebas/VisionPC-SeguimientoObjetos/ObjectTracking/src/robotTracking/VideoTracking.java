/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robotTracking;

import com.googlecode.javacpp.Loader;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

/**
 *
 * @author dav
 */
public class VideoTracking {

    public static final String VIDEO_NAME = "Video original";
    public static final String PROC_VIDEO_NAME = "Video procesado";
    private static final double RELATION_MAX = 1.9;
    private static final double RELATION_MIN = 1.2;
    
    public static void main(String args[]) {

        // Cargar el video.
        CvCapture video = cvCreateFileCapture("ProjectRobotCV-robotSolo.avi");

        // Crear la ventana del video normal.
        cvNamedWindow(VIDEO_NAME, CV_WINDOW_AUTOSIZE);
        // Crear la ventana del video procesado.
        cvNamedWindow(PROC_VIDEO_NAME, CV_WINDOW_AUTOSIZE);

        IplImage frame;

        // Se captura el primer frame.
        frame = cvQueryFrame(video);


        while (frame != null) {

            // Imagen en escala de grises vacia.
            IplImage grayFrame = cvCreateImage(cvGetSize(frame), IPL_DEPTH_8U, 1);
            // Imagen en escala de grises vacia.
            IplImage processedFrame = cvCreateImage(cvGetSize(frame), IPL_DEPTH_8U, 1);
            // Convierte el frame en esacala de grises.
            cvCvtColor(frame, grayFrame, CV_RGB2GRAY);


            //cvCanny(gray_converted, gray_frame, 50, 200, 3);
            //cvWatershed(frame, frame);
            //cvAdaptiveThreshold(gray_converted, gray_frame, 192, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY, 3, 0);
            //cvCanny(gray_converted, gray_frame, 50, 200, 3);
            //cvErode(gray_frame, gray_frame, null, 1);
            //cvDilate(gray_frame, gray_frame, null, 1);                                    
            //cvSmooth(gray_frame, gray_frame, CV_GAUSSIAN, 5);

            //cvErode(gray_frame, gray_frame, null, CV_C);

            //Smooth
            cvSmooth(grayFrame, grayFrame, CV_GAUSSIAN, 3);


            // Aplica el threshold
//            cvThreshold(grayFrame, processedFrame, 92, 255, CV_THRESH_BINARY);
//            cvThreshold(grayFrame, processedFrame, 0, 255,  CV_THRESH_TOZERO_INV | CV_THRESH_OTSU);
//            cvThreshold(grayFrame, processedFrame, 0, 255,   CV_THRESH_OTSU);
            // TODO: saber para que son esos parametros 91 y 30.
            cvAdaptiveThreshold(grayFrame, processedFrame, 255, CV_ADAPTIVE_THRESH_GAUSSIAN_C,
                    CV_THRESH_BINARY, 91, 30.0);

            // Storage
            CvMemStorage storage = cvCreateMemStorage(0);

            // First contour
            CvSeq fistCont = new CvContour();

//             cvCanny(processedFrame, processedFrame, 10, 200, 3);

            // Find contours and return the number of contours.
            //    CV_CHAIN_APPROX_SIMPLE compresses horizontal, vertical, and diagonal segments, that is, the function leaves only their ending points;
            int numContours = cvFindContours(processedFrame, storage, fistCont, Loader.sizeof(CvContour.class),
                    CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);




            // Number of contorns
            int n = 0;

            // for each contourn
            for (CvSeq c = fistCont; c != null; c = c.h_next()) {

                // Draw contourns
                cvDrawContours(
                        frame,
                        c, CvScalar.RED, CvScalar.BLUE, 0, 1, CV_AA);
                System.out.println("contour=" + n++ + "with elements=" + c.total());


                // Restringir por número de puntos
                if (c.total() > 40 && c.total() < 60) {
                    CvBox2D e = cvFitEllipse2(c);
                    CvPoint centerPoint = cvPoint((int) e.center().x(), (int) e.center().y());


                    double relacion = e.size().height() / e.size().width();
                    
                    if (relacion > RELATION_MIN && relacion < RELATION_MAX) {
                        cvDrawEllipse(frame, centerPoint,
                                cvSize((int) e.size().width() / 2, (int) e.size().height() / 2), e.angle(),
                                0, 360, CvScalar.YELLOW, 2, CV_AA, 0);
                    }



                    // For debug put a text.
//                    cvPutText(frame, "P" + (),
//                            centerPoint, new CvFont(CV_FONT_HERSHEY_PLAIN, 1, 1),
//                            new CvScalar());

                }
            }


            // Muestra los dos frame, original y procesado..
            cvShowImage(VIDEO_NAME, frame);
            cvShowImage(PROC_VIDEO_NAME, processedFrame);

            // Esperar 10 ms entre frames.
            cvWaitKey(10);

            // Captura el siguiente frame.
            frame = cvQueryFrame(video);
        }

        cvReleaseCapture(video);

        // Destruir las ventanas creadas.
        cvDestroyWindow(VIDEO_NAME);
        cvDestroyWindow(PROC_VIDEO_NAME);
    }
}
