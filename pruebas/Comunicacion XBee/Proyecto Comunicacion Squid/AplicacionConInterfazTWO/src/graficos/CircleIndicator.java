/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author ALDAJO
 */
public class CircleIndicator extends JPanel {
    
    private Color circleColor = Color.WHITE;
    
    public CircleIndicator() {
        setPreferredSize(new Dimension(40, 40));
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(circleColor);
        int minvalue = Math.min(getWidth(), getHeight());
        float radiusCircle = (minvalue*6)/20;
        float diameterCircle = radiusCircle*2;
        float middleWidth = getWidth()/2;
        float middleHeight = getHeight()/2;
        g.translate((int)middleWidth, (int)middleHeight);
        g.fillOval(-(int)radiusCircle, -(int)radiusCircle, (int)diameterCircle, (int)diameterCircle);
        g.setColor(Color.BLACK);
        g.drawOval(-(int)radiusCircle, -(int)radiusCircle, (int)diameterCircle, (int)diameterCircle);
    }
    
    public void on(){
        circleColor = Color.RED;
        repaint();
    }
    
    public void off(){
        circleColor = Color.WHITE;
        repaint();
    }
    
}
