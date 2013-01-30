/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XbeeController;

import Comunicacion.PuertoSerial;
import Principal.SquidMonitor;
import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.NodeDiscover;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALDAJO
 */
public class XbeeController implements PacketListener {
    
   //declare the variables
    XBee xbee = null;
    private HashMap<String, XBeeAddress64> addrsXbee;
    private List<MessageListener> clients;
    
    public XbeeController(String SerialPort, int baundRate) throws XBeeException{
        xbee = new XBee();
        addrsXbee = new HashMap<>();
        clients = new ArrayList<>();
        
        xbee.open(SerialPort, baundRate);
        xbee.addPacketListener(this);
    }
    
    public void addMessageListener(MessageListener cl){
        clients.add(cl);
    }
    
    public void changeSerialport(String SerialPort){
        //pendiente
    }
    
    public void sendMessageAsynchronous(XBeeAddress64 addr64, int[] values) throws XBeeException{
        xbee.sendAsynchronous(new ZNetTxRequest(addr64, values));
    }
    
    public void sendATCommand(String command) throws XBeeException{
        xbee.sendAsynchronous(new AtCommand(command));
    }
    
    public void sendRemoteATCommand(XBeeAddress64 addr, String command, int[] value) throws XBeeException{
        xbee.sendAsynchronous(new RemoteAtRequest(addr, command, value));
    }
    
    public void sendRemoteATCommand(XBeeAddress64 addr, String command) throws XBeeException{
        xbee.sendAsynchronous(new RemoteAtRequest(addr, command));
    }
    
    @Override
    public void processResponse(XBeeResponse xbr) {
        System.out.println("Mensaje Recibido=" + xbr);
        ApiId tempApiId = xbr.getApiId();
        if (tempApiId == ApiId.AT_RESPONSE) {
            AtCommandResponse atCommand = (AtCommandResponse) xbr;
            String atSCommand = atCommand.getCommand();
            if(atSCommand.equals("ND")){
                NodeDiscover nd = NodeDiscover.parse(atCommand);
                XBeeAddress64 tempAddr = nd.getNodeAddress64();
                String nodeName = nd.getNodeIdentifier();
                if(!addrsXbee.containsValue(tempAddr)){
                        addrsXbee.put(nodeName, tempAddr);
                        for (MessageListener client : clients) {
                            client.nodeDetector(nd.getNodeIdentifier());
                        }
                }
            }else{
                for (MessageListener client : clients) {
                    client.atMessage(atSCommand, atCommand.getValue());
                }
            }
        }else if(tempApiId == ApiId.ZNET_RX_RESPONSE){
            ZNetRxResponse dataPacket = (ZNetRxResponse) xbr;
            for (MessageListener client : clients) {
                    client.dataMessage(dataPacket.getData());
            }
        }else if(tempApiId == ApiId.REMOTE_AT_RESPONSE){
            RemoteAtResponse atCommandRemote = (RemoteAtResponse) xbr;
            for (MessageListener client : clients) {
                    client.remoteATMessage(atCommandRemote.getCommand(), atCommandRemote.getValue());
            }
        }
    }
    
}
