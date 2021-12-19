/**
 * ---- general information ----
 *
 * Bricks.java: A Bricks applet.
 * 
 * @version 1.0, 05 Apr 2002
 * @author  Jan Keller
 * @mail    post@jan-keller.de
 * @url     www.jan-keller.de
 * @place   Leipzig, Germany
 *
 * ---- Description ----
 *
 * JAVA programm for playing Bricks on the internet.
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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.EventObject;
import java.util.Vector;
import java.util.Random;

// --------------------------------------------------------------------
// CLASS Ball - the Ball
// --------------------------------------------------------------------

class Ball {

/* ********************************************************************
   * variables
   ******************************************************************** */

   static int radius = 8;           // sorry, this is the diameter

   int phasespace[] = new int[4];   // the position of the ball in the
                                    // phase space with 2n components n = 2

   int norm = 5;                    // velocity



/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Ball(int x, int y, int vx, int vy, int speed) {
     phasespace[0] = x;
     phasespace[1] = y;
     phasespace[2] = vx;
     phasespace[3] = vy;
     norm = speed;
     normalizeSpeed();
   }

/* ********************************************************************
   * methods
   ******************************************************************** */

   void display(Graphics g) {
     g.setColor(Color.yellow);
     g.fillOval(phasespace[0] - radius / 2, phasespace[1] - radius / 2, radius, radius);

   }

   void normalizeSpeed() {
     double x = Math.sqrt(phasespace[2] * phasespace[2] + phasespace[3] * phasespace[3]);
     int t;
     // here are some strange (physically) things, since i do not want v(y) = 0
     if (phasespace[3] <= 0) t = -1; else t = 0; // that's the signum function
     phasespace[2] *= (double) norm / x;
     phasespace[3] *= (double) norm / x;
     if (phasespace[3] == 0) phasespace[3] = t;
   }

   int[] getPS() { return phasespace; }

   void setPS(int x[]) {
     phasespace = x;
     normalizeSpeed();
     phasespace[0] += phasespace[2] / 2;    // deltaTime = 1 --> x(t+1)=x(t)+v(t+1)*1
     phasespace[1] += phasespace[3] / 2;    // !! change deltaTime = 1/2
   }

   int getRadius() { return radius / 2; }

   int getL() { return norm; }  // return length = speed = sqrt(v(x)^2+v(y)^2)
}

// --------------------------------------------------------------------
// CLASS Tile - a single Tile that can be shot
// --------------------------------------------------------------------

class Tile {

/* ********************************************************************
   * variables
   ******************************************************************** */

   int x, y;        // left upper position of the tile
   int lx, ly;      // width and height of the tile
   int aw, ah;      // arcwidth and archeight see awt.Graphics.fillRoundRect
   Color clr;       // color of the tile
   int value;       // the score for killing this particular tile

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Tile(int x, int y, int lx, int ly, Color clr, int value) {
     this.x = x;
     this.y = y;
     this.lx = lx;
     this.ly = ly;
     this.clr = clr;
     this.value = value;
     aw = lx / 2;
     ah = ly / 2;
   }

/* ********************************************************************
   * methods
   ******************************************************************** */

   void display(Graphics g) {
     g.setColor(clr);
     g.fillRoundRect(x, y, lx, ly, aw, ah);
   }

   int[] getPos() {
      int t[] = new int[4];
      t[0] = x;
      t[1] = y;
      t[2] = lx;
      t[3] = ly;
      return t;
   }

   int getValue() { return value; }

}

// --------------------------------------------------------------------
// CLASS Field - the paddle, the stones and the outer wand
// --------------------------------------------------------------------

class Field {

/* ********************************************************************
   * variables
   ******************************************************************** */

   static int paddleY = 360;
   static int paddleL = 70;
   int paddleX = 300;
   double paddleGrid = 0;       // that's keeps the movement of the paddle
   // in declining order and effects the reflexion of the ball with the paddle

   boolean goal = false;
   boolean finished = false;
   int scores = 0;              // holds the scores of the killed tiles

