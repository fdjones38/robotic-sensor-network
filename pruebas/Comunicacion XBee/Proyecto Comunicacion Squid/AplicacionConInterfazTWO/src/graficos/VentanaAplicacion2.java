/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
/**
 *
 * @author ALDAJO
 */
public class VentanaAplicacion2 extends JFrame{
    
    private JPanel panel;
    private JScrollPane scrollPane;
    private GridLayout layout;
    private int counter = 0;
    
    
    public VentanaAplicacion2(){
        super();
        initComponents();
        initCharacteristics();
    }
    
    private void initComponents(){
        panel = new JPanel();
        scrollPane = new JScrollPane();
        layout = new GridLayout(2, 1, 3, 3);
        panel.setLayout(layout);
        scrollPane.setViewportView(panel);
        add(scrollPane);
    }
    
    private void initCharacteristics(){
        panel.setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    public void addGraphicDataContainer(GraphicDataContainer2 gPanel){
        counter++;
        int currentColumns = layout.getColumns();
        if(counter > (2*currentColumns)){
            layout.setColumns(currentColumns+1);
        }
        panel.add(gPanel);
        setSize(panel.getPreferredSize());
    }
    
}
