/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import XbeeController.MessageListener;
import XbeeController.XbeeController;
import com.rapplogic.xbee.api.AtCommandResponse;
import com.rapplogic.xbee.api.RemoteAtResponse;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import graficos.GraphicDataContainer2;
import graficos.VentanaAplicacion2;
import java.util.HashMap;

/**
 *
 * @author ALDAJO
 */
public class Aplicacion2 extends VentanaAplicacion2 implements MessageListener{
    
    private XbeeController xbeeCG;
    //Direcciones con su respectivo nombre (ID)//antes addrXbeeSquids
    private HashMap<XBeeAddress64, String> addr64NamesG;
    //Direcciones a las cuales se le asigna un grafico (Solo si estan enviando datos)
    private HashMap<XBeeAddress64, GraphicDataContainer2> XBeegraphicData;
    
    Aplicacion4 areaT;
    
    //Constructor
    public Aplicacion2(XbeeController xbeeController) {
        xbeeCG = xbeeController;
        xbeeCG.addMessageListener(this);
        addr64NamesG = xbeeCG.getXBeesWithID();
        XBeegraphicData = new HashMap<>();
        areaT = new Aplicacion4();
    }
    //Añade un nuevo grafico
    private void addGraphicData(XBeeAddress64 ad64, int lines, String titleName){
        GraphicDataContainer2 gD = new GraphicDataContainer2(titleName+" Addres64:"+ad64.toString(), lines);
        XBeegraphicData.put(ad64, gD);
        addGraphicDataContainer(gD);
    }
    //Permite Cambiar el titulo de cada grafico
    private void changeTitleGraphicData(XBeeAddress64 ad64 ,String titleName){
        XBeegraphicData.get(ad64).setTitle(titleName+" Addres64:"+ad64.toString());
    }
    //Permite unir los bytes que viene separados por dos partes
    private float convertDataType1(int a, int b){
        //importante el orden en el que llegan
        return ((a<<8)+b)/10;
    }
    //En pruebas todavia
    private float convertDataType2(int a, int b, int c){
        //importante el orden en el que llegan
        return ((a<<16)+(b<<8)+c)/1000;
    }
    
    private float convertDataType3(long a, long b, long c, long d){
        //importante el orden en el que llegan
        long v1 = a <<24;
        long v2 = b <<16;
        long v3 = c << 8;
        areaT.addText(v1+"\t"+v2+"\t"+v3+"\t"+d);
        return (v1+v2+v3+d)/100;
    }
    
    //Se analizan solo los paquetes que contienen informacion para ser graficada
    @Override
    public void dataMessage(ZNetRxResponse packet) {
        addr64NamesG = xbeeCG.getXBeesWithID();
        int idData = packet.getData()[0];
        if(idData == 0xFB || idData == 0xFD || idData == 0xFE){
            int[] data = packet.getData();
            XBeeAddress64 xb64 = packet.getRemoteAddress64();
            String name;
            float value1;
            if(!XBeegraphicData.containsKey(xb64)){
                if(addr64NamesG.containsKey(xb64)){
                    name = addr64NamesG.get(xb64);
                }else{
                    name = "NoIDName";
                }
                addGraphicData(xb64, data[1], name);
            }else{
                if(addr64NamesG.containsKey(xb64)){
                    name = addr64NamesG.get(xb64);
                }else{
                    name = "NoIDName";
                }
                changeTitleGraphicData(xb64, name);
            }
            if(idData == 0xFB){
                for(int i=0; i<data[1]; i++){
                    value1 = convertDataType1(data[(i+1)*2], data[(2*i)+3]);
                    XBeegraphicData.get(xb64).addData(value1, i);
                }
            }else if(idData == 0xFD){
                for(int i=0; i<data[1]; i++){
                    value1 = convertDataType2(data[(3*i)+2], data[3*(i+1)], data[(3*i)+4]);
                    XBeegraphicData.get(xb64).addData(value1, i);
                }
            }else{
                areaT.addText(name+":");
                for(int i=0; i<data[1]; i++){
                    areaT.addText(data[(4*i)+2] + "\t" + data[(4*i)+3] + "\t" + data[4*(i+1)] + "\t" + data[(4*i)+5]);
                    value1 = convertDataType3(data[(4*i)+2], data[(4*i)+3], data[4*(i+1)], data[(4*i)+5]);
                    areaT.addText(Double.toString(value1)+"\n");
                    XBeegraphicData.get(xb64).addData(value1, i);
                }
            }
        }
    }
    //metodo para resetear la aplicacion
    public void resetAplicacion1(){
        addr64NamesG = null;
        XBeegraphicData = null;
        addr64NamesG = xbeeCG.getXBeesWithID();
        XBeegraphicData = new HashMap<>();
    }

    public void setVisibleAplicacion2(boolean indicator){
        setVisible(indicator);
    }

    @Override
    public void atMessage(String apiID, AtCommandResponse atResponse) {
        addr64NamesG = xbeeCG.getXBeesWithID();
    }

    @Override
    public void remoteATMessage(String apiID, RemoteAtResponse atRemoteResponse) {
        addr64NamesG = xbeeCG.getXBeesWithID();
    }

    @Override
    public void otherMessage(XBeeResponse resp) {
        
    }
    
}
