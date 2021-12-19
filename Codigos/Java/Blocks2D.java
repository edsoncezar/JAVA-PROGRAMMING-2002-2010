/**
 * ---- general information ----
 *
 * Blocks2D.java: A 2D Blocks applet.
 * 
 * @version 1.0,
 * @author  Jan Keller
 * @mail    post@jan-keller.de
 * @url     www.jan-keller.de
 * @place   Leipzig, Germany
 *
 * ---- Description ----
 *
 * JAVA programm for playing 2D Blocks on the internet.
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
import java.util.EventObject;
import java.util.Vector;    

// --------------------------------------------------------------------
// CLASS AboutBox - Frame Window with short description of the rules
// --------------------------------------------------------------------

class AboutBox extends Frame implements ActionListener, WindowListener {

/* ********************************************************************
   * variables
   ******************************************************************** */

    java.awt.TextArea info;
    Button Ok;

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public AboutBox(String Str1, String Str2) {

     super(Str1);
     add("Center", info = new TextArea(Str2, 14, 80, TextArea.SCROLLBARS_NONE));
     info.setEditable(false);
     info.setBackground(Color.white);
     info.setFont(new Font("Helvetica", 0, 14));
     // info.setCursor
     Panel panel = new Panel();
     add("South", panel);
     panel.add(Ok = new Button("OK"));
     pack();
     Ok.addActionListener(this);
     addWindowListener(this);
   }

   public void actionPerformed(java.awt.event.ActionEvent event) {
     Object object = event.getSource();
     if (object == Ok) {
       setVisible(false);
       dispose();
     }
   }

    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) { dispose();}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}

// --------------------------------------------------------------------
// CLASS Element - one single colored square
// --------------------------------------------------------------------

class Element {

/* ********************************************************************
   * variables
   ******************************************************************** */

   int x, y;        // the position within the system
   int xP, yP;      // the position within the board
   Color clr;       // the Color

   public static int Size = 18;

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Element(int xPos, int yPos, Color c) {
     x = xPos;
     y = yPos;
     reset();
     clr = c;
   }

/* ********************************************************************
   * the methods
   ******************************************************************** */

   void reset() {
     xP = 3 + x;    // new shapes are in the middle - only valid if Playfield.SizeX == 10!
     yP = y;
   }

   Element copy() {
     Element t = new Element(x, y, clr);    // makes a real copy
     t.setXP(xP);
     t.setYP(yP);
     return t;
   }

   void display(Graphics g) {
     int xPos = Blocks2D.PlayfieldX + xP * Size;
     int yPos = Blocks2D.PlayfieldY + yP * Size;
     g.setColor(Color.lightGray);
     g.drawRect(xPos, yPos, Size - 1, Size - 1); 
     g.setColor(clr);
     g.fillRect(xPos + 1, yPos + 1, Size - 2, Size - 2);
     // -----------------------------------------------------------------------------------
     //  Attention!
     // For some reason JAVA draws a drawRect(x, y, a, b) as a rectangle with width = a + 1
     // and a fillRect(x, y, a, b) as a rectangle with width = a
     // -----------------------------------------------------------------------------------
   }

   void displayTile(Graphics g) {
     int xPos = Blocks2D.nextTileX + x * Size;
     int yPos = Blocks2D.nextTileY + y * Size;
     g.setColor(Color.lightGray);
     g.drawRect(xPos, yPos, Size - 1, Size - 1); 
     g.setColor(clr);
     g.fillRect(xPos + 1, yPos + 1, Size - 2, Size - 2);
     // -----------------------------------------------------------------------------------
     //  Attention!
     // For some reason JAVA draws a drawRect(x, y, a, b) as a rectangle with width = a + 1
     // and a fillRect(x, y, a, b) as a rectangle with width = a
     // -----------------------------------------------------------------------------------
   }

   int getXP() { return xP; }

   int getYP() { return yP; }

   void setXP(int x) { xP = x; }

   void setYP(int y) { yP = y; }

   int getX() { return x; }

   int getY() { return y; }

   // cRotX and cRotY computes the change in xP, yP, when x, y would be changed!
   int cRotX(int xn) { return xP - x + xn; }

   int cRotY(int yn) { return yP - y + yn; }

   // setX and setY sets new x, y and computes new xP, yP
   void setX(int xn) {
     xP = cRotX(xn);
     x = xn;
   }

