/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author ALDAJO
 */
public class VentanaAplicacion3 extends JFrame {

    private JTabbedPane tabPane;
    private Box boxPanel1, boxPanel2;
    private JPanel internalPanel1, radioButtonsPanel;
    private JLabel label1, label2, label3;
    private JSlider slider1, slider2;
    private JComboBox combox1;
    private JRadioButton[] radioButtons;
    private ButtonGroup radioButtonGroup;
    private String[] namesRadioButtons = {"Names", "Address64"};
    
    public VentanaAplicacion3(){
        super("Aplicacion 3");
        initComponenets();
        initActions();
        initCharacteristics();
    }
    
    private void initComponenets(){
        tabPane = new JTabbedPane();
        boxPanel1 = Box.createVerticalBox();
        boxPanel2 = Box.createVerticalBox();
        internalPanel1 = new JPanel();
        radioButtonsPanel = new JPanel();
        label1 = new JLabel("Label 1");
        label2 = new JLabel("Label 2");
        label3 = new JLabel("Label 3");
        slider1 = new JSlider(SwingConstants.HORIZONTAL, 0, 1023, 0);
        slider2 = new JSlider(SwingConstants.HORIZONTAL, 0, 1023, 0);
        combox1 = new JComboBox();
        radioButtons = new JRadioButton[namesRadioButtons.length];
        radioButtonGroup = new ButtonGroup();
        for(int i = 0; i<radioButtons.length; i++){
            radioButtons[i] = new JRadioButton(namesRadioButtons[i]);
            radioButtonGroup.add(radioButtons[i]);
            radioButtonsPanel.add(radioButtons[i]);
        }
        
        boxPanel1.add(label1);
        boxPanel1.add(slider1);
        boxPanel1.add(label2);
        boxPanel1.add(slider2);
        tabPane.add("Control", boxPanel1);
        
        internalPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        internalPanel1.add(label3);
        boxPanel2.add(internalPanel1);
        boxPanel2.add(combox1);
        boxPanel2.add(radioButtonsPanel);
        boxPanel2.add(Box.createVerticalStrut(50));
        tabPane.add("Options", boxPanel2);
        
        add(tabPane);
    }
    
    private void initCharacteristics(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocation(700, 300);
        setResizable(false);
        slider1.setMajorTickSpacing(10);
        slider2.setMajorTickSpacing(10);
    }
    
    private void initActions(){
        slider1.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                onSlider1Changed(e);
            }
        });
        
        slider2.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                onSlider2Changed(e);
            }
        });
        
        combox1.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    onSelectedItemCombox(e);
                }
            }
        });
        
        radioButtons[0].addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                RadioButton1(e);
            }
        });
        
        radioButtons[1].addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                RadioButton2(e);
            }
        });
    }
    
    public void addElementCombox1(Object item){
        combox1.addItem(item);
    }
    
    public void removeElementsCombox(){
        combox1.removeAllItems();
    }
    
    public void enabledCombox(boolean cond){
        combox1.setEnabled(cond);
    }
    
    public Object getSelectItemCombox(){
        return combox1.getSelectedItem();
    }
    
    public int getValueSlider1(){
        return slider1.getValue();
    }
    
    public int getValueSlider2(){
        return slider2.getValue();
    }
    
    public void onSlider1Changed(ChangeEvent e){
        
    }
    
    public void onSlider2Changed(ChangeEvent e){
        
    }
    
    public void onSelectedItemCombox(ItemEvent e){
        
    }
    
    public void RadioButton1(ItemEvent e){
        
    }
    
    public void RadioButton2(ItemEvent e){
        
    }
}
