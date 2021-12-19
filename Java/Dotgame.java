/**
 * ---- general information ----
 *
 * Dotgame.java: A Dotgame applet.
 * 
 * @version 1.0,
 * @author  Jan Keller
 * @mail    post@jan-keller.de
 * @url     www.jan-keller.de
 * @place   Leipzig, Germany
 *
 * ---- Description ----
 *
 * JAVA programm for playing Dotgame [Käsekästchen] on the internet.
 *
 * This program is postcard-ware.
 * If you like this program, send a postcard to:
 *
 *   Jan Keller
 *   Grassistraße 31/315
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
    java.awt.Button Ok;

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
// CLASS C - A class containing constants
// --------------------------------------------------------------------

class C {

   public static final int XS = 200;        // size of the playfield (quadratic - the same in y)
   public static final int CL = 38;         // size of a cell + vertex

   public static final int None = 0;        // states of the cells, vertices, ...
   public static final int Player1 = 1;
   public static final int Player2 = 2;
   public static final int Yes  = 3;

   public C() { }

}

// --------------------------------------------------------------------
// CLASS Vertex - A vertex
// --------------------------------------------------------------------

class Vertex {

/* ********************************************************************
   * variables
   ******************************************************************** */

   int k, l;    // contains the position of the vertex

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Vertex(int k, int l) {
     this.k = k;
     this.l = l;
   }

/* ********************************************************************
   * methods
   ******************************************************************** */

   void display(Graphics g, int z) {
     g.setColor(Color.black);
     int x = C.XS - z / 2 * C.CL + k * C.CL;
     int y = C.XS - z / 2 * C.CL + l * C.CL;;
     g.fillRect(x, y, 8, 8);    // size = 8
   }
}

// --------------------------------------------------------------------
// CLASS Connection - A connection between two vertices
// --------------------------------------------------------------------

class Connection {

/* ********************************************************************
   * variables
   ******************************************************************** */

   int status = C.None;

   int k, l, m, n;    // contains the position of the connections

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Connection(int i, int j, int i1, int j1) {
     this.k = i;
     this.l = j;
     this.m = i1;
     this.n = j1;
   }

/* ********************************************************************
   * methods
   ******************************************************************** */

   void display(Graphics g, int z) {
     if (status == C.None) g.setColor(new Color(32, 92, 32));
     if (status == C.Yes)     g.setColor(Color.black);
     if (status == C.Player1) g.setColor(Color.orange);
     if (status == C.Player2) g.setColor(Color.magenta);
     int x = C.XS - z / 2 * C.CL + k * C.CL;
     int y = C.XS - z / 2 * C.CL + l * C.CL;
     int x1 = (m - k) * C.CL;
     int y1 = (n - l) * C.CL;
     if (y1 == 0) g.fillRect(x + 9, y, x1 - 10, 8);    // don't lay over the vertices
     if (x1 == 0) g.fillRect(x, y + 9, 8, y1 - 10);
   }

   int getStatus() { return status; }

   void setStatus(int status) { this.status = status; }

   boolean isOver(int i, int j, int z) {
     int x = C.XS - z / 2 * C.CL + k * C.CL;
     int y = C.XS - z / 2 * C.CL + l * C.CL;
     int x1 = (m - k) * C.CL;
     int y1 = (n - l) * C.CL;
     if ((y1 == 0) && (i > x + 8) && (i < x + x1) && (j >= y) && (j <= y + 8)) return true;
     if ((x1 == 0) && (i >= x) && (i <= x + 8) && (j > y + 8) && (j < y + y1)) return true;
     return false;
   }

   boolean isYes(int x1, int x2, int x3, int x4) {
     return ((status == C.Yes) && (k == x1) && (l == x2) && (m == x3) && (n == x4));
   }
}

// --------------------------------------------------------------------
// CLASS Cell - A full connected cell
// --------------------------------------------------------------------

class Cell {

/* ********************************************************************
   * variables
   ******************************************************************** */

   int status = C.None;

   int k, l;    // contains the position of the cell

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Cell(int k, int l) {
     this.k = k;
     this.l = l;
   }

/* ********************************************************************
   * methods
   ******************************************************************** */

