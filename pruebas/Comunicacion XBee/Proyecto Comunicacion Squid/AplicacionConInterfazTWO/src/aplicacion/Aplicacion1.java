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
import graficos.VentanaAplicacion1;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.TreeSet;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author ALDAJO
 */
public class Aplicacion1 extends VentanaAplicacion1 implements NodesListener, Runnable{

    private XbeeController xbee;
    private HashMap<String, XBeeAddress64> numNamesAddr;
    private XBeeAddress64 selectedAddr64;
    private boolean radioButtonSelecAddr64;
    private int delaySendData = 10;
    
    public Aplicacion1(XbeeController xb){
        super();
        initComponents();
        xbee = xb;
        numNamesAddr = xbee.getFormatAddr64List();
    }
    
    private void initComponents(){
        enableCombox(false);
        setLineAreaText("Go to options and select a XBee address");
        setNameButton1("Clear");
        setNameButton3("Nothing");
        setNameLabel1("List of Devices");
    }
    
    private void sendDataAsync(XBeeAddress64 xb64, int[] data){
        try {
            xbee.sendMessageAsynchronous(xb64, data);
        } catch (XBeeException ex) {
            setLineAreaText("There are problems in the Port...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
        }
    }
    
    private void setNamesXBeeCombox(){
        if(numNamesAddr != null){
            enableCombox(true);
            removeElementsCombox();
            TreeSet<String> names = new TreeSet<>(numNamesAddr.keySet());
            for(String n:names){
                addElementCombox(n);
            }
        }else{
            enableCombox(false);
            addElementCombox("-Select an Item-");
        }
    }
    
    private void setAddrXBeeCombox(){
        if(numNamesAddr != null){
            enableCombox(true);
            removeElementsCombox();
            for(XBeeAddress64 i:numNamesAddr.values()){
                addElementCombox(i);
            }
        }else{
            enableCombox(false);
            addElementCombox("-Select an Item-");
        }
    }
    
    @Override
    public void onControlMousePressed(MouseEvent e){
        setLineAreaText("MOUSE PRESSED: Move the mouse please...");
    }
    
    @Override
    public void onControlMouseRelased(MouseEvent e){
        if(selectedAddr64 != null ){
            int[] valuesToSend = new int[4];
            valuesToSend[0] = 0xF8;
            valuesToSend[1] = Data.encriptValue(0);
            valuesToSend[2] = Data.encriptValue(0);
            valuesToSend[3] = Data.encriptValue(0);
            sendDataAsync(selectedAddr64, valuesToSend);
        }
        setLineAreaText("MOUSE RELASED");
    }
    
    @Override
    public void onControlMouseDragged(MouseEvent e){
        if(selectedAddr64 != null ){
            float thempX =  getControlXcoordenate();
            float thempY =  getControlYcoordenate();
            int[] valuesToSend = new int[4];
            valuesToSend[0] = 0xF8;
            valuesToSend[1] = Data.encriptValue((int)(((-thempX)/2)+((0.866)*thempY)));
            valuesToSend[2] = Data.encriptValue((int)(((-thempX)/2)-((0.866)*thempY)));
            valuesToSend[3] = Data.encriptValue((int)(thempX));
            sendDataAsync(selectedAddr64, valuesToSend);
            setLineAreaText("X: " + (int)thempX + "  Y: " + (int)thempY);
            try {
                Thread.sleep(delaySendData);
            } catch (InterruptedException ex) {
                setLineAreaText("There are problems in the Aplication...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            }
        }
    }
    
    @Override
    public void onButtonLeftPressed(MouseEvent e){
        if(selectedAddr64 != null ){
            int[] valuesToSend = new int[4];
            valuesToSend[0] = 0xF8;
            valuesToSend[1] = Data.encriptValue(90);
            valuesToSend[2] = Data.encriptValue(90);
            valuesToSend[3] = Data.encriptValue(90);
            sendDataAsync(selectedAddr64, valuesToSend);
            setLineAreaText("ROTATE ON CLOCK");
            try {
                Thread.sleep(delaySendData);
            } catch (InterruptedException ex) {
                setLineAreaText("There are problems in the Aplication...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            }
        }
    }
    
    @Override
    public void onButtonLeftRelased(MouseEvent e){
        if(selectedAddr64 != null ){
            int[] valuesToSend = new int[4];
            valuesToSend[0] = 0xF8;
            valuesToSend[1] = Data.encriptValue(0);
            valuesToSend[2] = Data.encriptValue(0);
            valuesToSend[3] = Data.encriptValue(0);
            sendDataAsync(selectedAddr64, valuesToSend);
        }
        setLineAreaText("BUTTON LEFT RELASED");
    }
        
    @Override
    public void onButtonRightPressed(MouseEvent e){
        if(selectedAddr64 != null ){
            int[] valuesToSend = new int[4];
            valuesToSend[0] = 0xF8;
            valuesToSend[1] = Data.encriptValue(-90);
            valuesToSend[2] = Data.encriptValue(-90);
            valuesToSend[3] = Data.encriptValue(-90);
            sendDataAsync(selectedAddr64, valuesToSend);
            setLineAreaText("ROTATE ON UNCLOCK");
            try {
                Thread.sleep(delaySendData);
            } catch (InterruptedException ex) {
                setLineAreaText("There are problems in the Aplication...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            }
        }
    }
    
    @Override
    public void onButtonRightRelased(MouseEvent e){
        if(selectedAddr64 != null ){
            int[] valuesToSend = new int[4];
            valuesToSend[0] = 0xF8;
            valuesToSend[1] = Data.encriptValue(0);
            valuesToSend[2] = Data.encriptValue(0);
            valuesToSend[3] = Data.encriptValue(0);
            sendDataAsync(selectedAddr64, valuesToSend);
        }
        setLineAreaText("BUTTON RIGHT RELASED");
    }
    
    @Override
    public void onSliderChanged(ChangeEvent e){
        if(selectedAddr64 != null ){
            int[] valuesToSend = new int[2];
            valuesToSend[0] = 0xFA;
            valuesToSend[1] = Data.encriptValue(getValueSlider());
            sendDataAsync(selectedAddr64, valuesToSend);
            try {
                Thread.sleep(delaySendData);
            } catch (InterruptedException ex) {
                setLineAreaText("There are problems in the Aplication...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            }
        }
        setLineAreaText("VALUE SLIDER: "+ getValueSlider());
    }
    
    @Override
    public void onButton1Click(ActionEvent e){
        clearTextArea();
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
    public void onSelectedItemCombox(ItemEvent e){
        if(numNamesAddr != null){
            if(radioButtonSelecAddr64){
                selectedAddr64 = (XBeeAddress64) getSelectedItemCombox();
            }else{
                String thempElement = (String) getSelectedItemCombox();
                selectedAddr64 = numNamesAddr.get(thempElement);
            }
        }
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
    public void updateNumNamesXBeeList(HashMap<String, XBeeAddress64> numNamesAddr64) {
        numNamesAddr = numNamesAddr64;
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
