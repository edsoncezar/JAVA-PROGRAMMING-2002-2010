
/*
    A ChatWindow supports two-way chatting between two
    users over the Internet.  The constructor for a ChatWindow
    requires a connected Socket.  Messages are sent and received through
    this socket.  The window has a text-input box where the user can
    type messages.  When the user presses return or clicks a "Send"
    button, the text in this box is transmitted over the connection.
    The window runs a thread which reads messages received from the
    other side of the connection.  Messages that are sent or
    received are displayed in a "transcript" that fills most of
    the window.  There is a Close button that the user can click
    to close the connection and the window.  Clicking in the window's
    close box has the same effect.
       The class includes a main program so it can be run as a
    stand-alone application.  In this case, the program can act as
    a server that waits for a connection, or it can act as a client
    that connects to a ChatWindow server.  After a connection
    is established, ChatWindows are opened by the client and
    by the server, and the users can chat with each other.  To run
    as a server listening on port 17171, use the command 
    "java ChatWindow -s".  To listen on a different port, 
    specify the port number as the second command-line argument.
    To run as a client. use "java ChatWindow <server>" where
    <server> is the name or IP number of the computer on which the
    program is running as a server.  The listening port of the
    server can be specified as a second command-line parameter.
       Of course, ChatWindows can also be used by other
    programs.  In particular, they are designed to work with the
    ConnectionBroker server and the BrokeredChat applet.
       This program depends on the non-standard TextReader
    class, which is defined in TextReader.java.
*/ 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;


