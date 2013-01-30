/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ALDAJO
 */
public class GraphicDataContainer2 extends JPanel {
    
    private BorderLayout borderLayout;
    private JLabel labelTitle;
    private ChartData2 graphicsData;
    private JButton button1, button2, button3;
    private JPanel panelButtons, panelTitle;
    private final int Gap = 5;
    public final static int PREFERED_WIDTH = 400;
    public final static int PREFERED_HEIGHT = 400;
    private boolean flag = true;
    
    public GraphicDataContainer2(){
        initComponents();
    }

    public GraphicDataContainer2(String title, int cantLines){
        graphicsData = new ChartData2(10000, cantLines);
        initComponents();
        initCharacteristics();
        initActions();
        labelTitle.setText(title);
    }
    
    private void initComponents(){
        borderLayout = new BorderLayout(Gap, Gap);
        labelTitle = new JLabel();
        button1 = new JButton("STOP");
        button2 = new JButton("Button2");
        button3 = new JButton("Button3");
        panelButtons = new JPanel();
        panelTitle = new JPanel();
        
        setLayout(borderLayout);
        
        panelTitle.add(labelTitle);
        add(panelTitle, BorderLayout.NORTH);
        add(graphicsData, BorderLayout.CENTER);
        panelButtons.add(button1);
        panelButtons.add(button2);
        panelButtons.add(button3);
        add(panelButtons, BorderLayout.SOUTH);
    }
    
    private void initCharacteristics(){
        setMinimumSize(new Dimension(560, 340));
        setPreferredSize(new Dimension(800, 340));
        setBackground(Color.GRAY);
        panelButtons.setBackground(Color.GRAY);
        panelTitle.setBackground(Color.GRAY);
        labelTitle.setFont(new Font("Comic Sans", Font.BOLD, 14));
    }
    
    private void initActions(){
        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButton1Clicked(e);
            }
        });
        
        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButton2Clicked(e);
            }
        });
        button3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButton3Clicked(e);
            }
        });
    }
    
    public void onButton1Clicked(ActionEvent e){
        flag = !flag;
        if(flag){
            button1.setText("STOP");
        }else{
            button1.setText("CONTINUE");
        }
    }
    
    public void onButton2Clicked(ActionEvent e){
//        graphicsData.addData(Math.random()*100, 0);
//        graphicsData.addData(Math.random()*100, 1);
    }
    
    public void onButton3Clicked(ActionEvent e){
        
    }
    
    public void addData(float value, int line){
        if(flag){
            graphicsData.addData(value, line);
        }
    }
    
    public void setTitle(String title){
        labelTitle.setText(title);
    }
    
    @Override
    public void setEnabled(boolean flag){
        button1.setEnabled(flag);
        button2.setEnabled(flag);
        button3.setEnabled(flag);
    }
}