   Vector Tiles;

   Random ran = new Random();

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Field(int level) {
     // creates tiles
     Tiles = new Vector();
     if (level == 0)
       for (int j = 0; j < 4; j++)
       for (int i = 0; i < 3; i++) {
         Tiles.addElement(new Tile(15 + i * 90, 50 + j * 35, 70, 30, new Color(16 + j * 16, 16 + j * 16, 64 + j * 32), (4 - j) * 10));
         Tiles.addElement(new Tile(315 + i * 90, 50 + j * 35, 70, 30, new Color(16 + j * 16, 16 + j * 16, 64 + j * 32), (4 - j) * 10));
       }
     if (level == 1)
       for (int j = 0; j < 4; j++)
       for (int i = 0; i < 4; i++) {
         Tiles.addElement(new Tile(15 + i * 70, 50 + j * 35, 60, 30, new Color(16 + j * 16, 64 + j * 32, 16 + j * 16 ), (4 - j) * 20));
         Tiles.addElement(new Tile(315 + i * 70, 50 + j * 35, 60, 30, new Color(16 + j * 16, 64 + j * 32, 16 + j * 16), (4 - j) * 20));
       }
     if (level == 2)
       for (int j = 0; j < 4; j++)
       for (int i = 0; i < 5; i++) {
         Tiles.addElement(new Tile(15 + i * 55, 50 + j * 35, 50, 30, new Color(64 + j * 32, 16 + j * 16, 16 + j * 16), (4 - j) * 30));
         Tiles.addElement(new Tile(315 + i * 55, 50 + j * 35, 50, 30, new Color(64 + j * 32, 16 + j * 16, 16 + j * 16), (4 - j) * 30));
       }
     if (level == 3)
       for (int j = 0; j < 4; j++)
       for (int i = 0; i < 6; i++) {
         Tiles.addElement(new Tile(15 + i * 45, 50 + j * 25, 40, 20, new Color(64 + j * 32, 16 + j * 16, 64 + j * 32), (4 - j) * 40));
         Tiles.addElement(new Tile(315 + i * 45, 50 + j * 25, 40, 20, new Color(64 + j * 32, 16 + j * 16, 64 + j * 32), (4 - j) * 40));
       }
     if (level == 4)
       for (int j = 0; j < 4; j++)
       for (int i = 0; i < 7; i++) {
         Tiles.addElement(new Tile(15 + i * 40, 50 + j * 25, 35, 20, new Color((int) (ran.nextDouble()*255), (int) (ran.nextDouble()*255), (int) (ran.nextDouble()*255)), (4 - j) * 50));
         Tiles.addElement(new Tile(315 + i * 40, 50 + j * 25, 35, 20, new Color((int) (ran.nextDouble()*255), (int) (ran.nextDouble()*255), (int) (ran.nextDouble()*255)), (4 - j) * 50));
       }
   }

/* ********************************************************************
   * methods
   ******************************************************************** */

   void display(Graphics g) {
     // paint paddle
     g.setColor(Color.red);
     g.fillRect(paddleX - paddleL / 2, paddleY - 4, paddleL, 8);
     // paint tiles
     for (int i = 0; i < Tiles.size(); i++) {
       Tile Temp = (Tile) Tiles.elementAt(i);
       Temp.display(g);
     }

   }

   void setPaddle(int x) {
     int old = paddleX;
     if (x < Bricks.XS + paddleL / 2) paddleX = Bricks.XS + paddleL / 2;
       else if (x > Bricks.XE - paddleL / 2) paddleX = Bricks.XE - paddleL / 2;
         else paddleX = x;
     paddleGrid += paddleX - old;
   }

