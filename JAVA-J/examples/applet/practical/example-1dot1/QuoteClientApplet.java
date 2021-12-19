/*
 * 1.1 version.
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class QuoteClientApplet extends Applet 
                               implements ActionListener {
    boolean DEBUG = false;
    InetAddress address;
    TextField portField;
    Label display;
    DatagramSocket socket;

    public void init() {
        //Initialize networking stuff.
        String host = getCodeBase().getHost();

        try {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            System.out.println("Couldn't get Internet address: Unknown host");
            // What should we do?
        }

        try {
            socket = new DatagramSocket();
        } catch (IOException e) {
            System.out.println("Couldn't create new DatagramSocket");
            return;
        }

        //Set up the UI.
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridBag);

        Label l1 = new Label("Quote of the Moment:", Label.CENTER);
        c.anchor = GridBagConstraints.SOUTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.setConstraints(l1, c); 
        add(l1);

        display = new Label("(no quote received yet)", Label.CENTER);
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridBag.setConstraints(display, c); 
        add(display);

        Label l2 = new Label("Enter the port (on host " + host
                             + ") to send the request to:", 
                             Label.RIGHT);
        c.anchor = GridBagConstraints.SOUTH;
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.NONE;
        gridBag.setConstraints(l2, c); 
        add(l2);

        portField = new TextField(6);
        gridBag.setConstraints(portField, c); 
        add(portField);

        Button button = new Button("Send");
        gridBag.setConstraints(button, c); 
        add(button);

        portField.addActionListener(this);
        button.addActionListener(this);
    }

    public Insets getInsets() {
        return new Insets(4,4,5,5);
    }

    public void paint(Graphics g) {
        Dimension d = getSize();
        Color bg = getBackground();

        g.setColor(bg);
        g.draw3DRect(0, 0, d.width - 1, d.height - 1, true);
        g.draw3DRect(3, 3, d.width - 7, d.height - 7, false);
    }

    void doIt(int port) {
        DatagramPacket packet;
        byte[] sendBuf = new byte[256];
        
        packet = new DatagramPacket(sendBuf, 256, address, port);

        try { // send request
            if (DEBUG) {
                System.out.println("Applet about to send packet to address "
                               + address + " at port " + port);
            }
            socket.send(packet);
            if (DEBUG) {
                System.out.println("Applet sent packet.");
            }
        } catch (IOException e) {
            System.out.println("Applet socket.send failed:");
            e.printStackTrace();
            return;
        }

        packet = new DatagramPacket(sendBuf, 256);

        try { // get response
            if (DEBUG) {
                System.out.println("Applet about to call socket.receive().");
            }
            socket.receive(packet);
            if (DEBUG) {
                System.out.println("Applet returned from socket.receive().");
            }
        } catch (IOException e) {
            System.out.println("Applet socket.receive failed:");
            e.printStackTrace();
            return;
        }

        String received = new String(packet.getData());
        if (DEBUG) {
            System.out.println("Quote of the Moment: " + received);
        }
        display.setText(received);
    }

    public void actionPerformed(ActionEvent event) {
        int port;
        
        try {
            port = Integer.parseInt(portField.getText());
            doIt(port);
        } catch (NumberFormatException e) {
            //No integer entered.  Should warn the user.
        }
    }
}