   void setY(int yn) {
     yP = cRotY(yn);
     y = yn;
   }
}

// --------------------------------------------------------------------
// CLASS Shape - one out of 7 different shapes consisting of 4 elements
// --------------------------------------------------------------------

class Shape {

/* ********************************************************************
   * variables
   ******************************************************************** */

   public Vector Elements;     // the single squares

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Shape() { Elements = new Vector(); }

   public Shape(int a, int b, int c, int d, Color clr) {
     Elements = new Vector();
     AddElements(0, a, clr);
     AddElements(1, b, clr);
     AddElements(2, c, clr);
     AddElements(3, d, clr);
   }

/* ********************************************************************
   * the methods
   ******************************************************************** */

   Shape copy() {   // returns a copy of this shape
     Shape t = new Shape();
     for (int i = 0; i < Elements.size(); i++) {
       Element Temp = (Element) Elements.elementAt(i);
       t.Elements.addElement(Temp.copy());
     }     
     return t;
   }

   void AddElements (int row, int a, Color clr) {
        if ((a & 0xf000) > 0) Elements.addElement(new Element(0, row, clr));
        if ((a & 0x0f00) > 0) Elements.addElement(new Element(1, row, clr));
        if ((a & 0x00f0) > 0) Elements.addElement(new Element(2, row, clr));
        if ((a & 0x000f) > 0) Elements.addElement(new Element(3, row, clr));
   }

   void display(Graphics g) {
     for (int i = 0; i < Elements.size(); i++) {
       Element Temp = (Element) Elements.elementAt(i);
       Temp.display(g);
     }
   }

   void displayTiles(Graphics g) {
     for (int i = 0; i < Elements.size(); i++) {
       Element Temp = (Element) Elements.elementAt(i);
       Temp.displayTile(g);
     }
   }

   boolean move(int x, int y, Playfield b) {
     Element Temp;
     boolean ea = true;     // ea = everything alright
     for (int i = 0; i < Elements.size(); i++) {
       Temp = (Element) Elements.elementAt(i);
       int xp = Temp.getXP() + x;
       int yp = Temp.getYP() + y;
       if (b.test(xp, yp) == false) ea = false;
     }
     if (ea == true) {  // everything is alright
       if ((x != 0) || (y != 0)) {  // because we use .move(0,0) to see if a move is possible
         for (int i = 0; i < Elements.size(); i++) {
           Temp = (Element) Elements.elementAt(i);
           int xp = Temp.getXP() + x; Temp.setXP(xp);
           int yp = Temp.getYP() + y; Temp.setYP(yp);
         }
       }
       return true;
     }
     return false;
   }

   void rotate(Playfield b) {
   /*  see 00x0 will be 0000 and x(new) = 3 - y(old), y(new) = x(old)
           00x0         0000
           00x0         xxxx    means rotating clockwise
           00x0         0000 */
     Element Temp;
     boolean ea = true;
     for (int i = 0; i < Elements.size(); i++) {
       Temp = (Element) Elements.elementAt(i);
       int x = 3 - Temp.getY();
       int y = Temp.getX();
       int xp = Temp.cRotX(x);
       int yp = Temp.cRotY(y);
       if (b.test(xp, yp) == false) ea = false;
     }
     if (ea == true) {  // rotating possible
       for (int i = 0; i < Elements.size(); i++) {
         Temp = (Element) Elements.elementAt(i);
         int x = 3 - Temp.getY();
         int y = Temp.getX();
         Temp.setX(x);
         Temp.setY(y);
       }
     }
   }

   void registerShape(Playfield b) {
     for (int i = 0; i < Elements.size(); i++) {
       Element Temp = (Element) Elements.elementAt(i);
       b.registerElement(Temp);
     }
   }

   void reset() {
     for (int i = 0; i < Elements.size(); i++) {
       Element Temp = (Element) Elements.elementAt(i);
       Temp.reset();
     }
   }
}

// --------------------------------------------------------------------
// CLASS Playfield - the playfield, has elements
// --------------------------------------------------------------------

class Playfield {

/* ********************************************************************
   * variables
   ******************************************************************** */

   public static int SizeX = 10;    // width and length of the board
   public static int SizeY = 20;

   int lx = SizeX * Element.Size;   // just for convenience
   int ly = SizeY * Element.Size;