   void display(Graphics g, int z) {
     if (status == C.None) return;
     if (status == C.Player1) g.setColor(Color.green);
     if (status == C.Player2) g.setColor(Color.yellow);
     int x = C.XS - z / 2 * C.CL + k * C.CL;
     int y = C.XS - z / 2 * C.CL + l * C.CL;
     g.fillRect(x + 9, y + 9, 28, 28);  // size = 30 = C.CL - 8   | - 2
   }

   int getStatus() { return status; }

   void setStatus(int status) { this.status = status; }

   int getX() { return k; }

   int getY() { return l; }
}

// --------------------------------------------------------------------
// CLASS Field - the Playfield
// --------------------------------------------------------------------

class Field {

/* ********************************************************************
   * variables
   ******************************************************************** */

   int k;        // contains the size of the field (quadratic)

   boolean Ncell = false;

   Vector vertices;
   Vector connections;
   Vector cells;

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public Field(int k) {
     this.k = k;
     
     vertices = new Vector();
     connections = new Vector();
     cells = new Vector();

     for (int i = 0; i < k; i++)
     for (int j = 0; j < k; j++) {
       vertices.addElement(new Vertex(i, j));
       if ((i < k - 1) && (j < k - 1)) cells.addElement(new Cell(i, j));
       if (i < k - 1) connections.addElement(new Connection(i, j, i + 1, j));
       if (j < k - 1) connections.addElement(new Connection(i, j, i, j + 1));
     }
   }

/* ********************************************************************
   * methods
   ******************************************************************** */

   void display(Graphics g) {
     g.setColor(new Color(32, 92, 32));
     g.fillRect(0, 0, 400, 400);
     // paint all vertices, connections and cells
     for (int i = 0; i < vertices.size(); i++) {
       Vertex h = (Vertex) vertices.elementAt(i);
       h.display(g, k);
     }
     for (int i = 0; i < connections.size(); i++) {
       Connection h = (Connection) connections.elementAt(i);
       h.display(g, k);
     }
     for (int i = 0; i < cells.size(); i++) {
       Cell h = (Cell) cells.elementAt(i);
       h.display(g, k);
     }
   }

   void notifyMove(int x, int y, int p, Graphics g) {  // mouseMove
     for (int i = 0; i < connections.size(); i++) {
       Connection h = (Connection) connections.elementAt(i);
       int j = h.getStatus();
       if (j != C.Yes) {
         if ((h.isOver(x, y, k) == true) && (j == C.None)) {
           h.setStatus(p);
           h.display(g, k);
         }
         if ((h.isOver(x, y, k) == false) && (j != C.None)) {
           h.setStatus(C.None);
           h.display(g, k);
         }
       }
     }
   }

   void notifyClick(int x, int y, int p, Graphics g) {  // mouseClick
     boolean change = false;
     for (int i = 0; i < connections.size(); i++) {
       Connection h = (Connection) connections.elementAt(i);
       if ((h.getStatus() != C.Yes) && (h.isOver(x, y, k) == true)) {
         h.setStatus(C.Yes);
         h.display(g, k);
         change = true;
       }
     }
     // check cells
     if (change == true)
     for (int i = 0; i < cells.size(); i++) {
       Cell h = (Cell) cells.elementAt(i);
       if ((checkCell(h) == true) && (h.getStatus() == C.None)) {
         h.setStatus(p);
         h.display(g, k);
         Ncell = true;
       }
     }
     if (change == false) Ncell = true;     // no change in whoisnext because no change...
   }

   boolean checkCell(Cell h) {
     int x = h.getX();
     int y = h.getY();
     if (checkConnection(x, y, x + 1, y) == false) return false;
     if (checkConnection(x, y, x, y + 1) == false) return false;
     if (checkConnection(x + 1, y, x + 1, y + 1) == false) return false;
     if (checkConnection(x, y + 1, x + 1, y + 1) == false) return false;
     return true;
   }

   boolean checkConnection(int x1, int x2, int x3, int x4) {
     for (int i = 0; i < connections.size(); i++) {
       Connection h = (Connection) connections.elementAt(i);
       if (h.isYes(x1, x2, x3, x4) == true) return true;
     }
     return false;
   }

