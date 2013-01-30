/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author ALDAJO
 */
public class VentanaAplicacion1 extends JFrame {
    private JTabbedPane tabbedpane1;
    private Box box1;
    private JPanel panelControl, panelButtons, panelButonsSlider,
            panel1, panel2, panelRadioButtons, panelControlAndTabbet,
            optionButtonPanel, panelGraphicData;
    private ControlToMove control1;
    private JTextArea text;
    private JLabel label1;
    private JButton buttonLeft, buttonRight, button1, button2, button3;
    private JScrollPane scrollpanetext;
    private JComboBox combox1;
    private JRadioButton[] radioButtons;
    private ButtonGroup radioButtonGroup;
    private JSlider slider1;
    private GraphicDataContainer2 graphicData;
    
    private String[] namesRadioButtons = {"Names", "Address64"};

    public VentanaAplicacion1() throws HeadlessException {
        super("Aplicacion1");
        initComponents();
        initCharacteristics();
        initActions();
    }
    
    private void initActions(){
        control1.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                onControlMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        control1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                onControlMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                onControlMouseRelased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        buttonLeft.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                onButtonLeftPressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                onButtonLeftRelased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        buttonRight.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                onButtonRightPressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                onButtonRightRelased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onButton1Click(e);
            }
        });
        
        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButton2Click(e);
            }
        });
        
        button3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButton3Click(e);
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
        
        combox1.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    onSelectedItemCombox(e);
                }
            }
        });
        
        slider1.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                onSliderChanged(e);
            }
        });
    }
    
    private void initComponents(){
        setLayout(new GridLayout(1, 2, 5, 5));
        //Contenedores
        tabbedpane1 = new JTabbedPane();
        panelControl = new JPanel();
        panelButtons = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panelRadioButtons = new JPanel();
        panelButonsSlider = new JPanel();
        box1 = Box.createVerticalBox();
        scrollpanetext = new JScrollPane();
        panelControlAndTabbet = new JPanel();
        //panelControlAndTabbet = Box.createHorizontalBox();
        optionButtonPanel = new JPanel();
        panelGraphicData = new JPanel();
        //elementos
        control1 = new ControlToMove();
        text = new JTextArea();
        label1 = new JLabel("Label 1");
        String[] a = {"Sin elementos"};
        combox1 = new JComboBox(a);
        buttonLeft = new JButton("Button Left");
        buttonRight = new JButton("Button Right");
        button1 = new JButton("button 1");
        button2 = new JButton("button 2");
        button3 = new JButton("button 3");
        radioButtons = new JRadioButton[namesRadioButtons.length];
        radioButtonGroup = new ButtonGroup();
        slider1 = new JSlider(SwingConstants.HORIZONTAL, -90, 90, 0);
//        graphicData = new GraphicDataContainer2("title", 3);
//        panelGraphicData.add(graphicData);
        
        panelRadioButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        for(int i=0; i<radioButtons.length; i++){
            radioButtons[i] = new JRadioButton(namesRadioButtons[i], false);
            radioButtonGroup.add(radioButtons[i]);
            panelRadioButtons.add(radioButtons[i]);
        }
        
        panelControl.setLayout(new BorderLayout(5, 5));
        panelButtons.setLayout(new GridLayout(1, 2, 5, 5));
        panelButonsSlider.setLayout(new GridLayout(2, 1));
        panelControl.add(control1, BorderLayout.CENTER);
        panelButtons.add(buttonLeft);
        panelButtons.add(buttonRight);
        panelButonsSlider.add(panelButtons);
        panelButonsSlider.add(slider1);
        panelControl.add(panelButonsSlider, BorderLayout.SOUTH);
        
        panel1.setLayout(new BorderLayout(10, 10));
        scrollpanetext.setViewportView(text);
        panel1.add(scrollpanetext, BorderLayout.CENTER);
        optionButtonPanel.add(button1);
        optionButtonPanel.add(button2);
        panel1.add(optionButtonPanel, BorderLayout.SOUTH);
        tabbedpane1.addTab("Console",panel1);
        
        box1.add(label1);
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        box1.add(combox1);
        box1.add(panelRadioButtons);
        panel2.add(button3);
        box1.add(panel2);
        box1.add(Box.createVerticalStrut(500));
        tabbedpane1.addTab("GraphicData", new JPanel());
        tabbedpane1.addTab("Options",box1);
        
        panelControlAndTabbet.setLayout(new GridLayout(1, 2));
        panelControlAndTabbet.add(panelControl);
        panelControlAndTabbet.add(tabbedpane1);
        
        add(panelControlAndTabbet);
    }
    
    private void initCharacteristics(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1300, 550);
        setResizable(false);
        setLocation(30,100);
        text.setBackground(Color.BLACK);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Lucida Console", Font.ROMAN_BASELINE, 14));
        text.setEditable(false);
        combox1.addItem("Sin elementos");
        setLineAreaText("WELCOME TO HERGO APLICATION!!");
        slider1.setMajorTickSpacing(10);
        //graphicData.setEnabled(false);
    }
    
    public float getControlXcoordenate(){
        return (control1.getXcordenate()/control1.getMaxValue())*100;
    }
    
    public float getControlYcoordenate(){
        return (control1.getYcordenate()/control1.getMaxValue())*90;
    }
    
    public float getControlMaxValue(){
        return control1.getMaxValue();
    }
    
    public Object getSelectedItemCombox(){
        return combox1.getSelectedItem();
    }
    
    public void setLineAreaText(String line){
        text.setText(text.getText()+line+"\n");
    }
    
    public void setNameButton1(String name){
        button1.setText(name);
    }
    
    public void setNameButton3(String name){
        button3.setText(name);
    }
    
    public void setNameLabel1(String text){
        label1.setText(text);
    }
    
    public void addElementCombox(Object item){
        combox1.addItem(item);
    }
    
    public void removeElementsCombox(){
        combox1.removeAllItems();
    }
    
    public void clearTextArea(){
        text.setText("");
    }
    
    public void enableCombox(boolean condition){
        combox1.setEnabled(condition);
    }
    
    public void addGraphicData(GraphicDataContainer2 gD){
        graphicData = gD;
        panelGraphicData.removeAll();
        panelGraphicData.add(graphicData);
    }
    
    public int getValueSlider(){
        return slider1.getValue();
    }
    
    public void onControlMousePressed(MouseEvent e){
        
    }
    
    public void onControlMouseRelased(MouseEvent e){
        
    }
    
    public void onControlMouseDragged(MouseEvent e){
        
    }
    
    public void onButtonLeftPressed(MouseEvent e){
        
    }
    
    public void onButtonLeftRelased(MouseEvent e){
        
    }
    
    public void onButtonRightPressed(MouseEvent e){
        
    }
    
    public void onButtonRightRelased(MouseEvent e){
        
    }
    
    public void onButton1Click(ActionEvent e){
        
    }
    
    public void onButton2Click(ActionEvent e){
//        graphicData = new GraphicDataContainer2("T", 2);
//        tabbedpane1.setComponentAt(1, graphicData);
    }
    
    public void onButton3Click(ActionEvent e){
        
    }
    
    public void RadioButton1(ItemEvent e){
        
    }
    
    public void RadioButton2(ItemEvent e){
        
    }
    
    public void onSelectedItemCombox(ItemEvent e){
        
    }
    
    public void onSliderChanged(ChangeEvent e){
        
    }
}
