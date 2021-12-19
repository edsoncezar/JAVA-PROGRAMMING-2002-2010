/**
 * ---- general information ----
 *
 * Pingpong.java: A Ping-Pong applet.
 * 
 * @version 1.0, 03 Apr 2002
 * @author  Jan Keller
 * @mail    post@jan-keller.de
 * @url     www.jan-keller.de
 * @place   Leipzig, Germany
 *
 * ---- Description ----
 *
 * JAVA programm for playing Ping-Pong on the internet.
 *
 * This program is postcard-ware.
 * If you like this program, send a postcard to:
 *
 *   Jan Keller
 *   Grassistraﬂe 31/315
 *   04107 Leipzig
 *   Germany
 *
 * Thanks in advance!
 * You are free to do anything you want with this program.
 * However, I am not responsible for any bugs in this program and
 * possible damage to hard- or software when using this program.
 * Please refer to this program when you are using it in one of your programs
 * Feel free to e-mail me at any time at post@jan-keller.de
 **/

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

public class Pingpong extends Applet implements Runnable, KeyListener, FocusListener, MouseListener {

/* ********************************************************************
   * variables
   ******************************************************************** */

   static int Yes = 1;
   static int No = 2;

   int Start = No;
   int speed = 20;  // update all 20 ms

   String Str1 = "Pingpong 1.0 by Jan Keller - 27th Mar 2002";
   String Str2 = "Press Space to Start";
   String Str3 = "Left Player wins";
   String Str4 = "Right Player wins";

   int Score[] = new int[2];        // holds the Score
   int Pos[]   = new int[2];        // holds the position of the ball
   int Vel[]   = new int[2];        // holds the velocity of the ball

   int yPos1 = 200, yPos2 = 200;    // starting positions of the paddles

   Thread Ball = null;              // The Ball
   boolean suspend = false;

   Image	 im;                    // just for double-buffering the images - reduces flickering
   Graphics  offscreen;
   Dimension d;


   java.awt.Label ScoreLabel;       // Shows the Score
   java.awt.Label BeginMsgLabel;    // Shows the heginning Msg

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public void init() {
     // resize, set background color
     setLayout(null);
     setSize(600,400);
     setBackground(Color.black);

     Pos[0] = 585; Pos[1] = 200;

     try {
       d = getSize();
       im = createImage(d.width, d.height);
       offscreen = im.getGraphics();
     }
     catch(Exception _ex) { offscreen = null; _ex.printStackTrace(); }


     ScoreLabel = new java.awt.Label("0 : 0", Label.CENTER);
     add(ScoreLabel);
     ScoreLabel.setFont(new Font("Dialog", Font.BOLD, 14));
     ScoreLabel.setBounds(250,3,100,14);
     ScoreLabel.setForeground(Color.white);
     ScoreLabel.setBackground(Color.black);

     BeginMsgLabel = new java.awt.Label(Str2, Label.CENTER);
     add(BeginMsgLabel);
     BeginMsgLabel.setFont(new Font("Dialog", Font.BOLD, 12));
     BeginMsgLabel.setBounds(200,200,200,20);
     BeginMsgLabel.setForeground(Color.white);
     BeginMsgLabel.setBackground(Color.black);

     addKeyListener(this);
     addFocusListener(this);
     addMouseListener(this);
     requestFocus();
   }

/* ********************************************************************
   * graphical output
   ******************************************************************** */



   public void paint(Graphics g) {
     if (offscreen != null) {
       paintMe(offscreen);
       g.drawImage(im, 0, 0, this);
       return;
     } else {
       paintMe(g);
       return;
     }
   }

   public void paintMe(Graphics g) {

     g.setColor(Color.black);           // prepares screen wash
     g.fillRect(0,0,d.width,d.height);  // makes big, black rectangle washover

     // paint white lines
     g.setColor(Color.white);
     g.drawLine(5, 20, 595, 20);
     g.drawLine(5, 390, 595, 390);

     // paint paddles
     g.setColor(Color.red);
     g.fillRect(40, yPos1 - 20, 5, 40);
     g.setColor(Color.blue);
     g.fillRect(560, yPos2 - 20, 5, 40);  

     // paint ball
     g.setColor(Color.yellow);
     g.fillOval(Pos[0], Pos[1], 10, 10);

     super.paint(g);     // here not necessary
   }

   public void update(Graphics g) {
     paint(g);
   }

   void UpdateScore() {
     ScoreLabel.setText(String.valueOf(Score[0]) + " : " + String.valueOf(Score[1]));
   }

/* ********************************************************************
   * gameplay
   ******************************************************************** */

   void UpdatePos() {
     if (Pos[1] + Vel[1] < 33) Vel[1] *= -1;    // collide with upper line
     if (Pos[1] + Vel[1] > 377) Vel[1] *= -1;   // collide with lower line
     // left paddle
     if ((Pos[0] > 40) && (Pos[0] < 55) && (Vel[0] < 0) && (Pos[1] > yPos1 - 25) && (Pos[1] < yPos1 + 25)) Vel[0] *= -1;
     // right paddle
     if ((Pos[0] > 545) && (Pos[0] < 560) && (Vel[0] > 0) && (Pos[1] > yPos2 - 25) && (Pos[1] < yPos2 + 25)) Vel[0] *= -1;
     // update position
     Pos[0] += Vel[0];
     Pos[1] += Vel[1];
   }

