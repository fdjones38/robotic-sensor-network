package graficos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
/** 
 *
 * @author ALDAJO
 */
public class ControlToMove extends JPanel implements MouseListener, MouseMotionListener{
    
    //declaro las variables a utilizar
    private float width;                           //width of the window
    private float height;                          //height of the window
    private float diameter;                        //diameter reference for the circles
    private float diameterCircleMax;               //max diameter for the circle
    private float getPositionX;                    //position X, respect to the original coordenates
    private float getPositionY;                    //position Y, respect to the original coordenates
    double positionX;                               //position X, respect to the centre of the circle
    double positionY;                               //position Y, respect to the centre of the circle
    private float centreX;                         //new origin, respect to the centre of the circle
    private float centreY;                         //new origin, respect to the centre of the circle
    private float distanceToCentre;                //distance to the centre of the circunference
    private float distanceToCircleBorder;          //minumun distance to the border of the circunference
    private float angleRotationInToCircleRad;      //angle respect to the centre of the circunference
    private float angleRotationInToCircleGrad;     
    private float positionCentreX;
    private float positionCentreY;
    private float angleDegree;
    private boolean activate = false;               //activate de movement of the little circle
    
    public ControlToMove(){
        addMouseListener(this);
        addMouseMotionListener(this);
        setSize(500, 500);
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        //obtengo el alto y el ancho de la pantalla
        width = getWidth();
        height = getHeight();
        diameter = Math.min(width, height);
        centreX = width/2;
        centreY = diameter/2;
        diameterCircleMax = ((diameter*8)/10);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, (int)width, (int)height);
        //we traslate the origin to the centre
        g2.translate(centreX, centreY);
        g2.setColor(Color.getHSBColor((float)0.001, (float)0.001, (float)0.4));
        g2.fillOval((int)(-diameterCircleMax)/2, (int)(-diameterCircleMax)/2, (int)diameterCircleMax, (int)diameterCircleMax);
        g2.setColor(Color.BLACK);
        //we define a width for the lines
        g2.setStroke(new BasicStroke(5));
        //we draw a circle
        g2.drawOval((int)(-diameterCircleMax)/2, (int)(-diameterCircleMax)/2, (int)diameterCircleMax, (int)diameterCircleMax);
        
//        g2.setColor(Color.WHITE);
//        g2.fillOval((int)(-diameter*9)/20, (int)(-diameter*9)/20, (int)(diameter*9)/10, (int)(diameter*9)/10);
//        g2.setColor(Color.BLACK);
        
        //we draw a little circle
        if(activate){
        //return the centre of the circle
        g2.translate(-width/2, -diameter/2);
        g2.translate((int)positionX, (int)positionY);
//        g2.setColor(Color.WHITE);
//        g2.fillOval((int)(-diameter)/2, (int)(-diameter)/2, (int)(diameter), (int)(diameter));
//        g2.setColor(Color.BLACK);
        }
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.getHSBColor((float)0.001, (float)0.001, (float)0.3));        
        g2.fillOval((int)(-diameter)/12, (int)(-diameter)/12, (int)(diameter)/6, (int)(diameter)/6);
        g2.setColor(Color.getHSBColor((float)0.001, (float)0.001, (float)0.1));
        g2.fillOval((int)(-diameter)/20, (int)(-diameter)/20, (int)(diameter)/10, (int)(diameter)/10);
    }
    
    private double angleDegree(double degree, double x, double y){
        double valueDegree = 0;
        if(degree <= 0){
            if(x >= 0 && y <= 0){
                valueDegree = -degree;
            }else{
                valueDegree = 180-degree;
            }
            
        } else if(degree > 0){
            if(x >= 0 && y >= 0){
                valueDegree = 360-degree;
            }else{
                valueDegree = 180-degree;
            }
        }
        return valueDegree;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("hello");
    }

    @Override
    public void mousePressed(MouseEvent e) {
         
    }

    @Override 
    public void mouseReleased(MouseEvent e) {
        
        this.activate = false;
        this.positionCentreX = 0;
        this.positionCentreY = 0;
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        this.getPositionX = e.getX();
        this.getPositionY = e.getY();
        this.distanceToCentre = (float) Math.sqrt(Math.pow((positionX-centreX), 2)+Math.pow(positionY-centreY, 2));
        this.distanceToCircleBorder = distanceToCentre - (diameterCircleMax/2);
        this.angleRotationInToCircleRad = (float) Math.atan((getPositionY-centreY)/(getPositionX-centreX));
        this.angleRotationInToCircleGrad = (float) Math.toDegrees(angleRotationInToCircleRad);
        this.positionX = getPositionX;
        this.positionY = getPositionY;
        this.positionCentreX = (float) (positionX - centreX);
        this.positionCentreY = (float) (positionY - centreY);
        this.angleDegree = (float) angleDegree(angleRotationInToCircleGrad, positionCentreX, positionCentreY);
        if(distanceToCentre <= diameterCircleMax/2){
        this.activate=true;
        }else{
            activate = false;
        this.positionCentreX = 0;
        this.positionCentreY = 0;
//            this.positionX = getPositionX - (distanceToCircleBorder*Math.cos(angleRotationInToCircleRad));
//            this.positionY = getPositionY + (distanceToCircleBorder*Math.sin(angleRotationInToCircleRad));
        }
////        System.out.println("angle: " + angleDegree);
////        System.out.println("anglePropio: " + angleRotationInToCircleGrad);
////        System.out.println("xposition " + positionCentreX + " yposition " + positionCentreY);
//        System.out.println("posX = "+(getPositionX-centreX)+" posY= "+(getPositionY-centreY));
//        System.out.println("distance border = "+distanceToCircleBorder);
//        System.out.println("diameter "+ diameterCircleMax);
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public float getAngleDegree(){
        return angleDegree;
    }
    
    public float getXcordenate(){
        return positionCentreX;
    }
    
    public float getYcordenate(){
        return -positionCentreY;
    }
    
    public float getMaxValue(){
        return diameterCircleMax/2;
    }
    
}