   boolean gameOver() {
     for (int i = 0; i < cells.size(); i++) {
       Cell h = (Cell) cells.elementAt(i);
       if (h.getStatus() == C.None) return false;
     }
     return true;
   }

   boolean isNcell() {
     boolean h = Ncell;
     Ncell = false;
     return h;
   }

   int count(int p) {
     int c = 0;
     for (int i = 0; i < cells.size(); i++) {
       Cell h = (Cell) cells.elementAt(i);
       if (h.getStatus() == p) c++;
     }
     return c;
   }

   void computerMove() {

   }
}

// --------------------------------------------------------------------
// CLASS Dotgame - the applet class, implements the game machine
// --------------------------------------------------------------------

public class Dotgame extends Applet implements ActionListener, MouseListener, MouseMotionListener {

/* ********************************************************************
   * variables
   ******************************************************************** */

   String Str1 = "Dotgame [Käsekästchen] 1.0 by Jan Keller (04th Apr 2002)";
   String Str2 = "Dotgame [Käsekästchen]:\n";
   String Str3 = "Grid size";
   String Str4 = "Two Player Mode";
   String Str5 = "Computer is 1st Player";
   String Str6 = "Computer is 2nd Player";
   String Str7 = "Press the 'New Game' button for start.";
   String Str8 = "Green 0 : 0 Yellow";
   String Str9 = "Player 1's turn";
   String Str10 = "Player 2's turn";
   String Str11 = "Game Over, press 'New Game' for start.";

   Field board;

   int Computer = C.None;       // just for keeping track
   int WhoIsNext = C.Player1;
   int Start = C.None;

   java.awt.Button Info;            // button for information about the game
   java.awt.Button NewGame;         // button for a new game
   java.awt.Label  SizeLabel;       // label before the choice about the size of the grid
   java.awt.Label  ScoreLabel;      // label to report the actual score
   java.awt.Choice SizeChoice;      // choice for the grid size
   java.awt.Choice WhoStarts;	    // choice for "who starts"
   java.awt.TextArea ScoreBoard;    // actual reports in the play

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public void init() {
     // resize, set background color
     setLayout(null);
     setSize(600, 400);
     setBackground(new Color(32, 92, 32));

     NewGame = new java.awt.Button();
     add(NewGame);
     NewGame.setLabel("New Game");
     NewGame.setBounds(405,5,70,20);
     NewGame.setBackground(new Color(12632256));
     NewGame.setCursor(new Cursor(Cursor.HAND_CURSOR));

     Info = new java.awt.Button();
     add(Info);
     Info.setLabel("Info");
     Info.setBounds(480,5,40,20);
     Info.setBackground(new Color(12632256));
     Info.setCursor(new Cursor(Cursor.HAND_CURSOR));

     SizeLabel = new java.awt.Label(Str3, Label.CENTER);
     add(SizeLabel);
     SizeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     SizeLabel.setBounds(405,30,100,20);
     SizeLabel.setBackground(new Color(200,200,200));

     SizeChoice = new java.awt.Choice();
     add(SizeChoice);
     // 7 in the beginning
     SizeChoice.insert("4x4",0); SizeChoice.insert("5x5",1);
     SizeChoice.insert("6x6",2); SizeChoice.insert("7x7",3);
     SizeChoice.insert("8x8",4); SizeChoice.insert("9x9",5);
     SizeChoice.insert("10x10",6);
     SizeChoice.select(2);  // 6x6 as default
     SizeChoice.setBounds(510,30,85,20);
     SizeChoice.setFont(new Font("Dialog", Font.PLAIN, 12));
     SizeChoice.setCursor(new Cursor(Cursor.HAND_CURSOR));

     WhoStarts = new java.awt.Choice();
     add(WhoStarts);
     WhoStarts.addItem(Str4); // two-player mode in the beginning
     WhoStarts.addItem(Str5);
     WhoStarts.addItem(Str6);
     WhoStarts.setBounds(405,55,190,20);
     WhoStarts.setFont(new Font("Dialog", Font.PLAIN, 12));
     WhoStarts.setCursor(new Cursor(Cursor.HAND_CURSOR));

     ScoreBoard = new java.awt.TextArea(Str7, 10, 10, TextArea.SCROLLBARS_NONE);
     add(ScoreBoard);
     ScoreBoard.setBounds(405,80,190,60);
     ScoreBoard.setEditable(false);
     ScoreBoard.setForeground(new Color(32,32,255));
     ScoreBoard.setBackground(new Color(255,255,255));
     ScoreBoard.setFont(new Font("Dialog", Font.PLAIN, 12));
     ScoreBoard.setCursor(new Cursor(Cursor.HAND_CURSOR));

     ScoreLabel = new java.awt.Label(Str8, Label.CENTER);
     add(ScoreLabel);
     ScoreLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     ScoreLabel.setBounds(405,145,190,20);
     ScoreLabel.setBackground(new Color(200,200,200));

     NewGame.addActionListener(this);
     Info.addActionListener(this);

     addMouseListener(this);
     addMouseMotionListener(this);
     
   }

/* ********************************************************************
   * gui/ paint everything
   ******************************************************************** */

