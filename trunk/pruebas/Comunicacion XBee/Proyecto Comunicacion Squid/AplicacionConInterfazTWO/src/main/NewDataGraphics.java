/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import graficos.*;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
/**
 *
 * @author ALDAJO
 */
public class NewDataGraphics {
    public static void main(String[] args) {
//        int width = GraphicDataContainer.PREFERED_WIDTH;
//        int height = GraphicDataContainer.PREFERED_HEIGHT;
        GraphicDataContainer2[] win1 = new GraphicDataContainer2[10];
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();
        panel.setLayout(new GridLayout(2, 6, 3, 3));
        panel.setBackground(Color.BLACK);
        
        for(int i=0; i<win1.length;i++){
            win1[i] = new GraphicDataContainer2("DEVICE "+ (i+1), 2);
            panel.add(win1[i]);
        }
        frame.setSize(panel.getPreferredSize());
        scrollPane.setViewportView(panel);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
