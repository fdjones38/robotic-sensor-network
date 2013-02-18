package Comunicacion;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import gnu.io.CommPortIdentifier;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ALDAJO
 */
public class GUISerialPortSelector {
    
    /**
     * Enlista los puertos.
     */
    private Enumeration commports;
    
    /**
     * Puerto a seleccionar.
     */
    private CommPortIdentifier myCPI;
    //Nombre del puerto a seleccionar
    private String SelectedPortName = null;
    //Listado del nombre de los puertos
    private Set<String> portsNames = null;
    
    public GUISerialPortSelector(){
        initPortName();
    }
    
    public void refreshPortName(){
        initPortName();
    }
    
    private void initPortName(){
        portsNames = new TreeSet<String>();
        commports = CommPortIdentifier.getPortIdentifiers();
        while(commports.hasMoreElements()){
            myCPI = (CommPortIdentifier) commports.nextElement();
            portsNames.add(myCPI.getName());
        }
    }
    
    public String getSelectedPortName(){
        return SelectedPortName;
    }
    
    public String showMessageNamePortName(){
        if(portsNames.isEmpty()){
            //en caso de que no hayan puertos
            JOptionPane.showMessageDialog(null, "Lo lamentamos, no hay puertos habilitados");
            return null;
            //imprimimos los puertos en pantalla
        }else{
            String[] pName = portsNames.toArray(new String[0]);
//            System.out.println("puertos disponibles:");
//            //imprime los puertos
//            for(String i:pName){
//                System.out.println(i);
//            }
            //le decimos al usuario que seleccione un puerto
             SelectedPortName = (String) JOptionPane.showInputDialog(null,
            "Choose One Port", "Input",
            JOptionPane.QUESTION_MESSAGE, null,
            pName, pName[0]);
             //en caso de que halla cancelado la operacion
            if(SelectedPortName == null){
                return null;
            }
            return SelectedPortName;
        }
    }
    
    public static String identifierPortName(){
        Enumeration commports = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier myCPI; //Puerto a seleccionar
        String[] portsName = null; //Listado de puertos
        String SelectedPortName = null; //Nombre del puerto a seleccionar
        
        int counter = 0;
        while(commports.hasMoreElements()){
            myCPI = (CommPortIdentifier) commports.nextElement();
            if(counter == 0){
                portsName = new String[1];
                portsName[counter] = myCPI.getName();
            }else{
                String[] temp = portsName;
                portsName = null;
                portsName = new String[counter+1];
                for(int i = 0; i<temp.length; i++){
                    portsName[i] = temp[i];
                }
                portsName[counter] = myCPI.getName();
            }
            counter++;
        }
        
        if(portsName == null){
            //en caso de que no hayan puertos
            JOptionPane.showMessageDialog(null, "Lo lamentamos, no hay puertos habilitados");
            JOptionPane.showMessageDialog(null, "El programa se cerrará");
            System.exit(0);
            return null;
            //imprimimos los puertos en pantalla
        }else{
            System.out.println("puertos disponibles:");
            for(int i=0; i<portsName.length; i++){
                System.out.println(i+". "+portsName[i]);
            }
            //le decimos al usuario que seleccione un puerto
             SelectedPortName = (String) JOptionPane.showInputDialog(null,
            "Choose One Port", "Input",
            JOptionPane.QUESTION_MESSAGE, null,
            portsName, portsName[0]);
            if(SelectedPortName == null){
                JOptionPane.showMessageDialog(null, "El programa se cerrará");
                System.exit(0);
            }
            return SelectedPortName;
        }
        
    }
}
