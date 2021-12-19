/*
 * 1.0 code.
 */

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TalkClientApplet extends Applet implements Runnable {
    Socket socket;
    DataOutputStream os;
    DataInputStream is;
    TextField portField, message;
    TextArea display;
    Button button;
    int dataPort;
    boolean trysted;
    Thread receiveThread;
    String host;
    boolean DEBUG = false;

    public void init() {
        //Get the address of the host we came from.
        host = getCodeBase().getHost();

        //Set up the UI.
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridBag);

        message = new TextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.setConstraints(message, c); 
        add(message);

        display = new TextArea(10, 40);
        display.setEditable(false);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        gridBag.setConstraints(display, c); 
        add(display);

        Label l = new Label("Enter the port (on host " + host
                            + ") to send the request to:", 
                            Label.RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gridBag.setConstraints(l, c); 
        add(l);

        portField = new TextField(6);
        c.fill = GridBagConstraints.NONE;
        gridBag.setConstraints(portField, c); 
        add(portField);

        button = new Button("Connect");
        gridBag.setConstraints(button, c); 
        add(button);

        validate();
    }

    public synchronized void start() {
        if (DEBUG) {
            System.out.println("In start() method.");
        }
        if (receiveThread == null) {
            trysted = false;
            portField.setEditable(true);
            button.enable();
            os = null;
            is = null;
            socket = null;
            receiveThread = new Thread(this);
            receiveThread.start();
            if (DEBUG) {
                System.out.println("  Just set everything to null and started thread.");
            }
        } else if (DEBUG) {
            System.out.println("  receiveThread not null! Did nothing!");
        }
    }

    public synchronized void stop() {
        if (DEBUG) {
            System.out.println("In stop() method.");
        }
        receiveThread = null;
        trysted = false;
        portField.setEditable(true);
        button.enable();
        notify();

        try { //Close input stream.
            if (is != null) {
                is.close();
                is = null;
            }
        } catch (Exception e) {} //Ignore exceptions.

        try { //Close output stream.
            if (os != null) {
                os.close();
                os = null;
            }
        } catch (Exception e) {} //Ignore exceptions.

        try { //Close socket.
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (Exception e) {} //Ignore exceptions.
    }
            
    public Insets insets() {
        return new Insets(4,4,5,5);
    }

    public void paint(Graphics g) {
        Dimension d = size();
        Color bg = getBackground();

        g.setColor(bg);
        g.draw3DRect(0, 0, d.width - 1, d.height - 1, true);
        g.draw3DRect(3, 3, d.width - 7, d.height - 7, false);
    }

    public synchronized boolean action(Event event, Object arg) {
        int port;
        
        if (DEBUG) {
            System.out.println("In action() method.");
        }

        if (receiveThread == null) {
            start();
        }

        if (!trysted) {
        //We need to attempt a rendezvous.

            if (DEBUG) {
                System.out.println("  trysted = false. "
                                   + "About to attempt a rendezvous.");
            }

            //Get the port the user entered...
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException e) {
                //No integer entered. 
                display.appendText("Please enter an integer below.\n");
                return true;
            }
            //...and rendezvous with it.
            rendezvous(port);

        } else { //We've already rendezvoused. Just send data over.
            if (DEBUG) {
                System.out.println("  trysted = true. "
                                   + "About to send data.");
            }
            String str = message.getText();
            message.selectAll();

            try {
                os.writeUTF(str);
                os.flush();
            } catch (IOException e) {
                display.appendText("ERROR: Applet couldn't write to socket.\n");
                display.appendText("...Disconnecting.\n");
                stop();
                return true;
            } catch (NullPointerException e) {
                display.appendText("ERROR: No output stream!\n");
                display.appendText("...Disconnecting.\n");
                stop();
                return true;
            }
            display.appendText("Sent: " + str + "\n");
        }
        return true;
    }

    synchronized void waitForTryst() {
        //Wait for notify() call from action().
        try {
            wait();        
        } catch (InterruptedException e) {}

        if (DEBUG) {
            System.out.println("waitForTryst about to return. "
                               + "trysted = " + trysted + ".");
        }

        return;
    }

    public void run() {
        String received = null;

        waitForTryst();

        //OK, now we can send messages.
        while (Thread.currentThread() == receiveThread) {
            try { 
                //Wait for data from the server.
                received = is.readUTF();

                //Display it.
                if (received != null) {
                    display.appendText("Received: " + received + "\n");
                } else { //success but no data...
                    System.err.println("readUTF() returned no data");
                }
            } catch (EOFException e) { //Stream has no more data.
                display.appendText("NOTE: Other applet is disconnected.\n");
                //display.appendText("...Disconnecting.\n");
                //stop();
                return;
            } catch (NullPointerException e) { //Stream doesn't exist.
                display.appendText("NOTE: Disconnected from server.\n");
                display.appendText("...Completing disconnect.\n");
                stop();
                return;
            } catch (IOException e) { //Perhaps a temporary problem?
                display.appendText("NOTE: Couldn't read from socket.\n");
                //display.appendText("...Disconnecting.\n");
                //stop();
                return;
            } catch (Exception e) { //Unknown error. Throw tantrum.
                display.appendText("ERROR: Couldn't read from socket.\n");
                display.appendText("...Disconnecting.\n");
                System.err.println("Couldn't read from socket.");
                e.printStackTrace();
                stop();
                return;
            }
        }
    }

    private void rendezvous(int port) {
        //Try to open a socket to the port.
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            display.appendText("ERROR: Can't find host: " + host + ".\n");
            return;
        } catch (IOException e) {
            display.appendText("ERROR: Can't open socket on rendezvous port "
                                   + port + " (on host " + host + ").\n");
            return;
        }

        //Try to open streams to read and write from the socket.
        try {
            os = new DataOutputStream(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            display.appendText("ERROR: Created data socket but can't "
                               + "open stream on it.\n");
            display.appendText("...Disconnecting.\n");
            stop();
            return;
        }   

        if ((os != null) & (is != null)) {
            if (DEBUG) {
                System.out.println("Successful rendezvous.");
                System.out.println("socket = " + socket);
                System.out.println("output stream = " + os);
                System.out.println("input stream = " + is);
            }
            //Let the main applet thread know we've successfully rendezvoused.
            portField.setEditable(false);
            button.disable();
            trysted = true;
            notify();
        } else {
            display.appendText("ERROR: Port is valid but communication failed. "
                                   + "Please TRY AGAIN.\n");
        }
    }

}
