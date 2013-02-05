/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Comunicacion.PuertoSerial;
import XbeeController.MessageListener;
import XbeeController.NodesListener;
import XbeeController.XbeeController;
import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ALDAJO
 * Descripci�n: Programa que permite el recibir una cadena de datos e imprimirlos, donde se visualiza la direcci�n de
 * los dispositivos y los datos que se reciven ya procesados. Funciona con el formato de envio de datos creados
 * para las tarjetas arduino.
 */
public class XBeeReceiveData implements MessageListener{

    private XbeeController xbeeC;
    
    //Constructor
    public XBeeReceiveData() {
        xbeeC = new XbeeController();
        try {
            xbeeC.openSerialPort();
            xbeeC.addMessageListener(this);
        } catch (XBeeException ex) {
            JOptionPane.showMessageDialog(null, "Problems in the port\n" + ex);
        }
    }
    //procesamiento de datos que vienen divididos en dos bytes, para unirlos
    private float convertDataType1(int a, int b){
        //importante el orden en el que llegan
        return ((a<<8)+b)/10;
    }
	//procesamiento de datos que vienen divididos en dos bytes, para unirlos
    private float convertDataType2(int a, int b, int c){
        //importante el orden en el que llegan
        return ((a<<16)+(b<<8)+c)/1000;
    }
    //procesamiento de datos que vienen divididos en dos bytes, para unirlos
    private float convertDataType3(long a, long b, long c, long d){
        //importante el orden en el que llegan
        long v1 = a <<24;
        long v2 = b <<16;
        long v3 = c << 8;
        return (v1+v2+v3+d)/100;
    }
    
    private void analizeDataCome(ZNetRxResponse packet){
        //addr64Names = xbeeC.getXBeesWithID();
        int idData = packet.getData()[0];
        if(idData == 0xFB || idData == 0xFD || idData == 0xFE){
            //Almacenamos la informacion util: direccion y paquete de datos
            int[] data = packet.getData();
            XBeeAddress64 xb64 = packet.getRemoteAddress64();
            float value;
			//cadena de texto para imprimir en terminal opcional
            //String text = "Address: " +xb64.toString();
            System.out.print("Address: " +xb64.toString());
            if(idData == 0xFB){
                for(int i=0; i<data[1]; i++){
                    value = convertDataType1(data[(i+1)*2], data[(2*i)+3]);
                    System.out.print(" Data"+(i+1)+": "+value+"\n");
                    //text = text + " Data"+(i+1)+": "+value;
                }
            }else if(idData == 0xFD){
                for(int i=0; i<data[1]; i++){
                    value = convertDataType2(data[(3*i)+2], data[3*(i+1)], data[(3*i)+4]);
                    System.out.print(" Data"+(i+1)+": "+value+"\n");
                    //text = text + " Data"+(i+1)+": "+value;
                }
            }else{
                for(int i=0; i<data[1]; i++){
                    value = convertDataType3(data[(4*i)+2], data[(4*i)+3], data[4*(i+1)], data[(4*i)+5]);
                    System.out.print(" Data"+(i+1)+": "+value+"\n");
                    //text = text + " Data"+(i+1)+": "+value;
                }
            }
        }
    }

    @Override
    public void dataMessage(ZNetRxResponse packet) {
        analizeDataCome(packet);
    }

    @Override
    public void atMessage(String apiID, AtCommandResponse atResponse) {
        
    }

    @Override
    public void remoteATMessage(String apiID, RemoteAtResponse atRemoteResponse) {
        
    }

    @Override
    public void otherMessage(XBeeResponse resp) {
        
    }
    
}
