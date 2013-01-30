/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XbeeController;

/**
 *
 * @author ALDAJO
 */
public interface MessageListener {
    
        public void dataMessage(int[] data);

        public void atMessage(String apiID, int[] data);

        public void remoteATMessage(String apiID, int[] data);

        public void nodeDetector(String nodeIdentifier);
}