   void moveBall(Ball ball) {
     // pre methods
     declineGrid();

     int ps[] = new int[4]; // 2n dimensional phasespace (position + velocity of the ball)
     ps = ball.getPS();
     int nps[] = new int[4];    // updated values
     nps[0] = ps[0] + ps[2] / 2;    // supposed delta t = 1 then new x = old x + old v
     nps[1] = ps[1] + ps[3] / 2;    // these new positions are preliminary
     nps[2] = ps[2];            // new velocity still not computed
     nps[3] = ps[3];
     /* --------------------------------------------------------
        - Well, you might want to know, why all the following
        - things work. Actually, a bumping between more than
        - two bodies can be seen as several bumpings of two of
        - the bodies. That's why we can compute all interactions
        - successively and get the outcome, as everything would
        - be at the same time.
        -------------------------------------------------------- */
     nps = checkborders(nps, ball.getRadius());                 // bumps with the border
     nps = checkpaddle(nps, ball.getRadius(), ball.getL());     // bumps with the paddle
     nps = checktiles(nps, ball.getRadius(), ball.getL());      // bumps with the tiles

     nps[0] = ps[0]; nps[1] = ps[1];    // restore old positions     
     ball.setPS(nps);                   // update and compute new positions

     if (ps[1] >= paddleY - ball.getRadius() + ball.getL()) goal = true;
     // oh sorry you didn't catched the ball
     if (Tiles.isEmpty() == true) finished = true;  // no more tiles left
   }

   int[] checktiles(int x[], int r, int n) {
     for (int i = 0; i < Tiles.size(); i++) {
       Tile Temp = (Tile) Tiles.elementAt(i);
       boolean remove = false;      // if this tile get's killed i would prefer to know that
       int y[] = Temp.getPos();     // coordinates of left upper corner and width, height
       // upper side
       if ((x[0] >= y[0] - r) && (x[0] <= y[0] + y[2] + r) && (x[1] >= y[1] - r) && (x[1] <= y[1] + r)) {
         x[3] *= -1;    // reflexion in vertical direction
         x[2] += (double) 2 * (x[0] - y[0] - y[2] / 2) / y[2] * n;  // reflection to the side
         remove = true; // that's all folks -> aaaaaaaaaaaaaah :-) one tile died
       }
       // lower side
       if ((x[0] >= y[0] - r) && (x[0] <= y[0] + y[2] + r) && (x[1] >= y[1] + y[3] - r) && (x[1] <= y[1] + y[3] + r)) {
         x[3] *= -1;
         x[2] += (double) 2 * (x[0] - y[0] - y[2] / 2) / y[2] * n;
         remove = true;
       }
       // left side
       if ((x[0] >= y[0] - r) && (x[0] <= y[0] + r) && (x[1] >= y[1] - r) && (x[1] <= y[1] + y[3] + r)) {
         x[2] *= -1;
         x[1] += (double) (x[1] - y[1] - y[3] / 2) / y[3] * n;
         remove = true;
       }
       // right side
       if ((x[0] >= y[0] + y[2] - r) && (x[0] <= y[0] + y[2] + r) && (x[1] >= y[1] - r) && (x[1] <= y[1] + y[3] + r)) {
         x[2] *= -1;
         x[1] += (double) (x[1] - y[1] - y[3] / 2) / y[3] * n;
         remove = true;
       }
       if (remove == true) {
         scores += Temp.getValue();
         Tiles.removeElementAt(i);
         i--;   // because removing induces a shift in the indices - see util.Vector.removeElementAt()
       }
     }
     return x;
   }

   int[] checkborders(int x[], int  r) {
     // left & right border
     if (x[0] <= Bricks.XS + r) x[2] *= -1; // according to the laws of reflexion
     if (x[0] >= Bricks.XE - r) x[2] *= -1;
     // upper & lower border
     if (x[1] <= Bricks.YS + r) x[3] *= -1;
     if (x[1] >= Bricks.YE - r) x[3] *= -1;
     return x;
   }

