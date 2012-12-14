package principal;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.NodeDiscover;
import java.util.HashMap;
import comunicacion.*;
import java.util.Set;

/**
 *  Esta clase se encarga de enviar el comando AT "ND" al XBee local para
 *  identificar los nodos que se encuentran en la red.
 * @author ALDAJO
 */
public class Principal implements PacketListener{
        XBee xbee;
        HashMap<XBeeAddress64, String> addrsXbee;
        int count = 1;

    public Principal() throws XBeeException, InterruptedException {
        addrsXbee = new HashMap<>();
        xbee = new XBee();
        xbee.open(PuertoSerial.identifierPortName(), 9600);
        xbee.addPacketListener(this);
        xbee.sendAsynchronous(new AtCommand("ND"));
        /**xbee.addPacketListener(new PacketListener() {
            @Override
            public void processResponse(XBeeResponse xbr) {
                if (xbr.getApiId() == ApiId.AT_RESPONSE){
                    NodeDiscover nd = NodeDiscover.parse((AtCommandResponse)xbr);
                    XBeeAddress64 tempAddr = nd.getNodeAddress64();
                if(!addrsXbee.containsKey(tempAddr)){
                    //agregamos el nodo a la lista
                    String nodeName = nd.getNodeIdentifier();
                    addrsXbee.put(tempAddr, nodeName);
                    //Imprimimos el mensaje en pantalla
                    System.out.println("Dispositivo localizado!!!");
                    System.out.println("Dirección: " + tempAddr + " NombreNodo: " + nodeName);
                    System.exit(0);
                    }
                }
            }
        });*/
    }
        
        
        
    public static void main(String[] args) throws XBeeException, InterruptedException {
        //PropertyConfigurator.configure("log4j.properties");
        new Principal();
    }
    
    @Override
    public void processResponse(XBeeResponse xbr) {
        if (xbr.getApiId() == ApiId.AT_RESPONSE){
            NodeDiscover nd = NodeDiscover.parse((AtCommandResponse)xbr);
            XBeeAddress64 tempAddr = nd.getNodeAddress64();
                if(!addrsXbee.containsKey(tempAddr)){
                    Set <XBeeAddress64> addr64 = addrsXbee.keySet();
                    //agregamos el nodo a la lista
                    String nodeName = nd.getNodeIdentifier();
                    addrsXbee.put(tempAddr, nodeName);
                    //Imprimimos el mensaje en pantalla
                    //System.out.println("Dispositivo localizado!!!");
                    //System.out.println("Dirección: " + tempAddr + " NombreNodo: " + nodeName);
                    System.out.println("Lista de direcciones (Actualizacion No " + count + ")\n");
                    System.out.println("Direccion 64 Bits\t\t\t\tIdentificacion");
                    for(XBeeAddress64 addr:addr64){
                        System.out.println(addr + "\t\t" + addrsXbee.get(addr));
                    }
                    count++;
                    System.out.println("");
                }
        }
    }
}