/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import XbeeController.NodesListener;
import XbeeController.XbeeController;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import data.Data;
import graficos.VentanaAplicacion3;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author ALDAJO
 */
public class Aplicacion3 extends VentanaAplicacion3 implements NodesListener, Runnable{

    XbeeController xbee3;
    //direcciones para el JComboBox//antes numAddrNames
    private HashMap<String, XBeeAddress64> numNamesAddr64;
    
    private XBeeAddress64 selectedAddr64;
    
    private boolean radioButtonSelecAddr64 = false;
    
    private int delaySendData = 10;
    
    public Aplicacion3(XbeeController xb) {
        xbee3 = xb;
        xbee3.addNodeListener(this);
        numNamesAddr64 = xbee3.getFormatAddr64List();
        enabledCombox(false);
    }
    
    public void sendDataAsync(XBeeAddress64 ad64, int[] data){
        try {
            xbee3.sendMessageAsynchronous(ad64, data);
        } catch (XBeeException ex) {
            JOptionPane.showMessageDialog(this, "Problems in the port:\n" + ex);
        }
    }
    
    private void setNamesXBeeCombox(){
        if(numNamesAddr64 != null){
            enabledCombox(true);
            removeElementsCombox();
            TreeSet<String> names = new TreeSet<>(numNamesAddr64.keySet());
            for(String n:names){
                addElementCombox1(n);
            }
        }else{
            enabledCombox(false);
            addElementCombox1("-Select an Item-");
        }
    }
    
    private void setAddrXBeeCombox(){
        if(numNamesAddr64 != null){
            enabledCombox(true);
            removeElementsCombox();
            for(XBeeAddress64 i:numNamesAddr64.values()){
                addElementCombox1(i);
            }
        }else{
            enabledCombox(false);
            addElementCombox1("-Select an Item-");
        }
    }
    
    @Override
    public void onSlider1Changed(ChangeEvent e){
        if(selectedAddr64 != null ){
            int[] valuesToSend = new int[4];
            int[] bytesSlider = Data.divideIn2Bits(getValueSlider1());
            valuesToSend[0] = 0xFC;
            valuesToSend[1] = 5;          //ID DEL MOTOR1
            valuesToSend[2] = bytesSlider[0];
            valuesToSend[3] = bytesSlider[1];
            sendDataAsync(selectedAddr64, valuesToSend);
            try {
                Thread.sleep(delaySendData);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(this, "There are problems in the Aplication...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            }
        }
    }
    
    @Override
    public void onSlider2Changed(ChangeEvent e){
        if(selectedAddr64 != null ){
            int[] valuesToSend = new int[4];
            int[] bytesSlider = Data.divideIn2Bits(getValueSlider2());
            valuesToSend[0] = 0xFC;
            valuesToSend[1] = 10;         //ID DEL MOTOR2
            valuesToSend[2] = bytesSlider[0];
            valuesToSend[3] = bytesSlider[1];
            sendDataAsync(selectedAddr64, valuesToSend);
            try {
                Thread.sleep(delaySendData);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(this, "There are problems in the Aplication...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            }
        }
    }
    
    @Override
    public void onSelectedItemCombox(ItemEvent e){
        if(numNamesAddr64 != null){
            if(radioButtonSelecAddr64){
                selectedAddr64 = (XBeeAddress64) getSelectItemCombox();
            }else{
                String thempElement = (String) getSelectItemCombox();
                selectedAddr64 = numNamesAddr64.get(thempElement);
            }
        }
    }
    
    @Override
    public void RadioButton1(ItemEvent e){
        radioButtonSelecAddr64 = false;
        setNamesXBeeCombox();
    }
    
    @Override
    public void RadioButton2(ItemEvent e){
        radioButtonSelecAddr64 = true;
        setAddrXBeeCombox();
    }

    @Override
    public void newNodeAdded(XBeeAddress64 addr64) {
        
    }

    @Override
    public void newNodeWithIDAdded(XBeeAddress64 addr64, String name) {
        
    }

    @Override
    public void removedID(XBeeAddress64 addr64) {
        
    }

    @Override
    public void addedID(XBeeAddress64 addr64, String name) {
        
    }
    
    @Override
    public void updateNumNamesXBeeList(HashMap<String, XBeeAddress64> numNamesAddr64X) {
        numNamesAddr64 = numNamesAddr64X;
        if(radioButtonSelecAddr64){
            setAddrXBeeCombox();
        }else{
            setNamesXBeeCombox();
        }
    }
    
    @Override
    public void exceptionSendATNND(XBeeException ex) {
        
    }

    @Override
    public void run() {
        setVisible(true);
    }
    
}
