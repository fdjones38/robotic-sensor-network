/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XBeeController;

import Comunicacion.PuertoSerial;
import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.NodeDiscover;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * version2
 * @author ALDAJO
 */
public class XbeeController implements PacketListener {
    
    XBee xbee = null;
    //Direcciones con su respectivo nombre (ID)//antes addrXbeeSquids
    private HashMap<XBeeAddress64, String> addr64Names;
    //todos los dispositivos encontrados (tienen un numero de registro)
    private HashMap<Integer, XBeeAddress64> numAddr64Register;
    //direcciones para el JComboBox//antes numAddrNames
    private HashMap<String, XBeeAddress64> numNamesAddr64XBee;
    //Clientes que implementan la interfaz MessageListener
    private List<MessageListener> clientsML;
    //Clientes que implementan la interfaz NodesListener
    private List<NodesListener>clientsNL;
    //permite manipular los puertos Seriales
    private PuertoSerial pSerial;
    //contador de XBee
    private int counterXBee = 0;
    //Nombre y direccion de 64 bits constantes
    private final String bcName = "Broadcast";
    private final XBeeAddress64 BROADCASTADDR = XBeeAddress64.BROADCAST;
    
    private String selectedSerialPort;
    
    public XbeeController(){
        pSerial = new PuertoSerial();
        clientsML = new ArrayList<>();
        clientsNL = new ArrayList<>();
        addr64Names = new HashMap<>();
        numAddr64Register = new HashMap<>();
        addr64Names.put(BROADCASTADDR, bcName);
        numAddr64Register.put(0, BROADCASTADDR);
    }
    
    public void addMessageListener(MessageListener cl){
        clientsML.add(cl);
    }
    
    public void addNodeListener(NodesListener cl){
        clientsNL.add(cl);
    }
    
    public void openSerialPort() throws XBeeException{
        pSerial = null;
        pSerial = new PuertoSerial();
        selectedSerialPort = pSerial.showMessageNamePortName();
        if(selectedSerialPort == null){
            JOptionPane.showMessageDialog(null, "You do not select a Port!");
        }else{
            if(xbee != null){
                xbee.close();
                xbee = null;
            }
            pSerial.refreshPortName();
            xbee = new XBee();
            xbee.open(selectedSerialPort, 9600);
            xbee.addPacketListener(this);
        }
    }
    
    public String getSelectedPortName(){
        return selectedSerialPort;
    }
    
    public HashMap<Integer, XBeeAddress64> getAllXBeeAddr64(){
        return numAddr64Register;
    }
    
    public HashMap<XBeeAddress64, String> getXBeesWithID(){
        return addr64Names;
    } 
    
    public void sendMessageAsynchronous(XBeeAddress64 addr64, int[] values) throws XBeeException{
        xbee.sendAsynchronous(new ZNetTxRequest(addr64, values));
    }
    
    public void sendATCommand(String command) throws XBeeException{
        xbee.sendAsynchronous(new AtCommand(command));
    }
    
    public void sendATCommand(String command, int[] value) throws XBeeException{
        xbee.sendAsynchronous(new AtCommand(command, value));
    }
    
    public void sendRemoteATCommand(XBeeAddress64 addr, String command, int[] value) throws XBeeException{
        xbee.sendAsynchronous(new RemoteAtRequest(addr, command, value));
    }
    
    public void sendRemoteATCommand(XBeeAddress64 addr, String command) throws XBeeException{
        xbee.sendAsynchronous(new RemoteAtRequest(addr, command));
            
    }
    
    public void searchXBeeDevices() throws XBeeException{
            addr64Names = null;
            numAddr64Register = null;
            addr64Names = new HashMap<>();
            numAddr64Register = new HashMap<>();
            counterXBee = 0;
            addr64Names.put(BROADCASTADDR, bcName);
            numAddr64Register.put(0, BROADCASTADDR);
            xbee.sendAsynchronous(new AtCommand("ND"));
    }
    
    public void changeRemoteID(XBeeAddress64 addr64, int[] data) throws XBeeException{
         sendRemoteATCommand(addr64,"NI", data);
    }
    
    public HashMap<String, XBeeAddress64> getFormatAddr64List(){
        return numNamesAddr64XBee;
    }
    
