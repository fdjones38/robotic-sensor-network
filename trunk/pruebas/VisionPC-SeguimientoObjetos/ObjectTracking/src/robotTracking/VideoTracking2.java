/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robotTracking;

import com.googlecode.javacpp.Loader;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author dav
 */
public class VideoTracking2 {

    public static final String VIDEO_NAME = "Video original";
    public static final String PROC_VIDEO_NAME = "Video procesado";
    private static final double RELATION_MAX = 1.9;
    private static final double RELATION_MIN = 1.2;
    
    public static void main(String args[]) {

        // Cargar el video.
        CvCapture video = cvCreateFileCapture("robot2.avi");

        // Crear la ventana del video normal.
        cvNamedWindow(VIDEO_NAME, CV_WINDOW_AUTOSIZE);
        
        // Crear la ventana del video procesado.
        //cvNamedWindow(PROC_VIDEO_NAME, CV_WINDOW_AUTOSIZE);

        // Se captura el primer frame.
        IplImage frame;
        frame = cvQueryFrame(video);

        double fps = 25;
        double desiredFps = 6;
        
        int frec = (int) Math.ceil(fps / desiredFps);
        int frameCounter = 1;
        while (frame != null) {
            
            //Esta condición indica cada cuantos frames 
            if ( frameCounter % frec == 0 ){
                
                // Imagen en escala de grises vacia.
                IplImage grayFrame = cvCreateImage(cvGetSize(frame), IPL_DEPTH_8U, 1);
                // Imagen en escala de grises vacia.
                IplImage processedFrame = cvCreateImage(cvGetSize(frame), IPL_DEPTH_8U, 1);
                // Convierte el frame en esacala de grises.
                cvCvtColor(frame, grayFrame, CV_RGB2GRAY);
                
                //Smooth
                cvSmooth(grayFrame, grayFrame, CV_GAUSSIAN, 3);

                // Aplica el threshold
                // TODO: saber para que son esos parametros 91 y 30.
                cvAdaptiveThreshold(grayFrame, processedFrame, 255, CV_ADAPTIVE_THRESH_GAUSSIAN_C,
                        CV_THRESH_BINARY, 91, 30.0);

                // Storage
                CvMemStorage storage = cvCreateMemStorage(0);

                // First contour
                CvSeq firstCont = new CvContour();

                //cvCanny(processedFrame, processedFrame, 10, 200, 3);

                // Find contours and return the number of contours.
                //    CV_CHAIN_APPROX_SIMPLE compresses horizontal, vertical, and diagonal segments, that is, the function leaves only their ending points;
                int numContours = cvFindContours(processedFrame, storage, firstCont, Loader.sizeof(CvContour.class),
                        CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE);

                
                //Creamos una cola para recorrer todo el árbol de contornos
                Queue<CvSeq> queue = new ArrayDeque<CvSeq>();
                queue.add(firstCont);
                while( queue.size() > 0 ){
                    
                    //Obtenemos el siguiente elemento de la cola
                    //y lo sacamos de ella.
                    CvSeq el = queue.poll();                
                    
                    //Con las siguientes linas garantizamos que estamos recorriendo todos
                    //los elementos del árbol. Es necesario que esto se haga en forma
                    //de árbol para poder saber cuantos elementos hay dentro de un contorno
                    //con el método "numHoles"
                    
                    //Agregamos a la cola los que están en el mismo nivel                
                    for ( CvSeq  c_h = el.h_next(); c_h != null; c_h = c_h.h_next() ){
                        queue.add(c_h);
                    }

                    //Agregamos el primer elemento del nivel interior.
                    CvSeq c_v = el.v_next();
                    if ( c_v != null ){
                        queue.add(c_v);
                    }
                    
                    
                    //-----------------------------------------------------------------------
                    //Averiguamos si el contorno es una elipse                
                    CvBox2D robotEllipse;
                    if ( (robotEllipse = getRobotEllipse(el)) != null ){                    

                        int holes = numHoles(el);
                        if ( holes > 0 ){
                            // Draw contourns                                            
                            cvDrawContours(
                                    frame,
                                    el, CvScalar.RED, CvScalar.BLUE, 0, 1, CV_AA);                            
                            
                            //Dibujamos un texto con el ID del robot
                            CvPoint centerPoint = cvPoint((int)  robotEllipse.center().x(), (int) robotEllipse.center().y());
                            cvPutText(frame, "Robot" + (holes),
                                    centerPoint, new CvFont(CV_FONT_HERSHEY_PLAIN, 1, 1),
                                    new CvScalar());
                                }


                    }
                }
            

            }
            // Muestra los dos frame, original y procesado..
            cvShowImage(VIDEO_NAME, frame);
            //cvShowImage(PROC_VIDEO_NAME, processedFrame);

            // Esperar 10 ms entre frames.
            cvWaitKey(30);

            // Captura el siguiente frame.
            frame = cvQueryFrame(video);
            
            frameCounter++;
        }

        cvReleaseCapture(video);

        // Destruir las ventanas creadas.
        cvDestroyWindow(VIDEO_NAME);
        cvDestroyWindow(PROC_VIDEO_NAME);
    }
    
    public static CvBox2D getRobotEllipse(CvSeq contour){
        
        if ( contour.total() > 10 ){
        
            CvBox2D e = cvFitEllipse2(contour);            
            
            double axisRealation = e.size().height() / e.size().width();

            return (axisRealation > RELATION_MIN && axisRealation < RELATION_MAX) ? e : null;            
        }else{
            return null;
        }
    }
    
    public static int numHoles(CvSeq contour){
        CvSeq child = contour.v_next();
        if ( child != null ){
            int count = 1;
            for ( CvSeq n = child.h_next(); n != null; n = n.h_next() ){
                count++;
            }
            return count;
        }else{
            return 0;
        }
    }
}
