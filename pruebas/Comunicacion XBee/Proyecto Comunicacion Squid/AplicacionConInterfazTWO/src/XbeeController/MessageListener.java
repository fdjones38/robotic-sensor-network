/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XbeeController;

import com.rapplogic.xbee.api.AtCommandResponse;
import com.rapplogic.xbee.api.RemoteAtResponse;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;

/**
 *
 * @author ALDAJO
 */
public interface MessageListener {
    
        public void dataMessage(ZNetRxResponse packet);

        public void atMessage(String apiID, AtCommandResponse atResponse);

        public void remoteATMessage(String apiID, RemoteAtResponse atRemoteResponse);
        
        public void otherMessage(XBeeResponse resp);
        
}