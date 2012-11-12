
package tracking.multiple;


import java.awt.Color;
import java.awt.Graphics;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.CanvasFrame;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Este software está basado en un algoritmo tomado de:
 * http://ganeshtiwaridotcomdotnp.blogspot.com/2012/04/colored-object-tracking-in-java-javacv.html
 *
 * TrakingMain es la clase principal, se encarga de mostrar en pantalla la
 * imagen obtenida desde la webcam y la trayectoria de los objetos detectados en
 * la imagen.
 *
 * @author Leidy Tatiana Soto M.
 */
public class TrackingMain extends JFrame {

    private static JPanel panel;        //Panel donde se dibujan las rutas.
    private static CanvasFrame video;   //Frame donde se muestra la imagen de la webcam.
    private JFrame paint;               //Contenedor del dibujo de rutas.
    private Object_1 redObject;         //Objecto que detecta objetos de color ROJO.
    private Object_2 blueObject;        //Objecto que detecta objetos de color AZUL.

    public TrackingMain() {
        //Se crean todos los Frame con propiedades de tamaño y de localización en pantalla.
        video = new CanvasFrame("Web Cam Live");
        paint = new JFrame("Detection");
        panel = (JPanel) paint.getContentPane();
        paint.setContentPane(panel);
        video.setSize(500, 500);
        paint.setSize(800, 720);
        paint.setLocation(0, 0);
        video.setLocation(810, 140);
        video.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paint.setVisible(true);

        redObject = new Object_1();
        blueObject = new Object_2();
    }

    /*
     * Este método dibuja la ruta de los objetos detectados en la imagen.
     */
    public static void paint(IplImage img, int posX, int posY, Color color) {
        Graphics g = panel.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        g.setColor(color);
        g.drawOval(posX, posY, 10, 10);
        System.out.println(posX + " , " + posY);
    }

    /*
     * Este método muestra en una ventana la igagen capturada por la webcam.
     * Esta imagen está siendo capturada en la clase Object_1, la cual se encarga
     * de llamar a este método y actualizar a image.
     */
    public static void showVideo(IplImage image) {
        video.showImage(image);
    }

    public static void main(String[] args) {
        new TrackingMain();
    }
}
