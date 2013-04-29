/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import XBeeController.MessageListener;
import XBeeController.XBeeController;
import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import javax.swing.JOptionPane;

/**
 * Programa que permite el recibir una cadena de datos e imprimirlos, donde se
 * visualiza la dirección de los dispositivos y los datos que se reciben ya
 * procesados. Funciona con el formato de envío de datos creados para las
 * tarjetas arduino. En el arduino está instalado el programa
 * LightFollowerZigbee.
 *
 * @author Alejandro Gómez Florez.
 *
 */
public class XBeeDataReceiver implements MessageListener {

    /**
     * Indica que los datos recibidos están dividos en dos bytes.
     */
    private static final int TWO_BYTES_INDICATOR = 0xFB;
    /**
     * Indica que los datos recibidos están dividos en tres bytes.
     */
    private static final int THREE_BYTES_INDICATOR = 0xFD;
    /**
     * Indica que los datos recibidos están dividos en cuatro bytes.
     */
    private static final int FOUR_BYTES_INDICATOR = 0xFE;
    private static final int NUM_DECIMAL = 100;
    private XBeeController xbeeC;

    /**
     * Default constructor.
     */
    public XBeeDataReceiver() {
        xbeeC = new XBeeController();
    }

    public void start() {
        try {
            boolean oppenedPort = xbeeC.openSerialPort();

            if (!oppenedPort) {
                System.out.println("The serial port was not openned.");
                return;
            }

            xbeeC.addMessageListener(this);
        } catch (XBeeException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Problems in the port\n"
                    + ex.getMessage());
        }
    }

    /**
     * Convierte bytes en float.
     *
     * @param bytes
     * @return
     */
    public static float convertDataType(int[] bytes) {
        //variable que nos almacena el valor procesado por el algoritmo
        int fstResult = 0;

        // Leer el número en bytes.
        System.out.print("bytes:");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print("\t"+bytes[i]);
            int calc = (int) (bytes[i] << ((bytes.length - 1 - i) * 8));
            fstResult = fstResult | calc;
        }
        System.out.println("");

//         Se convierte a números decimales.
        float result = ((float)fstResult) / NUM_DECIMAL;

        return (float)result;
    }

    /**
     * Convertir los datos recibidos en bits a float. El paquete que se recibe
     * cumple con el siguiente formato:
     *
     * data[0]: tamaño de cada uno de los datos. Puede ser 2,3 o 4 bytes.
     *
     * data[1]: número de valores numéricos que se van a recibir.
     *
     * data[2...n]: valores.
     *
     * @param packet
     */
    private float[] convertIncommingData(ZNetRxResponse packet) {
        // Obtiene el id de los datos.
        int idData = packet.getData()[0];

        // Validar que el paquete recibido cumpla con el formato.
        if (idData != TWO_BYTES_INDICATOR && idData != THREE_BYTES_INDICATOR && idData != FOUR_BYTES_INDICATOR) {
            return null;
        }

        // Almacena la informacion: direccion de 64bits del módulo xbee y
        // paquete de datos.
        int[] data = packet.getData();


        // Número de valores numéricos que se van a recibir.
        int numData = data[1];
        
        System.out.print("NUmero de datos:" + numData+"\t");


        float[] convertedValues = new float[numData];

        // Tamaño de los datos
        int size = -1;
        
        switch (idData) {
            case TWO_BYTES_INDICATOR:
                size = 2;
                break;
            case THREE_BYTES_INDICATOR:
                size = 3;
                break;
            case FOUR_BYTES_INDICATOR:
                size = 4;
                break;
        }
        
        System.out.print("particiones:" + size + "\n");

        int index = 2;
        // Recorrer el número de valores en el paquete.
        for (int i = 0; i < numData; i++) {
            int dataTmp[] = new int[size];

            // Recorrer el número de bytes por cada valor/dato.
            for (int j = 0; j < size; j++) {
                dataTmp[j] = data[index++];
            }

            // Convertir de bits a float.
            convertedValues[i] = convertDataType(dataTmp);
        }

        return convertedValues;
    }

    @Override
    public void dataMessage(ZNetRxResponse packet) {
        XBeeAddress64 xb64 = packet.getRemoteAddress64();

        // Imprime la dirección.
        System.out.println("Address: " + xb64.toString());

        float[] receivedValues = null;

        try {
            receivedValues = convertIncommingData(packet);
        } catch (Exception e) {
            Exception exception = new Exception("Error: converting received data.", e);
            exception.printStackTrace();
        }

        // Validar que los valores recibidos no sean nulos.
        if (receivedValues == null) {
            System.out.println("Valores recibidos nulos");
            return;
        }

        System.out.print("Valores recibidos: ");
        for (float val : receivedValues) {
            System.out.print(val + " ");
        }

        System.out.println();
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
