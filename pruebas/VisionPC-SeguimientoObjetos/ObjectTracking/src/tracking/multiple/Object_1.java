/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tracking.multiple;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvFlip;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvScalar;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_MEDIAN;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGetCentralMoment;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGetSpatialMoment;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvMoments;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvSmooth;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.VideoInputFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;

/**
 * Este software está basado en un algoritmo tomado de: http://ganeshtiwaridotcomdotnp.blogspot.com/2012/04/colored-object-tracking-in-java-javacv.html
 * Esta clase utiliza las herramientas de javacv para el reconocimiento de objetos de color ROJO.
 * 
 * @author Leidy Tatiana Soto M.
 */

public class Object_1 implements Runnable {

    private final int INTERVAL = 5; //Intervalo de tiempo para el hilo.
    private final int CAMERA_NUM = 0;   //0 es el valor por defecto para la cámara. Si se quiere utilizar otra cámara, el valor para ésta sería 1.
    
    /**
     * Se define el intervalo de colores a reconocer en la imagen.
     * El color para este objeto es el ROJO que va de un tono oscuro (rgba_minRED)
     * a un tono más claro (rgba_maxRED).
     * La clase cvScalar permite definir la escala de colores a identificar en la imagen,
     * los parámeros que recibe son: blue, green, red, unusedValor.
     */
    static CvScalar rgba_minRED = cvScalar(0, 0, 130, 0); //Oscuro.
    static CvScalar rgba_maxRED = cvScalar(80, 80, 255, 0); //Claro.
    
    private Thread thread; //Hilo para grabación y reconocimiento del objeto.
    private int ii = 0;    //Contador de imagenes capturadas.
    
    public Object_1(){
        thread = new Thread(this, "Object 1: Red."); //Se crea el hilo.
        thread.start(); //Se inicializa el hilo.
    }
    
    @Override
    public void run() {
        FrameGrabber grabber = new VideoInputFrameGrabber(CAMERA_NUM); //Objeto que permite capturar la imagen de la webcam.
        try {
            grabber.start(); //Inicia la grabación.
            IplImage img;
            
            //Coordenadas del objeto reconocido.
            int posX = 0;
            int posY = 0;
            while (true) {
                img = grabber.grab(); //Se guarda la imagen capturada desde la cámara.
                if (img != null) {
                    cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
                    TrackingMain.showVideo(img); //Se muestra la imagen en pantalla.
                    IplImage detectThrs = getThresholdImage(img);

                    CvMoments moments = new CvMoments();
                    cvMoments(detectThrs, moments, 1);
                    double mom10 = cvGetSpatialMoment(moments, 1, 0);
                    double mom01 = cvGetSpatialMoment(moments, 0, 1);
                    double area = cvGetCentralMoment(moments, 0, 0);
                    posX = (int) (mom10 / area);
                    posY = (int) (mom01 / area);
                    //Si la posición del objeto es válida se pinta en el panel.
                    if (posX > 0 && posY > 0) {
                        TrackingMain.paint(img, posX, posY, Color.RED);
                    }
                }
                Thread.sleep(INTERVAL); //Ponemos a dormir el hilo.
            }
        } catch (Exception e) {
            System.out.println("Problemas con el hilo");
        }
    }
    
    /*
     * Este método analiza la imagen para comprobar que en ella haya un objeto
     * en la escala de colores definida. De la imagen que retorna se obtienen las coordenadas
     * del objeto reconocido, mediante la clase cvMoments.
     */
    private IplImage getThresholdImage(IplImage orgImg) {
        IplImage imgThreshold = cvCreateImage(cvGetSize(orgImg), 8, 1);
        cvInRangeS(orgImg, rgba_minRED, rgba_maxRED, imgThreshold);
        cvSmooth(imgThreshold, imgThreshold, CV_MEDIAN, 15);
        cvSaveImage(++ii + "dsmthreshold.jpg", imgThreshold);
        return imgThreshold;
    }
    
    /*
     * La ecualización del histograma de una imagen es una transformación que pretende 
     * obtener para una imagen un histograma con una distribución uniforme. Es decir, 
     * que exista el mismo número de pixels para cada nivel de gris del histograma de 
     * una imagen monocroma. En teoría, la aplicación de esta operación debería transformar 
     * el histograma en otro con una forma perfectamente uniforme sobre todos los niveles de gris.
     * Tomado de: http://es.wikipedia.org/wiki/Ecualizaci%C3%B3n_del_histograma
     */
    public IplImage Equalize(BufferedImage bufferedimg) {
        IplImage iploriginal = IplImage.createFrom(bufferedimg);
        IplImage srcimg = IplImage.create(iploriginal.width(), iploriginal.height(), IPL_DEPTH_8U, 1);
        IplImage destimg = IplImage.create(iploriginal.width(), iploriginal.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(iploriginal, srcimg, CV_BGR2GRAY); //La imagen se transforma a una escala de grises para obtener velocidad de procesamiento.
        cvEqualizeHist(srcimg, destimg);
        return destimg;
    }
    
}