   public void paint(Graphics g) {
     if (board != null) board.display(g);
     super.paint(g);
   }

   public void update(Graphics g) {
     paint(g);
   }

   void updateLabels() {
     ScoreLabel.setText("Green " + String.valueOf(board.count(C.Player1)) + " : " + String.valueOf(board.count(C.Player2)) + " Yellow");
     if (WhoIsNext == C.Player1) ScoreBoard.setText(Str9);
     if (WhoIsNext == C.Player2) ScoreBoard.setText(Str10);
     if (Start == C.None) ScoreBoard.setText(Str11);
   }

/* ********************************************************************
   * handler for the buttons
   ******************************************************************** */

   public void actionPerformed(java.awt.event.ActionEvent event) {
     Object obj = event.getSource();
     if (obj == Info) {
       AboutBox aboutbox = new AboutBox(Str1, Str2);
       aboutbox.show();
     }
     if (obj == NewGame) {
       int i = SizeChoice.getSelectedIndex() + 4;   // why + 4? -> see SizeChoice.insert in init()
       board = new Field(i); // and garbage collection destroys any old field
       WhoIsNext = C.Player1;
       String Str = WhoStarts.getSelectedItem();
       if (Str == Str4) Computer = C.None;
       if (Str == Str5) {
         Computer = C.Player1;
         board.computerMove();
         WhoIsNext = C.Player2;
       }
       if (Str == Str6) Computer = C.Player2;
       Start = C.Yes;
       updateLabels();
       repaint();
     }

   }

/* ********************************************************************
   * mouse-handler
   ******************************************************************** */

   public void mouseReleased(MouseEvent e) {
     int x = e.getX();
     int y = e.getY();     
     if ((WhoIsNext != Computer) && (Start == C.Yes) && (board != null)) {
       board.notifyClick(x, y, WhoIsNext, this.getGraphics());   
       if (board.gameOver() == true) {
         Start = C.None;
       } else {
         if (board.isNcell() == false) {
           if (WhoIsNext == C.Player1) WhoIsNext = C.Player2; else WhoIsNext = C.Player1;
           if (Computer == WhoIsNext) {
             board.computerMove();
             if (WhoIsNext == C.Player1) WhoIsNext = C.Player2; else WhoIsNext = C.Player1;
           }
         }
       }
       updateLabels();
     }
   }

   public void mouseMoved(MouseEvent e) {
     int x = e.getX();
     int y = e.getY();
     if ((WhoIsNext != Computer) && (Start == C.Yes) && (board != null))
       board.notifyMove(x, y, WhoIsNext, this.getGraphics());
   }

   public void mousePressed(MouseEvent e) { }
   public void mouseClicked(MouseEvent e) { }
   public void mouseEntered(MouseEvent e) { }
   public void mouseExited(MouseEvent e) { }
   public void mouseDragged(MouseEvent e) { }

/* ********************************************************************
   * just nearly the end of the applet
   ******************************************************************** */

   public String getAppletInfo() {
     return Str1;
   }

}