public class ChatWindow extends JFrame 
                implements ActionListener, WindowListener, Runnable {
                
   /* This part of the class are used when it is run
      as a standalone application.  In that case, it makes a
      connection, either as a server or client, then creates an
      object of type ChatWindow to handle the connection. */
                
   static final String HANDSHAKE = "<Independent Connection Window>";
      // The HANDSHAKE string is sent by each end of the connection
      // to the other end, as soon as the connection is opened.
      // This is done to verify that the partner in the connection
      // is another instance of this program.  (This is used in
      // the main() routine.)

   static final int DEFAULT_PORT = 17171;  // Port on which the
                                           // server listens, if 
                                           // none is specified
                                           // on the command line.

   public static final void main(String[] args) {
   
      int port;  // Port on which the server listens.
      Socket connection;  // For communication with the
                          //    program on the other end.   
      TextReader in;   // A stream for receiving the handshake.
      PrintWriter out; // A stream for sending the handshake.
      String message;  // The message from the other side,
                       //    which should be the handshake message.
      
      if (args.length == 0) {
           // There must be at least one command line argument.
         System.out.println("Usage:  java ChatWindow <server-computer> [<port>]");
         System.out.println("    or  java ChatWindow -s [<port>]");
         return;
      }
      
      /* Get the port number from the second command-line paramter,
         if there is one.  Otherwise, use the default port. */
      
      if (args.length == 1)
         port = DEFAULT_PORT;
      else {
         try {
            port = Integer.parseInt(args[1]);
         }
         catch (NumberFormatException e) {
            System.out.println(args[1] + " is not a legal port number.");
            return;
         }
      }
      
      /* Open a connection.  If the first command-line argument is
         "-s", then wait for a connection as a server.  Otherwise,
         use the first argument as the computer name and connect
         to that computer as a client.  Once the connection is
         made, exchange handshake messages. */
      
      try {
         if (args[0].equalsIgnoreCase("-s")) {
            ServerSocket listener = new ServerSocket(port);
            System.out.println("Listening on port " + listener.getLocalPort());
            connection = listener.accept();
            listener.close();
         }
         else {
            connection = new Socket(args[0],port);
         }
         out = new PrintWriter(connection.getOutputStream());
         out.println(HANDSHAKE);
         out.flush();
         in = new TextReader(connection.getInputStream());
         message = in.getln();
         if (! message.equals(HANDSHAKE) )
            throw new IOException("Connected program is not ChatWindow");
         System.out.println("Connected.");
      }
      catch (Exception e) {
         System.out.println("Error opening connection.");
         System.out.println(e.toString());
         return;
      }
      
      /* Open a ChatWindow to handle the connection, and
         set its standalone flag to true (so that the program
         will terminate when the window is closed). */
      
      ChatWindow w = new ChatWindow("ChatWindow", connection);
      w.standalone = true;
   
   } // end main();


   /* The rest of the class specifies the behavior of
      ChatWindow objects. */

   static final char MESSAGE = '0'; // The first character of any
                                    // line sent over the connection
   static final char CLOSE = ']';   // is a command, either MESSAGE
                                    // or CLOSE.  When a CLOSE is
                                    // received, it indicates that 
                                    // the connection has been closed
                                    // from the other side.

   JTextArea transcript;    // For displaying messages sent and
                            //   and received over the network
                            //   (and also messages from the program).
                           
   JTextField messageInput; // Where the user types lines to be transmitted.
   
   Socket connection;    // The connection to the other end.  This
                         //   provided by the constructor.  It is 
                         //   set to null when the conenction closes.
                         
   TextReader incoming;  // Stream for receiving data. 

   PrintWriter outgoing; // Stream for transmitting data.
   
   Thread reader;  // A thread that reads the data received over
                   //    the connection.
   
   JButton clearButton;  // A button that clears the transcript.

   JButton sendButton;   // A button that transmits the string in
                         //     the messageInput box.  Pressing
                         //     return in the box will do the 
                         //     same thing.

   JButton closeButton;  // A button that closes the connection.
                         //    Clicking in the window's close box
                         //    is equivalent.  This closes the
                         //    connection, if it is not already dead,
                         //    and closes the window.
                        
   boolean  waitFor;  // This can be set by the constructor.  If
                      // so, the window will wait to receive an
                      // incoming message before allowing
                      // any data to be sent out.
   
   boolean standalone = false;  // This is set to true by main()
                                //   when this class is run as a
                                //   standalone program.  It is 
                                //   checked in windowClosed()
                                //   which will call System.exit()
                                //   if this variable is true.
                                
   
   public ChatWindow(String title, Socket connection) {
      this(title,connection,false);
   }

   public ChatWindow(String title, Socket connection, boolean waitFor) {
         // Constructor.  Create the window and open it
         // on the screen.  The first parameter gives the
         // title for the title bar of the window.  The
         // second should be a Socket that is ready to
         // send and receive data.  If waitFor is true, the
         // widow will wait to receive some data over the
         // network before allowing any data to be sent out.
   
      super(title);
      
      this.connection = connection;

      addWindowListener(this);

      /* Create all the components for the window. */

      transcript = new JTextArea();
      transcript.setBackground(Color.white);
      transcript.setEditable(false);  // It's for display only.
      transcript.setFont( new Font("Monospaced", Font.PLAIN, 12) );

      messageInput = new JTextField();
      messageInput.addActionListener(this);
      messageInput.setBackground(Color.white);

      closeButton = new JButton("Close");
      closeButton.addActionListener(this);
      
      clearButton = new JButton("Clear Transcript");
      clearButton.addActionListener(this);
      
      sendButton = new JButton("Send");
      sendButton.addActionListener(this);
      
      /* Lay out the components. */
      
      JPanel bottom = new JPanel();
      bottom.setLayout(new GridLayout(2,1));
      JPanel inputBar = new JPanel();
      inputBar.setLayout(new BorderLayout());
      inputBar.add(messageInput, BorderLayout.CENTER);
      inputBar.add(new JLabel("Enter text to send: "), BorderLayout.WEST);
      inputBar.setBorder(BorderFactory.createEtchedBorder());
      JPanel buttonBar = new JPanel();
      buttonBar.setLayout(new GridLayout(1,3));
      buttonBar.add(closeButton);
      buttonBar.add(clearButton);
      buttonBar.add(sendButton);
      bottom.add(inputBar);
      bottom.add(buttonBar);
      getContentPane().add(bottom, BorderLayout.SOUTH);
      getContentPane().add(transcript, BorderLayout.CENTER);
      
      /* Get the streams for reading and writing data.
         There should be no error here.  If there is,
         set this.connection to null and show a message
         in the transcript. */
      
      try {
         incoming = new TextReader( connection.getInputStream() );
         outgoing = new PrintWriter( connection.getOutputStream() );
      }
      catch (IOException e) {
         transcript.setText("Error opening I/O streams!\n"
                              + "Connection can't be used.\n"
                              + "You can close the window now.\n");
         closeButton.setText("Close Window");
         sendButton.setEnabled(false);
         clearButton.setEnabled(false);
         this.connection = null;
      }
      
      /* Create the thread for reading data from the connection,
         unless an error just occured. */
      
      if (this.connection != null) {
         reader = new Thread(this);
         reader.start();
         if (waitFor) {
            sendButton.setEnabled(false);
            transcript.setText("Waiting for connection.\n");
            this.waitFor = true;
         }
      }

      /* Size and show the window. */
      
      setSize(550,350);
      setLocation(50,80);
      show();
      
      /* Set close operation to allow the window listener to do the work. */
      
      setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

   } // end constructor
   
   
   public void actionPerformed(ActionEvent evt) {
         // Respond when the user clicks one of the buttons
         // or presses return in the message input box.
      Object source = evt.getSource();
      if (source == clearButton)
         transcript.setText("");
      else if (source == sendButton || source == messageInput)
         doSend();
      else if (source == closeButton)
         doClose();
   }
   

   synchronized void doSend() {
         // Called by actionPerformed when the user wants to
         // transmit the string in the messageInput box.
      if (connection == null) {
            // There is no connection, so we can't transmit.
         return;
      }
      if (waitFor) {
            // Still waiting to receive incoming data.  Data can't
            // be sent until something is received.
         return;
      }
      String message = messageInput.getText();
      outgoing.println(MESSAGE + message);
      outgoing.flush();
      if (outgoing.checkError()) {
            // If the output stream has gone bad, end the 
            // connection by calling doForceClose().
         transcript.append("ERROR:    An error occured while sending data.\n");
         doForceClose();
      }
      else
         transcript.append("SENT:     " + message + '\n');
      messageInput.selectAll();
      messageInput.requestFocus();
   }


   void doClose() {
         // Called when the user clicks the close button or the
         // close box on the window.  Close the connection, if
         // it is still open.  Then close the window.
      if (connection != null) {
            // First, inform the other side that the connection
            // is being closed.
         outgoing.println(CLOSE);
         outgoing.flush();
         try {
            connection.close();  // Close the connection.
         }
         catch (IOException e) {
         }
         connection = null;  // So we know the connection is closed.
      }
      dispose();  // Close the window.
   }   
   

   void doForceClose() {
         // This is called when an error occurs either in
         // sending or transmitting data or when a CLOSE
         // message has been received from the other side.
         // Put a message in the transcript, change the 
         // user interface to show that the show is over,
         // and close the connection.  
      if (connection == null) {
           // There is no connection to be closed.
           // (This really shouldn't happen, but let's be safe.
         return;
      }
      closeButton.setText("Close Window");
      sendButton.setEnabled(false);
      clearButton.setEnabled(false);
      try {
         connection.close();
      }
      catch (IOException e) {
      }
      transcript.append("\nCLOSED:   Connection has been closed.\n");
      transcript.append("          You can now close the window.\n");
      connection = null;
   }
   
   
   void addToTranscript(final String message) {
         // Add the message to the transcript, followed by
         // a line-separator.  This is done in a thread-safe
         // manner, by scheduling a Runnable object to be
         // executed in the event-handling thread.
      Runnable postit = new Runnable() {
            public void run() {
               transcript.append(message + "\n");
            }
         };
      SwingUtilities.invokeLater(postit);
   }
   

   void closeFromThread() {
         // Call doForceClose() in a thread-safe manner.
      Runnable closeit = new Runnable() {
            public void run() {
               doForceClose();
            }
         };
      SwingUtilities.invokeLater(closeit);
   }
   
   
   public void run() {
        // The run method for the thread the reads the data
        // that is received over the connection.  It simply
        // reads lines of data and posts them to the transcript
        // until the connection is closed or an error occurs.
      try {
         while (true) {
            String message = incoming.getln();
            if (connection == null) {
                 // The connection has been closed somehow.
               break;
            }
            if (waitFor) {
               addToTranscript("Connected.\n");
               sendButton.setEnabled(true);
               waitFor = false;
            }
            else if (message.length() > 0) {
               if (message.charAt(0) == CLOSE) {
                     // The other side has closed the connection.
                     // Print a message and end.
                  addToTranscript("\nConnection closed from other side.");
                  closeFromThread();
                  break;
               }
               message = message.substring(1); 
               addToTranscript("RECEIVED: " + message);
            }
         }
      }
      catch (Exception e) {
            // If an error occurs during input, show a message
            // and close the connection.
         synchronized (this) {
            if (connection != null) {
               addToTranscript("ERROR:    An error occurred while receiving data.");
               closeFromThread();
            }
         }
      }
   }
   

   public void windowClosing(WindowEvent evt) {
          // Part of the WindowListener interface.  It is 
          // called when the user clicks the window's close box.
          // Call doClose() to close the connection and the window.
      doClose();
   }
   

   public void windowClosed(WindowEvent evt) {
         // This is called after the window has been closed.
         // If this was a standalone program, shut down the
         // Java virtual machine (to end the program).
      if (standalone)
         System.exit(0);
   }
   

   // Other methods required by the WindowListener interface.
   
   public void windowOpened(WindowEvent evt) { }
   public void windowIconified(WindowEvent evt) { }
   public void windowDeiconified(WindowEvent evt) { }
   public void windowActivated(WindowEvent evt) { }
   public void windowDeactivated(WindowEvent evt) { }

} // end class ChatWindow