   int[] checkpaddle(int x[], int r, int n) {
     if ((x[1] >= paddleY - 4 - r) && (x[1] <= paddleY - r + n))
       if ((x[0] >= paddleX - paddleL / 2 - r) && (x[0] <= paddleX + paddleL / 2 + r)) {
         int d = x[0] - paddleX;    // where on the paddle
         if (x[3] > 0) x[3] *= -1;
         x[3] -= 4; // pushes up the ball
         x[2] += (double) 3 * d / paddleL * n;
         x[2] += paddleGrid;
       }
     return x;
   }

   boolean isGoal() { return goal; }
   boolean isFinished() { return finished; }

   void resetGoal() { goal = false; }

   void declineGrid() { paddleGrid /= 1.2; }

   int getScores() { int x = scores; scores = 0; return x; }
}

// --------------------------------------------------------------------
// CLASS Bricks - the applet class, implements the game machine
// --------------------------------------------------------------------

public class Bricks extends Applet implements MouseListener, MouseMotionListener, KeyListener, Runnable {

/* ********************************************************************
   * variables
   ******************************************************************** */

   public static int XS = 5;        // borders of the field
   public static int YS = 5;
   public static int XE = 595;
   public static int YE = 375;

   static int LIVES = 5;            // guess what's that
   static int speed = 20; 		    // update every 20 ms

   boolean start = false;
   int score = 0;
   int level = 0;
   int lives = LIVES;

   Ball ball = null;
   Field board = null;

   Thread me;                   	// the thread for runnable
   boolean suspend = false;
   Image	 im;                	// just for double-buffering the images - reduces flickering
   Graphics  offscreen;
   Dimension d;

   String Str1 = "Bricks 1.0 by Jan Keller (04th Apr 2002)";
   String Str2 = "Press space to start";
   String Str3 = "Game Over - Press space to restart";
   String Str4 = "Lost a live";
   String Str5 = "Congratulation, you played all levels";

   java.awt.Label ScoreLabel;       // shows the score
   java.awt.Label LevelLabel;       // shows the level
   java.awt.Label LivesLabel;       // shows the lives
   java.awt.Label BeginMsgLabel;    // shows the heginning msg

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public void init() {
     // resize, set background color
     setLayout(null);
     setSize(600, 400);
     setBackground(Color.black);

     ball = new Ball(300, 15, 0, 1, 10);
     board = new Field(level);
     speed = getGameSpeed();

     try {
       d = getSize();
       im = createImage(d.width, d.height);
       offscreen = im.getGraphics();
     }
     catch(Exception _ex) { offscreen = null; _ex.printStackTrace(); }

     ScoreLabel = new java.awt.Label("Points: 0", Label.LEFT);
     add(ScoreLabel);
     ScoreLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     ScoreLabel.setBounds(5,382,100,16);
     ScoreLabel.setForeground(Color.white);

     LevelLabel = new java.awt.Label("Level: " + String.valueOf(level), Label.LEFT);
     add(LevelLabel);
     LevelLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     LevelLabel.setBounds(110,382,60,16);
     LevelLabel.setForeground(Color.white);

     LivesLabel = new java.awt.Label("Lives: " + String.valueOf(lives), Label.LEFT);
     add(LivesLabel);
     LivesLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     LivesLabel.setBounds(175,382,60,16);
     LivesLabel.setForeground(Color.white);

     BeginMsgLabel = new java.awt.Label(Str2, Label.CENTER);
     add(BeginMsgLabel);
     BeginMsgLabel.setFont(new Font("Dialog", Font.BOLD, 14));
     BeginMsgLabel.setBounds(100,200,400,20);
     BeginMsgLabel.setForeground(Color.white);

     addMouseListener(this);
     addMouseMotionListener(this);
     addKeyListener(this);
     requestFocus();
   }

/* ********************************************************************
   * gui/ paint everything
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
     g.setColor(Color.white);
     g.drawRect(0,0,d.width - 1, d.height - 1);
     g.drawRect(0,d.height - 21,d.width - 1, d.height - 1);
     g.drawRect(XS, YS, XE - XS, YE - YS);

     // paint field and ball
     if (board != null) board.display(g);
     if (ball != null) ball.display(g);

     super.paint(g);
   }

   public void update(Graphics g) {
     paint(g);
   }

   void updateLabels() {
     ScoreLabel.setText("Points: " + String.valueOf(score));
     LevelLabel.setText("Level: " + String.valueOf(level));
     LivesLabel.setText("Lives: " + String.valueOf(lives));
   }

/* ********************************************************************
   * game machine methods
   ******************************************************************** */