    private void formatAddr64List(){
        numNamesAddr64XBee = null;
        numNamesAddr64XBee = new HashMap<>();
        //numNamesAddr.put(bcName, BROADCASTADDR);
        for(Integer i: numAddr64Register.keySet()){
            //empleamos la lista de todas las direcciones registradas
            XBeeAddress64 thempAddr64 = numAddr64Register.get(i);
            if(addr64Names.containsKey(thempAddr64)){
                numNamesAddr64XBee.put(i+"."+addr64Names.get(thempAddr64), thempAddr64);
            }else{
                numNamesAddr64XBee.put(i+".SinNombre", thempAddr64);
            }
        }
    }
    
    private void sendInfoToClients(XBeeResponse xb){
        ApiId tempApiId = xb.getApiId();
        if (tempApiId == ApiId.AT_RESPONSE) {
            //envia la respuesta at a los clientes
            AtCommandResponse atComR = (AtCommandResponse) xb;
            String atCommand = atComR.getCommand();
            analizeATCommandResponse(atCommand, atComR);
            for (MessageListener client : clientsML) {
                client.atMessage(atCommand, atComR);
            }
        }else if(tempApiId == ApiId.ZNET_RX_RESPONSE){
            //envia el paquete de respuesta a los clientes
            ZNetRxResponse dataPacket = (ZNetRxResponse) xb;
            XBeeAddress64 xAddr64 = dataPacket.getRemoteAddress64();
            if(!numAddr64Register.containsValue(xAddr64)){
                counterXBee++;
                numAddr64Register.put(counterXBee, xAddr64);
                for(NodesListener client:clientsNL){
                    client.newNodeAdded(xAddr64);
                }
                try {
                    sendATCommand("ND");
                } catch (XBeeException ex) {
                    for(NodesListener client:clientsNL){
                        client.exceptionSendATNND(ex);
                    }
                }
            }
            for (MessageListener client : clientsML) {
                client.dataMessage(dataPacket);
            }
        }else if(tempApiId == ApiId.REMOTE_AT_RESPONSE){
            //envia la respuesta at a los clientes
            RemoteAtResponse atCommandRemoteResponse = (RemoteAtResponse) xb;
            for (MessageListener client : clientsML) {
                client.remoteATMessage(atCommandRemoteResponse.getCommand(), atCommandRemoteResponse);
            }
            analizeRemoteATCommandResponse(atCommandRemoteResponse.getCommand(), atCommandRemoteResponse);
        }else{
            for (MessageListener client : clientsML) {
                client.otherMessage(xb);
            }
        }
    }
    
    private void analizeATCommandResponse(String atCommand, AtCommandResponse atCR){
        if(atCommand.equals("ND")){
                NodeDiscover nd = NodeDiscover.parse(atCR);
                XBeeAddress64 tempAddr = nd.getNodeAddress64();
                String nodeName = nd.getNodeIdentifier();
                //primero se pregunta si está en la lista de direcciones registradas
                if(!numAddr64Register.containsValue(tempAddr)){						//(1)No esta Registrada
                        //se crea un numero de registro
                        counterXBee++;
                        numAddr64Register.put(counterXBee, tempAddr);
                        //(2)se pregunta si tiene nombre
                        if(!(nodeName.equals(" ") || nodeName.equals(""))){				//(2)Tiene ID
                                addr64Names.put(tempAddr, nodeName);
                                for(NodesListener client:clientsNL){
                                        client.newNodeWithIDAdded(tempAddr, nodeName);
                                }
                        }else{                                                                          //(2)No tiene ID
                                for(NodesListener client:clientsNL){
                                        client.newNodeAdded(tempAddr);
                                }
                        }
                }else{											//(1)Está en la lista
                        //(2)se pregunta si tiene nombre
                        if(!(nodeName.equals(" ")||nodeName.equals(""))){				//(2)Tiene ID
                                addr64Names.put(tempAddr, nodeName);
                                for(NodesListener client:clientsNL){
                                        client.addedID(tempAddr, nodeName);
                                }
                        }else{                                                                          //No tiene ID. Se elimina de la lista de nombres
                                addr64Names.remove(tempAddr);
                                for(NodesListener client:clientsNL){
                                        client.removedID(tempAddr);
                                }
                        }
                }
        }//close first If
        formatAddr64List();
        for(NodesListener client:clientsNL){
            client.updateNumNamesXBeeList(numNamesAddr64XBee);
        }
    }
    
    private void analizeRemoteATCommandResponse(String atCR, RemoteAtResponse atCRR){
        //No hay necesidad de codigos por el momento
    }
    
    @Override
    public void processResponse(XBeeResponse xbr) {
        sendInfoToClients(xbr);
    }
    
}
