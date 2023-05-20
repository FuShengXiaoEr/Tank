package org.example.nettychatroom;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/19 21:23
 * Version 1.0
 * @description
 **/
public class ClientFrame extends Frame {
    public static final ClientFrame INSTANCE = new ClientFrame();

    private TextArea ta = new TextArea();
    private TextField tf = new TextField();
    private Client c;

    private ClientFrame(){
        this.setSize(200,400);
        this.setLocation(100,20);
        this.add(ta,BorderLayout.CENTER);
        this.add(tf,BorderLayout.SOUTH);
        this.setTitle("nettychatroom");

        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // send to server
                c.send(tf.getText());
//                ta.setText(ta.getText() + tf.getText());
//                tf.setText("");
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                c.closeConnect();
                System.exit(0);
            }
        });
    }

    public void connectToServer(){
        c = new Client();
        c.connect();
    }

    public static void main(String[] args) {
        ClientFrame frame = ClientFrame.INSTANCE;
        frame.setVisible(true);
        frame.connectToServer();
    }

    public void updateText(String sr) {
        ta.setText(ta.getText()+sr+"\r\n");
    }
}
