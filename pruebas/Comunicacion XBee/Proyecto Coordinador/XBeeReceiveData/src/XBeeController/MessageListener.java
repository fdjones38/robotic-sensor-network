/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XBeeController;

import com.rapplogic.xbee.api.AtCommandResponse;
import com.rapplogic.xbee.api.RemoteAtResponse;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;

/**
 *
 * @author Alejandro....
 */
public interface MessageListener {
    
        /**
         * 
         * @param packet 
         */
        public void dataMessage(ZNetRxResponse packet);

        /**
         * 
         * @param apiID
         * @param atResponse 
         */
        public void atMessage(String apiID, AtCommandResponse atResponse);

        public void remoteATMessage(String apiID, RemoteAtResponse atRemoteResponse);
        
        public void otherMessage(XBeeResponse resp);
        
}