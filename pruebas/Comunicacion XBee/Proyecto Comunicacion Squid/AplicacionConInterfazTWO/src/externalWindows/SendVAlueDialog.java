/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package externalWindows;

import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author ALDAJO
 */
public class SendVAlueDialog extends JDialog {
    private JLabel label1, label2;
    private JButton sendButton, cancelButton;
    private JPanel panel1, panel2, panel3;
    private JComboBox listNums;
    
    private void initComponents(){
        label1 = new JLabel("Select a device num");
        label2 = new JLabel("Send ID");
        
        sendButton = new JButton("Send");
        cancelButton = new JButton("Cancel");
        
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        
        panel1.setLayout(new GridLayout(1,3,5,5));
        panel2.setLayout(new GridLayout(1,3,5,5));
        panel3.setLayout(new GridLayout(1,2,5,5));
        
        panel1.add(label1);
        panel1.add(label2);
    }
}