   Element Board[][] = new Element[SizeX][SizeY];  // contains the single squares


/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Playfield() {
     for (int i = 0; i < SizeX; i++)
     for (int j = 0; j < SizeY; j++)
       Board[i][j] = null;
   }

/* ********************************************************************
   * the methods
   ******************************************************************** */

   void display(Graphics g) {
     g.setColor(Color.black);
     g.fillRect(Blocks2D.PlayfieldX, Blocks2D.PlayfieldY, lx, ly);
     g.setColor(Color.lightGray);
     int x = Blocks2D.PlayfieldX;
     int y = Blocks2D.PlayfieldY;
     g.drawLine(x - 1, y, x - 1, y + 1 + ly);
     g.drawLine(x - 1 + lx, y, x - 1 + lx, y + 1 + ly);
     g.drawLine(x - 1, y + 1 + ly, x - 1 + lx, y + 1 + ly);

     for (int i = 0; i < SizeX; i++)
     for (int j = 0; j < SizeY; j++)
       if (Board[i][j] != null) Board[i][j].display(g);
   }

   boolean test(int x, int y) {
     if ((x < 0) || (x >= SizeX) || (y < 0) || (y >= SizeY)) return false;
     if (Board[x][y] == null) return true;
     return false;
   }

   void clear() {
     for (int i = 0; i < SizeX; i++)
     for (int j = 0; j < SizeY; j++)
       Board[i][j] = null;
   }

   void registerElement(Element t) {
     int x = t.getXP();
     int y = t.getYP();
     if (test(x, y) == false) System.out.println("Playfield.registerElement() failed!");
     Board[x][y] = t.copy();
   }

   int checkLines() {
     int nl = 0;
     for (int y = SizeY - 1; y >= 0; y--) {
       boolean ea = true;
       for (int x = 0; x < SizeX; x++) if (Board[x][y] == null) ea = false;
       if (ea == true) {
         for (int y1 = y - 1; y1 >= 0; y1--)
         for (int x1 = 0; x1 < SizeX; x1++) {
           Element t = Board[x1][y1];
           Board[x1][y1 + 1] = t;
           if (t != null) {
             int h = t.getYP();
             t.setYP(h + 1);
           }
         }
         for (int x1 = 0; x1 < SizeX; x1++) Board[x1][0] = null;
         nl++;
         y++;   // because of the deletion of the line everything above moved one line down
       }
     }
     return nl;
   }

}

// --------------------------------------------------------------------
// CLASS Blocks2D - the applet class, implements the game machine
// --------------------------------------------------------------------