   void lookForGoal() {
     // left goal
     if (Pos[0] < 15) {
       Score[1] += 1;
       UpdateScore();
       Vel[0] *= -1;
       Pos[0] = 15;
       try { Thread.sleep(500); } catch(InterruptedException _ex) { _ex.printStackTrace();}
     }
     // right goal
     if (Pos[0] > 585) {
       Score[0] += 1;
       UpdateScore();
       Vel[0] *= -1;
       Pos[0] = 585;
       try { Thread.sleep(500); } catch(InterruptedException _ex) { _ex.printStackTrace();}
     }
     if (Score[0] == 15) BeginMsgLabel.setText(Str3);
     if (Score[1] == 15) BeginMsgLabel.setText(Str4);
     if ((Score[0] == 15) || (Score[1] == 15)) {    // the game ended
       BeginMsgLabel.setVisible(true);
       try { Thread.sleep(3000); } catch(InterruptedException _ex) { _ex.printStackTrace();}
       Score[0] = Score[1] = 0;
       BeginMsgLabel.setText(Str2);
       endPos();
     }
   }

   void startPos() {
     Start = Yes;
     Score[0] = Score[1] = 0;
     UpdateScore();
     int yPos1 = 200, yPos2 = 200;    // starting positions of the paddles
     Pos[0] = 585; Pos[1] = 200;
     Vel[0] = -4;  Vel[1] = 2;
     BeginMsgLabel.setVisible(false);
   }

   void endPos() {
     Start = No;
     UpdateScore();
     int yPos1 = 200, yPos2 = 200;    // starting positions of the paddles
     Pos[0] = 585; Pos[1] = 200;
     Vel[0] = -2;  Vel[1] = 1;
     BeginMsgLabel.setVisible(true);
   }

/* ********************************************************************
   * threadslike things
   ******************************************************************** */

   public void start() {
     if (Ball == null) {
       Ball = new Thread(this);
       Ball.start();
     }
   }

   public void stop() {
     if( Ball != null ) { endPos(); Ball.stop(); Ball = null; }
   }

   public void run() {
     while (Ball != null) { 
       if (Start == Yes) {
         UpdatePos();
         lookForGoal();
         repaint();
         try { Thread.sleep(speed); }
         catch(InterruptedException _ex) { _ex.printStackTrace();}
      }
     }
   }

   public void Toggle() {
     if (Ball != null) {
       if (suspend == true) { Ball.resume(); }
       else { Ball.suspend(); }
       suspend = !suspend;
     }
   }

   public void Pause() {
     if (Ball != null)
       if (suspend == false) {
         Ball.suspend();
         suspend = true;
       }
   }

   public void Continue() {
     if (Ball != null)
       if (suspend == true) {
         Ball.resume();
         suspend = false;
       }
   }

/* ********************************************************************
   * key pressing handling
   ******************************************************************** */

   public void keyTyped(KeyEvent e) { }

   public void keyPressed(KeyEvent e) {
     if ((Start == No) && (e.getKeyCode() == KeyEvent.VK_SPACE)) {
       startPos();
       return;
     }
     if ((Start == Yes) && (e.getKeyCode() == KeyEvent.VK_SPACE)) {
       Toggle();
       return;
     }
     if ((e.getKeyCode() == KeyEvent.VK_DOWN) && (yPos2 < 370) && (Vel[0] > 0)) {
       yPos2 += 10;
       return;
     }
     if ((e.getKeyCode() == KeyEvent.VK_UP) && (yPos2 > 40) && (Vel[0] > 0)) {
       yPos2 -= 10;
       return;
     }
     if ((e.getKeyCode() == KeyEvent.VK_C) && (yPos1 < 370) && (Vel[0] < 0)) {
       yPos1 += 10;
       return;
     }
     if ((e.getKeyCode() == KeyEvent.VK_D) && (yPos1 > 40) && (Vel[0] < 0)) {
       yPos1 -= 10;
       return;
     }
   }

   public void keyReleased(KeyEvent e) { }

/* ********************************************************************
   * mouse motion and clicking handling
   ******************************************************************** */

   public void mouseReleased(MouseEvent e) {
     requestFocus();
   }

   public void mousePressed(MouseEvent e) { }
   public void mouseClicked(MouseEvent e) { }
   public void mouseEntered(MouseEvent e) { }
   public void mouseExited(MouseEvent e) { }

/* ********************************************************************
   * focus loosing and gaining handling
   ******************************************************************** */

   public void focusGained(FocusEvent evt) { Continue(); }
   

   public void focusLost(FocusEvent evt) { Pause(); }

/* ********************************************************************
   * just nearly the end of the applet
   ******************************************************************** */

   public String getAppletInfo() {
     return Str1;
   }   

}