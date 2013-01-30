/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package externalWindows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author ALDAJO
 */
public class ConfigXBeeDialog extends JDialog implements ActionListener{
    private JLabel label1, label2, label3;
    private JButton sendButton, cancelButton;
    private JComboBox listNums;
    private JTextField textName;
    private JPanel panel1, panel2, panel3;
    private String selectedName;
    private Integer selectedNum;
    
    public ConfigXBeeDialog(JFrame owner) {
        super(owner, "Message");
//        setLocationRelativeTo(owner);
        setLocation(400, 300);
        listNums = new JComboBox();
        initComponents();
    }
    
    public ConfigXBeeDialog() {
        setTitle("Message");
        listNums = new JComboBox();
        initComponents();
    }
    
    private void initComponents(){
        setLayout(new BorderLayout(5,5));
        setSize(500,75);
        setResizable(false);
                
        label1 = new JLabel("Select a device num");
        label2 = new JLabel("Write a new ID");
        label3 = new JLabel("Send ID");
        
        sendButton = new JButton("Send");
        cancelButton = new JButton("Cancel");
        textName = new JTextField();
        
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        
        panel1.setLayout(new GridLayout(1,3,5,5));
        panel2.setLayout(new GridLayout(1,3,5,5));
        panel3.setLayout(new GridLayout(1,2,5,5));
        
        panel1.add(label1);
        panel1.add(label2);
        panel1.add(label3);
        
        panel2.add(listNums);
        panel2.add(textName);
        
        panel3.add(sendButton);
        panel3.add(cancelButton);
        
        panel2.add(panel3);
        
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        
        sendButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }
    
    public void setElementToComBox(Integer element){
        listNums.addItem(element);
    }
    
    public void removeElemetsComboBox(){
        listNums.removeAllItems();
    }
    
    public Integer getSelectedNum(){
        Integer thempNum = selectedNum;
        selectedNum = null;
        return thempNum;
    }
    
    public String getSelectedName(){
        String thempName = selectedName;
        selectedName = null;
        return thempName;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == sendButton){
            if(textName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "por favor ingrese un ID");
            }else{
                //codigo del mensaje
                selectedNum = (Integer) listNums.getSelectedItem();
                selectedName = textName.getText();
                setVisible(false);
                listNums.removeAllItems();
            }
        }else if(e.getSource() == cancelButton){
            selectedNum = null;
            selectedName = null;
            setVisible(false);
            listNums.removeAllItems();
        }
    }
}
