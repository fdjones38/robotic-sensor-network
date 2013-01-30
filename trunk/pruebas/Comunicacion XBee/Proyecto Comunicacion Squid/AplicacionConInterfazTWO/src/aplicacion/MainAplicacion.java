package aplicacion;

import XbeeController.MessageListener;
import XbeeController.NodesListener;
import XbeeController.XbeeController;
import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import data.Data;
import externalWindows.ConfigXBeeDialog;
import graficos.VentanaPrincipal;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ALDAJO
 */
public class MainAplicacion extends VentanaPrincipal implements MessageListener, NodesListener{
    //Direcciones con su respectivo nombre (ID)//antes addrXbeeSquids
    private HashMap<XBeeAddress64, String> addr64Names;
    //todos los dispositivos encontrados (tienen un numero de registro)
    private HashMap<Integer, XBeeAddress64> numAddr64Register;
    //direcciones para el JComboBox//antes numAddrNames
    private HashMap<String, XBeeAddress64> numNamesAddr64;
    //ventana de dialogo para cambiar el ID
    private ConfigXBeeDialog windowConfig = null;
    //objeto que permite la comunicacion
    private XbeeController xbeeC = null;
    private XBeeAddress64 lastAddr64Chnged;
    //bandera para habilitar botones (al parecer no sera necesaria)
    private boolean flag = true;
    //aplicaciones
    private Thread thApp1 = null;
    private Aplicacion2 app2;
    private Thread thApp3 = null;
    //constructor
    public MainAplicacion(){
        super();
        initCharacteristics();
        xbeeC = new XbeeController();
        xbeeC.addMessageListener(this);
        xbeeC.addNodeListener(this);
        windowConfig = new ConfigXBeeDialog(this);
    }
    //caracteristicas de la ventana
    private void initCharacteristics(){
        addNameButton1("Change ID");
        addNameButton2("On...");
        addNameButton3("Search Xbee");
        addNameButton4("Clear");
        addNameButton5("OpenPort");
        addNameEtiqueta1("Dispositive List");
        addNameEtiqueta2("Operations Console");
        addNameEtiqueta("Aplications");
        addNameEtiqueta4("PORT:---");
        enableButtons12G(false);
        enableButton3(false);
    }
    //Actualiza y Ordena los Elementos del JCombox
    private void refreshElementsJcombox() {
        //jComboBox1
        removeElementsJCombox();
        numNamesAddr64 = null;
        numNamesAddr64 = new HashMap<>();
        //numNamesAddr.put(bcName, BROADCASTADDR);
        for(Integer i: numAddr64Register.keySet()){
            //empleamos la lista de todas las direcciones registradas
            XBeeAddress64 thempAddr64 = numAddr64Register.get(i);
            if(addr64Names.containsKey(thempAddr64)){
                numNamesAddr64.put(i+"."+addr64Names.get(thempAddr64), thempAddr64);
                addElementJCombox(i+"."+addr64Names.get(thempAddr64));
            }else{
                numNamesAddr64.put(i+".SinNombre", thempAddr64);
                addElementJCombox(i+".SinNombre");
            }
        }
    }
    //imprime las direcciones almacenadas
    private void orderElementsAddr64TextList(){
        clearAreaTexto1();
        setLineAreaTexto1("#", "ID", "Direccion 64 Bits");
        for(Integer i:numAddr64Register.keySet()){
           XBeeAddress64 thempAddr64 = numAddr64Register.get(i);
           if(addr64Names.containsKey(thempAddr64)){
               setLineAreaTexto1(i.toString(), addr64Names.get(thempAddr64), thempAddr64.toString());
           }else{
               setLineAreaTexto1(i.toString(), thempAddr64.toString());
           }
        }
    }
    //Envia un mensaje Asincrono
    void sendMA(XBeeAddress64 addr64, int[] data){
        try {
            xbeeC.sendMessageAsynchronous(addr64, data);
        } catch (XBeeException ex) {
            JOptionPane.showMessageDialog(null, "There are problems in the Port...\n"+
                    "Please verify!\nError type: "+ ex);
        }
    }
    //Envia el Comando AT "NI"
    private void sendATNICommand(XBeeAddress64 addr64, int[] data){
        try {
            xbeeC.sendATCommand("NI", data);
        } catch (XBeeException ex) {
            JOptionPane.showMessageDialog(null, "There are problems in the Port...\n"+
                    "Please verify!\nError type: "+ ex);
        }
    }
    //Envía El comando AT "NI" para dispositivos remotos
    private void sendRemoteNICommand(XBeeAddress64 addr64, int[] data){
        try {
            xbeeC.sendRemoteATCommand(addr64,"NI", data);
        } catch (XBeeException ex) {
            Logger.getLogger(MainAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //se le asigna una accion al boton 1: cambiar el ID de un XBee
    @Override
    public void onButton1Clicked(ActionEvent e) {
        // TODO add your handling code here:
        lastAddr64Chnged = null;
        //obtenemos un listado de los numeros de registro
        Set<Integer> thempNums = numAddr64Register.keySet();
        //
        windowConfig.removeElemetsComboBox();
        //
        for(Integer thempnum:thempNums){
            if(thempnum != 0){
                windowConfig.setElementToComBox(thempnum);
            }
        }
        windowConfig.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        windowConfig.setVisible(true);
        Integer thempKey = windowConfig.getSelectedNum();
        String thempText = windowConfig.getSelectedName();
        //almacenamos la direccion para ser comparada con la respuesta que llega por
        //el evento
        if(!(thempKey == null && thempText == null)){
            try {
                XBeeAddress64 thempAddr64 = numAddr64Register.get(thempKey);
                lastAddr64Chnged = thempAddr64;
                xbeeC.changeRemoteID(thempAddr64, Data.stringToInts(thempText));
                setLineAreaTexto2("Changing ID...");
            } catch (XBeeException ex) {
                setLineAreaTexto2("There are problems in the Port...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            }
        }
    }
    //se le asigna una accion al boton 2 al presionar
    @Override
    public void accionBoton2Presionado(java.awt.event.MouseEvent e){
        int[] messages = new int[2];
        messages[0] = 0xF1;
        messages[1] = 0x01;
        String tempName = (String)getSelectItemJCombox();
        XBeeAddress64 tempAddr64 = numNamesAddr64.get(tempName);
            sendMA(tempAddr64, messages);
            String temp = (tempName + ": <ON>");
            setLineAreaTexto2(temp);
    }
    //accion qúe se realiza al soltar el boton2 despues de presionarlo
    @Override
    public void accionBoton2Soltar(java.awt.event.MouseEvent e){
        int[] messages = new int[2];
        messages[0] = 0xF1;
        messages[1] = 0x00;
        String tempName = (String)getSelectItemJCombox();
        XBeeAddress64 tempAddr64 = numNamesAddr64.get(tempName);
            sendMA(tempAddr64, messages);
            String temp = (tempName + ": <OFF>");
            setLineAreaTexto2(temp);
    }
    //se le asigna una accion al boton 3: buscar los dispositivos
    @Override
    public void onButton3Clicked(ActionEvent e) {
        setLineAreaTexto2("Searching Devices...");
        try {
            xbeeC.searchXBeeDevices();
        } catch (XBeeException ex) {
            setLineAreaTexto2("There are problems in the Port...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
        }
        enableButtons12G(false);
        flag = true;
    }
    //se le asigna una accion al boton 4: limpiar el area de texto 2
    @Override
    public void onButton4Clicked(ActionEvent e) {
        clearAreaTexto2();
    }
    //se le asigna una accion al boton 5: abrir el puerto
    @Override
    public void onButton5Clicked(ActionEvent e){
        try {
            xbeeC.openSerialPort();
            if(xbeeC.getSelectedPortName() != null){
                app2 = null;
                app2 = new Aplicacion2(xbeeC);
                setLineAreaTexto2("CONECTADO AL PUERTO:" + xbeeC.getSelectedPortName());
                enableButton3(true);
                addNameEtiqueta4("PORT:" + xbeeC.getSelectedPortName());
                enableButtons12G(false);
            }
        } catch (XBeeException | ArrayIndexOutOfBoundsException | IllegalStateException ex) {
            setLineAreaTexto2("There are problems in the Port...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            addNameEtiqueta4("PORT:---");
            enableButtons12G(false);
            enableButton3(false);
        }
    }
    //Ejecuta la aplicacion 1
    @Override
    public void onButtonG0Clicked(ActionEvent e){
        thApp1 = new Thread(new Aplicacion1(xbeeC));
        thApp1.start();
    }
    //Ejecuta la aplicacion 2
    @Override
    public void onButtonG1Clicked(ActionEvent e){
        app2.setVisibleAplicacion2(true);
        app2.setSize(700, 700);
    }
    //ejecuta la aplicacion 3
    @Override
    public void onButtonG2Clicked(ActionEvent e){
        thApp3 = new Thread(new Aplicacion3(xbeeC));
        thApp3.start();
    }
    
    ///////////////////MessageListener//////////////////
    @Override
    public void dataMessage(ZNetRxResponse packet) {
        if(packet.getData()[0] == 0xFB){
            circleIndicatorOn();
        }
    }

    @Override
    public void atMessage(String atCom, AtCommandResponse atResponse) {
        if(atCom.equals("ND")){
            if(flag){
                flag = false;
                enableButtons12G(true);
            }
        }
    }

    @Override
    public void remoteATMessage(String apiID, RemoteAtResponse atRemoteResponse) {
        
        XBeeAddress64 thempAddr64 = atRemoteResponse.getRemoteAddress64();
        
        if(apiID.equals("NI") && lastAddr64Chnged.equals(thempAddr64)){
            try {
                xbeeC.sendATCommand("ND");
                //System.out.println("enviando ND");
            } catch (XBeeException ex) {
                setLineAreaTexto2("There are problems in the Port...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
            }
            setLineAreaTexto2("ID changed!");
            lastAddr64Chnged = null;
            refreshElementsJcombox();
            orderElementsAddr64TextList();
        }
    }

    @Override
    public void otherMessage(XBeeResponse resp) {
        
    }
    
    /////////////Nodes Listener////////////////////////////////////////
    @Override
    public void newNodeAdded(XBeeAddress64 addr64) {
        setLineAreaTexto2("NEW DEVICE FOUND");
        setLineAreaTexto2("Address: " + addr64 + " ID: NOTHING");
        numAddr64Register = xbeeC.getAllXBeeAddr64();
        orderElementsAddr64TextList();
        refreshElementsJcombox();
        if(flag){
            flag = false;
            enableButtons12G(true);
        }
    }
    
    @Override
    public void newNodeWithIDAdded(XBeeAddress64 addr64, String name) {
        setLineAreaTexto2("NEW DEVICE FOUND");
        setLineAreaTexto2("Address: " + addr64 + " ID: " + name);
        numAddr64Register = xbeeC.getAllXBeeAddr64();
        addr64Names = xbeeC.getXBeesWithID();
        orderElementsAddr64TextList();
        refreshElementsJcombox();
        if(flag){
            flag = false;
            enableButtons12G(true);
        }
    }

    @Override
    public void removedID(XBeeAddress64 addr64) {
        numAddr64Register = xbeeC.getAllXBeeAddr64();
        addr64Names = xbeeC.getXBeesWithID();
        setLineAreaTexto2("ID REMOVED...");
        orderElementsAddr64TextList();
        refreshElementsJcombox();
    }

    @Override
    public void addedID(XBeeAddress64 addr64, String name) {
        numAddr64Register = xbeeC.getAllXBeeAddr64();
        addr64Names = xbeeC.getXBeesWithID();
        setLineAreaTexto2("ID ADDED...");
        setLineAreaTexto2("Address: " + addr64 + " ID: " + name);
        orderElementsAddr64TextList();
        refreshElementsJcombox();
    }

    @Override
    public void exceptionSendATNND(XBeeException ex) {
        setLineAreaTexto2("There are problems in the Port...\n"+
                              "Please verify!\n"+
                              "Error type: "+ ex);
    }

    @Override
    public void updateNumNamesXBeeList(HashMap<String, XBeeAddress64> numNamesAddr64) {
        numAddr64Register = xbeeC.getAllXBeeAddr64();
        orderElementsAddr64TextList();
        refreshElementsJcombox();
    }
    
}