public class Blocks2D extends Applet implements
  ActionListener, MouseListener, KeyListener, Runnable {

/* ********************************************************************
   * variables
   ******************************************************************** */

   public static int nextTileX = 205;
   public static int nextTileY = 135;
   public static int PlayfieldX = 10;
   public static int PlayfieldY = 20;

   int level = 0;       // level
   int lines = 0;       // lines
   int score = 0;       // score
   int speed = 100;       // speed in ms between moves of the shapes

   boolean start = false;

   Vector ShapeSet;             // contains all 7 possible shapes
   Shape FallingShape = null;
   Shape NextShape = null;
   Playfield Field = new Playfield();

   Thread me;                   // the thread for runnable
   boolean suspend = false;
   Image	 im;                // just for double-buffering the images - reduces flickering
   Graphics  offscreen;
   Dimension d;

   String Str1 = "Blocks2D 1.0 by Jan Keller (02th Apr 2002)";
   String Str2 = "Blocks2D:\nTwo dimensional Blocks is a Tetris clone. The falling shape can moved to the left with the 'arrow left' key and moved to the right with the 'arrow right' key. It rotates clockwise with the 'arrow up' key and it accelerates with the spacebar. That gives additional points. For each 10 lines you have completed your level gets one higher, that means faster falling of shapes and more points per line. With the key 'p' you can pause/continue the game.\nHave fun!";
   String Str3 = "Level: ";
   String Str4 = "Lines: ";
   String Str5 = "Score: ";
   String Str6 = "Next Tile";
   String Str7 = "Press 'New Game' for start";
   String Str8 = "Game ended - Points: ";

   java.awt.Button NewGame;         // button for a new game
   java.awt.Button Pause;           // button for pause/resume
   java.awt.Button Info;            // Information about the Game
   java.awt.Label  LevelLabel;      // Label for the levelinformation
   java.awt.Label  LinesLabel;      // Label for the lines
   java.awt.Label  ScoreLabel;      // Label for the score
   java.awt.Label  NextLabel;       // Label for the picture of the next
   java.awt.Label  MsgLabel;        // Label for a big Message

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public void init() {

     // resize, set background color
     setLayout(null);
     setSize(400,400);
     setBackground(new Color(32,92,32));

     try {
       d = getSize();
       im = createImage(d.width, d.height);
       offscreen = im.getGraphics();
     }
     catch(Exception _ex) { offscreen = null; _ex.printStackTrace(); }


     NewGame = new java.awt.Button();
     add(NewGame);
     NewGame.setLabel("New Game");
     NewGame.setBounds(205,5,80,20);
     NewGame.setBackground(new Color(12632256));
     NewGame.setCursor(new Cursor(Cursor.HAND_CURSOR));

     Pause = new java.awt.Button();
     add(Pause);
     Pause.setLabel("Pause - Resume");
     Pause.setBounds(290,5,105,20);
     Pause.setBackground(new Color(12632256));
     Pause.setCursor(new Cursor(Cursor.HAND_CURSOR));

     Info = new java.awt.Button();
     add(Info);
     Info.setLabel("Info");
     Info.setBounds(205,30,40,20);
     Info.setBackground(new Color(12632256));
     Info.setCursor(new Cursor(Cursor.HAND_CURSOR));

     LevelLabel = new java.awt.Label(Str3 + "0", Label.LEFT);
     add(LevelLabel);
     LevelLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     LevelLabel.setBounds(205,55,100,16);
     LevelLabel.setBackground(new Color(200,200,200));

     LinesLabel = new java.awt.Label(Str4 + "0", Label.LEFT);
     add(LinesLabel);
     LinesLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     LinesLabel.setBounds(205,75,100,16);
     LinesLabel.setBackground(new Color(200,200,200));

     ScoreLabel = new java.awt.Label(Str5 + "0", Label.LEFT);
     add(ScoreLabel);
     ScoreLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     ScoreLabel.setBounds(205,95,100,16);
     ScoreLabel.setBackground(new Color(200,200,200));

     NextLabel = new java.awt.Label(Str6, Label.CENTER);
     add(NextLabel);
     NextLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     NextLabel.setBounds(205,115,100,16);
     NextLabel.setBackground(new Color(200,200,200));

     MsgLabel = new java.awt.Label(Str7, Label.CENTER);
     add(MsgLabel);
     MsgLabel.setFont(new Font("Dialog", Font.BOLD, 12));
     MsgLabel.setBounds(205,200,190,20);
     MsgLabel.setForeground(Color.white);


     NewGame.addActionListener(this);
     Pause.addActionListener(this);
     Info.addActionListener(this);

     addMouseListener(this);
     addKeyListener(this);
     requestFocus();

     // init all values
     initShapeSet();    // all 7 shapes that consist of 4 squares
   }

   void initShapeSet() {
     ShapeSet = new Vector();
     ShapeSet.addElement(new Shape( 0x0000 ,
                                    0x0FF0 ,
                                    0x0FF0 ,
                                    0x0000 , Color.blue));      // 2x2 block
     ShapeSet.addElement(new Shape( 0x0F00 ,
                                    0x0F00 ,
                                    0x0FF0 ,
                                    0x0000 , Color.yellow));    // normal "L"
     ShapeSet.addElement(new Shape( 0x00F0 ,
                                    0x00F0 ,
                                    0x0FF0 ,
                                    0x0000 , Color.pink));      // mirrored "L"
     ShapeSet.addElement(new Shape( 0x0000 ,
                                    0x0F00 ,
                                    0xFFF0 ,
                                    0x0000 , Color.green));     // 3 in a line with one add. in the middle
     ShapeSet.addElement(new Shape( 0x0F00 ,
                                    0x0F00 ,
                                    0x0F00 ,
                                    0x0F00 , Color.red));       // 4 in a row
     ShapeSet.addElement(new Shape( 0x0F00 ,
                                    0x0FF0 ,
                                    0x00F0 ,
                                    0x0000 , Color.magenta));   // "z" to the right
     ShapeSet.addElement(new Shape( 0x00F0 ,
                                    0x0FF0 ,
                                    0x0F00 ,
                                    0x0000 , Color.orange));    // "z" to the left
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
     g.setColor(new Color(32,92,32));   // prepares screen wash
     g.fillRect(0,0,d.width,d.height);  // makes big, green rectangle washover

     Field.display(g);                   // display Playfield

     if (FallingShape != null) FallingShape.display(g); // display falling shape

     if (NextShape != null) NextShape.displayTiles(g);  // display next Tile

     updateLabels();

     super.paint(g);
   }

   public void update(Graphics g) {
     paint(g);
   }

   void updateLabels() {
     LevelLabel.setText(Str3 + String.valueOf(level));
     LinesLabel.setText(Str4 + String.valueOf(lines));
     ScoreLabel.setText(Str5 + String.valueOf(score));
   }

/* ********************************************************************
   * game machine methods
   ******************************************************************** */

   public int GetGameSpeed() {
     switch (level) {
       case 0: return 700;
       case 1: return 600;
       case 2: return 500;
       case 3: return 400;
       case 4: return 350;
       case 5: return 300;
       case 6: return 250;
       case 7: return 200;
       case 8: return 150;
       case 9: return 100;
       default: return 100;
     }
   }
   
   public int GetRandomShapeNr() {
     int ShapeNr;
     do {
       ShapeNr = (int) (Math.random() * ShapeSet.size());
     } while (ShapeNr >= ShapeSet.size());
     return ShapeNr;
   }

   public void GetNextRandomShape() {
     if (FallingShape == null) {
       Shape t = (Shape) ShapeSet.elementAt(GetRandomShapeNr()); // this is all because rotating shall not
       FallingShape = t.copy();     // affect new shapes
     } else {
       FallingShape = NextShape;
     }
     Shape t = (Shape) ShapeSet.elementAt(GetRandomShapeNr());
     NextShape = t.copy();
     FallingShape.reset();
   }

   public void AddScore(int s) {
     score += s;
     repaint();
   }

   void AddLineScore(int nl) {
     AddScore(nl * 100 * (level + 1));
   }

   void checkLevel() {
     level = lines / 10;
     if (level > 9) level = 9;
     speed = GetGameSpeed();
     repaint();
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
         if (FallingShape.move(0, 1, Field) == false) {
           FallingShape.registerShape(Field);
           int nl = Field.checkLines(); // new lines
           if (nl > 0) {
             lines += nl;
             AddLineScore(nl);
             checkLevel();
           }
           GetNextRandomShape();
           if (FallingShape.move(0, 0, Field) == false) {   // Playfield already full
             start = false;
             FallingShape = NextShape = null;
             MsgLabel.setText(Str8 + String.valueOf(score));
             MsgLabel.setVisible(true);
           }
         }
       }
       repaint();
       try { Thread.sleep(speed); } catch(InterruptedException _ex) { _ex.printStackTrace();}
     }
   }

   void Toggle() {
     if (me != null) {
       if (suspend == true) { me.resume(); }
       else { me.suspend(); }
       suspend = !suspend;
     }
   }

