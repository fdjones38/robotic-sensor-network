/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author ALDAJO
 */
public class Aplicacion4 extends JFrame {

    JTextArea area1;
    JScrollPane scrollPane1;
    
    public Aplicacion4(){
        super("Aplicacion4");
        area1 = new JTextArea();
        scrollPane1 = new JScrollPane();
        
        area1.setBackground(Color.BLACK);
        area1.setForeground(Color.WHITE);
        area1.setFont(new Font("Lucida Console", Font.ROMAN_BASELINE, 12));
        
        scrollPane1.setViewportView(area1);
        add(scrollPane1);
        
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(new Dimension(300, 300));
    }
    
    public void addText(String text){
        area1.setText(area1.getText()+text+"\n");
    }
}
