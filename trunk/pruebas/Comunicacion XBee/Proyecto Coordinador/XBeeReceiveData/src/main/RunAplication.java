/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import application.XBeeDataReceiver;

/**
 *
 * @author ALDAJO
 */
public class RunAplication {
    
    public static void main(String[] args){
        XBeeDataReceiver xbeeRD = new XBeeDataReceiver();
        xbeeRD.start();
    }
    
}
