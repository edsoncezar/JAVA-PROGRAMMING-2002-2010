/*
 * 1.1 version.
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TalkClientApplet extends Applet 
				      implements Runnable,
						 ActionListener {
    Socket socket;
    BufferedWriter os;
    BufferedReader is;
    TextField portField, message;
    TextArea display;
    Button button;
    int dataPort;
    boolean trysted;
    Thread receiveThread;
    String host;
    boolean DEBUG = false;
    String newline;

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
	message.addActionListener(this);
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
	portField.addActionListener(this);
        add(portField);

        button = new Button("Connect");
        gridBag.setConstraints(button, c); 
	button.addActionListener(this);
        add(button);

	newline = System.getProperty("line.separator");
    }

    public synchronized void start() {
        if (DEBUG) {
            System.out.println("In start() method.");
        }
        if (receiveThread == null) {
            trysted = false;
            portField.setEditable(true);
            button.setEnabled(true);
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
        button.setEnabled(true);
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

    public synchronized void actionPerformed(ActionEvent event) {
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
                display.append("Please enter an integer below."
			       + newline);
                return;
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
                os.write(str);
		os.newLine();
                os.flush();
            } catch (IOException e) {
                display.append("ERROR: Applet couldn't write to socket."
			       + newline);
                display.append("...Disconnecting."
			       + newline);
                stop();
                return;
            } catch (NullPointerException e) {
                display.append("ERROR: No output stream!"
			       + newline);
                display.append("...Disconnecting."
			       + newline);
                stop();
                return;
            }
            display.append("Sent: " + str + newline);
        }
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
                received = is.readLine();

                //Display it.
                if (received != null) {
                    display.append("Received: " + received 
				   + newline);
                } else { //success but no data...
                    System.err.println("End of stream?");
		    return; //XXX
                }
            } catch (IOException e) { //Perhaps a temporary problem?
                display.append("NOTE: Couldn't read from socket.\n");
                return;
            }
        }
    }

    private void rendezvous(int port) {
        //Try to open a socket to the port.
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            display.append("ERROR: Can't find host: " + host
			   + newline);
            return;
        } catch (IOException e) {
            display.append("ERROR: Can't open socket on rendezvous port "
                           + port + " (on host " + host + ")."
			   + newline);
            return;
        }

        //Try to open streams to read and write from the socket.
        try {
            os = new BufferedWriter(
		     new OutputStreamWriter(socket.getOutputStream()));
            is = new BufferedReader(
		     new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            display.append("ERROR: Created data socket but can't "
                           + "open stream on it."
			   + newline);
            display.append("...Disconnecting." + newline);
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
            button.setEnabled(false);
            trysted = true;
            notify();
        } else {
            display.append("ERROR: Port is valid but communication failed. "
                           + "Please TRY AGAIN." + newline);
        }
    }

}