/* ********************************************************************
   * handler for the buttons
   ******************************************************************** */

   public void actionPerformed(java.awt.event.ActionEvent event) {
     Object object = event.getSource();
     if (object == Info) {
       AboutBox aboutbox = new AboutBox(Str1, Str2);
       aboutbox.show();
     }
     if (object == NewGame) {
       speed = GetGameSpeed();
       start = true;
       level = lines = score = 0;
       Field.clear();
       GetNextRandomShape();
       MsgLabel.setVisible(false);
       requestFocus();
       repaint();
     }
     if (object == Pause) Toggle();
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

     if (eKey == KeyEvent.VK_P) {       // pressed "p" for pause/resume
       Toggle();
       return;
     }
     if ((eKey == KeyEvent.VK_SPACE) && (start == true)) {  // pressed arrow left
       boolean t = FallingShape.move(0, 1, Field);
       if (t == true) AddScore(2);
       repaint();
       return;
     }
     if ((eKey == KeyEvent.VK_LEFT) && (start == true)) {   // pressed arrow left
       boolean t = FallingShape.move(-1, 0, Field);
       repaint();
       return;
     }
     if ((eKey == KeyEvent.VK_RIGHT) && (start == true)) {  // pressed arrow right
       boolean t = FallingShape.move(1, 0, Field);
       repaint();
       return;
     }
     if ((eKey == KeyEvent.VK_UP) && (start == true)) {     // pressed arrow up
       FallingShape.rotate(Field);
       repaint();
       return;
     }
   }

/* ********************************************************************
   * just nearly the end of the applet
   ******************************************************************** */

   public String getAppletInfo() {
     return Str1;
   }   
}