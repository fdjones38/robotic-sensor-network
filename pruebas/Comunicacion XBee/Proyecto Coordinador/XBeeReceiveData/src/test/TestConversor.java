/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import application.XBeeDataReceiver;

/**
 *
 * @author rsr
 */
public class TestConversor {
    
    public static void main(String[] args) {
        //valor = -99882
        //valor decimal bits negativo = 4294867414
        //bits = 11111111111111100111100111010110
        //0b11010110 = 214
        //0b0111100100000000 = 30976
        //0b111111100000000000000000 = 16646144 java = 10092544
        //0b11111111000000000000000000000000 = 4278190080 java = -16777216
        //fragmentados:
        //0b11010110 = 214
        //0b01111001 = 121
        //0b11111110 = 254
        //0b11111111 = 255
        
//        int[] bytes ={255,254,121,214};
        int[] bytes ={255,255,248,56};
        
        float comparative = (float) -19.92;
      
        float valorConvertido = XBeeDataReceiver.convertDataType(bytes);
        System.out.println(valorConvertido);
//        if(valorConvertido == -99882){
        if(valorConvertido == comparative){
            System.out.println("Bien");
        }else
            System.out.println("mal");
        
    }
}