   public int getGameSpeed() {
     switch (level) {
       case 0: return 15;
       case 1: return 12;
       case 2: return 10;
       case 3: return 8;
       case 4: return 5;
       default: return 5;
     }
   }

/* ********************************************************************
   * runnable methods
   ******************************************************************** */

   public void start() {
     if (me == null) {
       me = new Thread(this);
       me.start();
     }
   }

   public void stop() {
     if (me != null) { me.stop(); me = null; }
   }

   public void run() {
     while (me != null) { 
       if (start == true) {
         board.moveBall(ball);
         score += board.getScores();
         if (board.isFinished() == true) {
           if (level < 4) {
             level++;
             board = new Field(level);  // garbage collection deletes the old field
             ball = new Ball(300, 15, 0, 1, 10);
             BeginMsgLabel.setText("Level " + String.valueOf(level) + " completed");
             BeginMsgLabel.setVisible(true);
             try { Thread.sleep(1000); } catch(InterruptedException _ex) { _ex.printStackTrace();}
             BeginMsgLabel.setVisible(false);
           } else {
             start = false;
             BeginMsgLabel.setText(Str5);
             BeginMsgLabel.setVisible(true);
             try { Thread.sleep(3000); } catch(InterruptedException _ex) { _ex.printStackTrace();}
             BeginMsgLabel.setText(Str3);
           }
         }
         if (board.isGoal() == true) {
             lives--;
           if (lives > 0) {
             BeginMsgLabel.setText(Str4);
             BeginMsgLabel.setVisible(true);
             try { Thread.sleep(1000); } catch(InterruptedException _ex) { _ex.printStackTrace();}
             BeginMsgLabel.setVisible(false);
             ball = new Ball(300, 15, 0, 1, 10);
             board.resetGoal();
           } else {
             start = false;
             BeginMsgLabel.setText(Str3);
             BeginMsgLabel.setVisible(true);
           }
         }
       }
       updateLabels();
       repaint();
       try { Thread.sleep(speed); } catch(InterruptedException _ex) { _ex.printStackTrace();}
     }
   }

   void toggle() {
     if (me != null) {
       if (suspend == true) { me.resume(); }
       else { me.suspend(); }
       suspend = !suspend;
     }
   }

/* ********************************************************************
   * mouse/ key / focus handling (listening)
   ******************************************************************** */

   public void mouseReleased(MouseEvent e) { requestFocus(); }
   public void mousePressed(MouseEvent e) { }
   public void mouseClicked(MouseEvent e) { }
   public void mouseEntered(MouseEvent e) { }
   public void mouseExited(MouseEvent e) { }

   public void keyTyped(KeyEvent e) { }
   public void keyReleased(KeyEvent e) { }

   public void keyPressed(KeyEvent e) {
     int eKey = e.getKeyCode();
     if ((start == false) && (eKey == KeyEvent.VK_SPACE)) {
       BeginMsgLabel.setVisible(false);
       ball = new Ball(300, 15, 0, 1, 10);     
       score = level = 0; lives = LIVES;
       board = new Field(level);
       speed = getGameSpeed();
       updateLabels();
       start = true;
       return;
     }
     if ((start == true) && (eKey == KeyEvent.VK_P)) {
       toggle();
       return;
     }
   }

   public void mouseMoved(MouseEvent e) {
     int i = e.getX();
     if (board != null) board.setPaddle(i);
   }

   public void mouseDragged(MouseEvent e) { }

/* ********************************************************************
   * just nearly the end of the applet
   ******************************************************************** */

   public String getAppletInfo() {
     return Str1;
   }

}