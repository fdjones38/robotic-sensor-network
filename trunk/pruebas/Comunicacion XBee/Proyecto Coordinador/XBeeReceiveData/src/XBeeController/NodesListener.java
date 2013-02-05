/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XbeeController;

import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import java.util.HashMap;

/**
 *
 * @author ALDAJO
 */
public interface NodesListener {
    
    public void newNodeAdded(XBeeAddress64 addr64);
    
    public void newNodeWithIDAdded(XBeeAddress64 addr64, String name);
    
    public void removedID(XBeeAddress64 addr64);
    
    public void addedID(XBeeAddress64 addr64, String name);
    
    public void updateNumNamesXBeeList(HashMap<String, XBeeAddress64> numNamesAddr64);
    
    public void exceptionSendATNND(XBeeException ex);
    
}
