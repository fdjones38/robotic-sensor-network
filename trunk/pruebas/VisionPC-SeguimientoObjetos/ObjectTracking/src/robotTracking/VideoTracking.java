/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robotTracking;

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
    
    
    public static void main(String args[]){
       
        // Cargar el video.
        CvCapture video = cvCreateFileCapture("RobotCV2.avi");  
        
        // Crear la ventana del video normal.
        cvNamedWindow(VIDEO_NAME,CV_WINDOW_AUTOSIZE);
        // Crear la ventana del video procesado.
        cvNamedWindow(PROC_VIDEO_NAME,CV_WINDOW_AUTOSIZE);
        
        IplImage frame;        
        
        // Se captura el primer frame.
        frame = cvQueryFrame(video);
        
        
        while(frame != null){            
            
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
            cvSmooth(grayFrame, processedFrame, CV_GAUSSIAN, 3);
            
            
            // Aplica el threshold
//            cvThreshold(grayFrame, processedFrame, 92, 255, CV_THRESH_BINARY);
//            cvThreshold(grayFrame, processedFrame, 0, 255,  CV_THRESH_TOZERO_INV | CV_THRESH_OTSU);
//            cvThreshold(grayFrame, processedFrame, 0, 255,   CV_THRESH_OTSU);
            // TODO: saber para que son esos parametros.
            cvAdaptiveThreshold(grayFrame, processedFrame, 255, CV_ADAPTIVE_THRESH_GAUSSIAN_C,
                CV_THRESH_BINARY, 91, 30.0);
            
            
            //cvWatershed(frame, frame);
            //cvAdaptiveThreshold(gray_converted, gray_frame, 200, CV_ADAPTIVE_THRESH_GAUSSIAN_C, CV_THRESH_BINARY, 5, 0);
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            // Muestra los dos frame, original y procesado..
            cvShowImage(VIDEO_NAME, frame );
            cvShowImage(PROC_VIDEO_NAME, processedFrame );
            
            // Esperar 10 ms entre frames.
            cvWaitKey(10);
            
            // Captura el siguiente frame.
            frame = cvQueryFrame(video);
        };
        
        cvReleaseCapture(video);
                
        // Destruir las ventanas creadas.
        cvDestroyWindow(VIDEO_NAME);
        cvDestroyWindow(PROC_VIDEO_NAME);
    }
            
}
