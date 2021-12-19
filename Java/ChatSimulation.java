
/*
   This applet simulates a network chat, in which the user chats with
   someone over a network.  Here, there is no network.  The partner is
   simulated by a thread that emits strings at random.  The point
   of the simulation is to show how to use a separate thread for receiving
   information.
   
   The applet has an input box where the user enters messages.  When
   the user presses return, the message is posted to a text area.  In
   a real chat program, it would be transmitted over the network.
   When a (simulated) message is received from the other side, it is
   also posted to this text area.  
   
   The partner thread is started the first time the user sends
   a message.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatSimulation extends JApplet 
                             implements ActionListener, Runnable {

   static String[] incomingMessages = {
      "Say, how about those Mets",
      "My favorite color is gray.  What's yours?",
      "In a World without Walls, who needs Windows?  "
          + "In a World without Fences, who needs Gates?",
      "An empty vessel makes the most noise.",
      "Can you tell me what a Thread is?",
      "Do you always follow style rules when you program?",
      "Sometimes, I really, really hate computers.",
      "Do you remember the Pythagorean theorem?",
      "Have you tried the GIMP image processing program?",
      "I was thinking, How come wrong numbers are never busy?",
      "Do you know Murphy's Law?  You should.",
      "Are you getting bored yet"
   };

   JTextArea transcript;  // A text area that shows transmitted and
                          // received messages.
                          
   JTextField input;  // A text-input box where the use enters
                      // outgoing messages.
                      
   Thread runner;    // The thread the simulated the incoming messages.
   
   boolean running;  // This is set to true when the thread is created.
                     // when it becomes false, the thread should exit.
                     // It is set to false in the applet's stop() method.

   public void init() {
         // Initialize the applet.
      setBackground(Color.lightGray);
      getContentPane().setBackground(Color.lightGray);
      getContentPane().setLayout(new BorderLayout(3,3));
      transcript = new JTextArea();
      transcript.setBackground(Color.white);
      transcript.setEditable(false);
      getContentPane().add(new JScrollPane(transcript),BorderLayout.CENTER);
      JPanel bottom = new JPanel();
      bottom.setBackground(Color.lightGray);
      bottom.setLayout(new BorderLayout(3,3));
      getContentPane().add(bottom,BorderLayout.SOUTH);
      input = new JTextField();
      input.setBackground(Color.white);
      input.addActionListener(this);
      bottom.add(input,BorderLayout.CENTER);
      JButton send = new JButton("Send");
      send.addActionListener(this);
      bottom.add(send,BorderLayout.EAST);
      bottom.add(new JLabel("Text to send:"),BorderLayout.WEST);
   }
   
   public Insets getInsets() {
         // Leave a border around the edges of the applet.
      return new Insets(3,3,3,3);
   }
   
   public void actionPerformed(ActionEvent evt) {
         // This will be called when the user clicks the "Send" button
         // or presses return in the text-input box.  It posts the
         // contents of the input box to the transcript.  If the
         // thread is not running, it creates and starts a new thread.
      transcript.append("SENT: " + input.getText() + "\n\n");
      input.selectAll();
      input.requestFocus();
      if (running == false) {
         runner = new Thread(this);
         running = true;
         runner.start();
      }
   }
   
   public void postIncomingMessage(final String message) {
         // This method posts the message String to the transcript
         // in a thread-safe way.  It does this by creating a Runnable
         // object whose run method will do the posting, and 
         // scheduling that object to be run in the event-handling
         // thread, where it is safe to work on Swing components.
      Runnable poster = new Runnable() {
            public void run() {
               if (running)
                  transcript.append("RECEIVED: " + message + "\n\n");
            }
         };
      SwingUtilities.invokeLater(poster);
   }
  
   public void run() {
         // The run method just posts messages to the transcript
         // at random intervals of 2 to 12 seconds.
      postIncomingMessage("Hey, hello there! Nice to chat with you.");
      while (running) {
         try {
             // Wait a random time from 2000 to 12000 milliseconds.
             Thread.sleep( 2000 + (int)(10000*Math.random()) );
         }
         catch (InterruptedException e) {
         }
         int msgNum = (int)(Math.random() * incomingMessages.length);
         postIncomingMessage(incomingMessages[msgNum]);
      }
   }

   public void stop() {
         // When applet is stopped, make sure that the tread will
         // terminate by setting running to false.
      running = false;
      runner = null;
   }
   

} // end class ChatSimulation

