/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

//import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 *
 * @author ALDAJO
 */
public class VentanaPrincipal extends JFrame {
    
    //Paneles
    private JPanel panelArea1, panelArea2, panelAreas,
            panelBotones1, panelBotones2, panelGeneralBotonesG,
            panelIndicator, panelLabelIndicator;
    private Box contenedorBotonesG;
    //Etiquetas
    private JLabel label1, label2, label3, label4, labelIndicator;
    private JComboBox listElements;
    //Botones
    private JButton button1, button2, button3, button4, button5;
    private JButton[] buttonsG;
    //Areas de texto
    private JTextArea areaTexto1, areaTexto2;
    //nombre para el arreglo de botonesG
    private String[] nombreBotonesG = {"Aplicacion 1","Aplicacion 2", "Aplicacion 3", "Aplicacion 4", "Aplicacion 5", "Aplicacion 6", "Aplicacion 7"};
    private JScrollPane scrollPane1, scrollPane2;
    
    private CircleIndicator circle;
    
    public VentanaPrincipal(){
        super("Aplicación");
        initComponents();
        initActions();
        initCharacteristics();
    }
    
    private void initComponents (){
        setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000,600));
        setLocation(200,100);
        panelArea1 = new JPanel();
        panelArea2 = new JPanel();
        panelAreas = new JPanel();
        panelBotones1 = new JPanel();
        panelBotones2 = new JPanel();
        panelGeneralBotonesG = new JPanel();
        panelIndicator = new JPanel();
        panelLabelIndicator = new JPanel();
        contenedorBotonesG = Box.createVerticalBox();
        circle = new CircleIndicator();
        
        label1 = new JLabel("Label 1");
        label2 = new JLabel("Label 2");
        label3 = new JLabel("Label 3");
        label4 = new JLabel("Label 4");
        labelIndicator = new JLabel("Indicator Data");
        
        button1 = new JButton("Button1");              //Boton1
        button2 = new JButton("Button2");                 //Boton2
        button3 = new JButton("Button3");            //Boton3
        button4 = new JButton("Button4");                 //Boton4
        button5 = new JButton("Button5");                 //Boton4
        buttonsG = new JButton[nombreBotonesG.length];
        
        listElements = new JComboBox();
        
        scrollPane1 = new JScrollPane();
        scrollPane2 = new JScrollPane();
                
        areaTexto1 = new JTextArea();
        areaTexto2 = new JTextArea();
        
        //primer panel para la ventana
        panelGeneralBotonesG.setLayout(new BorderLayout(5,5));
        //establecer como layout el gridbagLayout
        //panelBotonesG.setLayout(new GridLayout(botonesG.length,1,10,10));
        
        for(int i=0; i<buttonsG.length; i++){
            buttonsG[i] = new JButton(nombreBotonesG[i]);
            buttonsG[i].setMaximumSize(new Dimension(200, 30));
            //panelBotonesG.add(botonesG[i]);
            contenedorBotonesG.add(buttonsG[i]);
            contenedorBotonesG.add(Box.createVerticalStrut(5));
        }
        panelGeneralBotonesG.add(contenedorBotonesG, BorderLayout.CENTER);
        panelGeneralBotonesG.add(label3, BorderLayout.NORTH);
        panelIndicator.setLayout(new BorderLayout(1, 1));
        panelLabelIndicator.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelLabelIndicator.add(labelIndicator);
        panelIndicator.add(panelLabelIndicator, BorderLayout.NORTH);
        panelIndicator.add(circle, BorderLayout.CENTER);
        panelGeneralBotonesG.add(panelIndicator, BorderLayout.SOUTH);
        
        //segundo panel para la ventana
        panelBotones1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBotones1.add(button1);
        panelBotones1.add(listElements);
        panelBotones1.add(button2);
        panelArea1.setLayout(new BorderLayout(5,5));
        panelArea1.add(label1, BorderLayout.NORTH);
        scrollPane1.setViewportView(areaTexto1);
        panelArea1.add(scrollPane1, BorderLayout.CENTER);
        panelArea1.add(panelBotones1, BorderLayout.SOUTH);
        
        //tercer panel para la ventana
        panelBotones2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));        
        panelBotones2.add(button3);
        panelBotones2.add(button4);
        panelBotones2.add(button5);
        panelBotones2.add(label4);
        panelArea2.setLayout(new BorderLayout(5,5));
        panelArea2.add(label2, BorderLayout.NORTH);
        scrollPane2.setViewportView(areaTexto2);
        panelArea2.add(scrollPane2, BorderLayout.CENTER);
        panelArea2.add(panelBotones2, BorderLayout.SOUTH);
        
        //panel que contiene al segundo y el tercero
        panelAreas.setLayout(new GridLayout(1, 2, 10, 10));
        panelAreas.add(panelArea1);
        panelAreas.add(panelArea2);
        
        //añadimos los paneles a la ventana
        add(panelGeneralBotonesG, BorderLayout.WEST);
        add(panelAreas,BorderLayout.CENTER);
    }
    
    private void initCharacteristics(){
        areaTexto1.setBackground(Color.BLACK);
        areaTexto2.setBackground(Color.BLACK);
        areaTexto1.setEditable(false);
        areaTexto2.setEditable(false);
        areaTexto1.setFont(new Font("Arial", Font.ROMAN_BASELINE, 14));
        areaTexto2.setFont(new Font("Lucida Console", Font.ROMAN_BASELINE, 14));
        areaTexto1.setForeground(Color.white);
        areaTexto2.setForeground(Color.white);
        areaTexto1.setColumns(30);
        areaTexto2.setText("WELCOME...\n");
        listElements.addItem("no hay direcciones");
        label4.setFont(new Font("Lucida Console", Font.ROMAN_BASELINE, 14));
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
                accionBoton2(e);
            }
        });
        
        button2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                accionBoton2Presionado(e);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                accionBoton2Soltar(e);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        button3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButton3Clicked(e);
            }
        });
        
        button4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButton4Clicked(e);
            }
        });
        
        button5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButton5Clicked(e);
            }
        });
        
        buttonsG[0].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButtonG0Clicked(e);
            }
        });
        
        buttonsG[1].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButtonG1Clicked(e);
            }
        });
        
        buttonsG[2].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onButtonG2Clicked(e);
            }
        });
    }
    
    public void enableButtons12G(boolean condition){
        button1.setEnabled(condition);
        button2.setEnabled(condition);
        listElements.setEnabled(condition);
        for(JButton i:buttonsG){
            i.setEnabled(condition);
        }
    }
    
    public void enableButton3(boolean condition){
        button3.setEnabled(condition);
        //boton4.setEnabled(condition);
    }
    
    public void setLineAreaTexto1(String col1, String col3){
        areaTexto1.setText(areaTexto1.getText() + col1 +"\t\t\t" + col3 + "\n");
    }
    
    public void setLineAreaTexto1(String col1, String col2, String col3){
        areaTexto1.setText(areaTexto1.getText() + col1 +"\t"+ col2 +"\t\t" + col3 + "\n");
    }
    
    public void setLineAreaTexto2(String line){
        areaTexto2.setText(areaTexto2.getText() + line + "\n");
    }
    
    public void clearAreaTexto1(){
        areaTexto1.setText("");
    }
    
    public void clearAreaTexto2(){
        areaTexto2.setText("");
    }
    
    public void circleIndicatorOn(){
        circle.on();
    }
    
    public  void circleIndicatorOff(){
        circle.off();
    }
    
    public void addElementJCombox(Object item){
        listElements.addItem(item);
    }
    
    public void removeElementsJCombox(){
        listElements.removeAllItems();
    }
    
    public void addNameButton1(String name){
        button1.setText(name);
    }
    
    public void addNameButton2(String name){
        button2.setText(name);
    }
    
    public void addNameButton3(String name){
        button3.setText(name);
    }
    
    public void addNameButton4(String name){
        button4.setText(name);
    }
    
    public void addNameButton5(String name){
        button5.setText(name);
    }
    
    public void addNameEtiqueta(String title){
        label3.setText(title);
    }
    
    public void addNameEtiqueta1(String title){
        label1.setText(title);
    }
    
    public void addNameEtiqueta2(String title){
        label2.setText(title);
    }
    
    public void addNameEtiqueta4(String name){
        label4.setText(name);
    }
    
    public Object getSelectItemJCombox(){
        return listElements.getSelectedItem();
    }
    
    public void onButton1Clicked(ActionEvent e){
        //sin acciones predefinidas
    }
    
    public void accionBoton2(ActionEvent e){
        //sin acciones predefinidas
    }
    
    public void accionBoton2Presionado(java.awt.event.MouseEvent e){
        //sin acciones predefinidas
    }
    
    public void accionBoton2Soltar(java.awt.event.MouseEvent e){
        //sin acciones predefinidas
    }
    
    public void onButton3Clicked(ActionEvent e){
        //sin acciones predefinidas
    }
    
    public void onButton4Clicked(ActionEvent e){
        //sin acciones predefinidas
    }
    
    public void onButton5Clicked(ActionEvent e){
        //sin acciones predefinidas
    }
    
    public void onButtonG0Clicked(ActionEvent e){
        //sin acciones predefinidas
    }
    
    public void onButtonG1Clicked(ActionEvent e){
        //sin acciones predefinidas
    }
    
    public void onButtonG2Clicked(ActionEvent e){
        //sin acciones predefinidas
    }
    
}