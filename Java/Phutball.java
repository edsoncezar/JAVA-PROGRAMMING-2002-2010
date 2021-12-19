/**
 * ---- general information ----
 *
 * Phutball.java: A Phutball applet.
 * 
 * @version 1.0,
 * @author  Jan Keller
 * @mail    post@jan-keller.de
 * @url     www.jan-keller.de
 * @place   Leipzig, Germany
 *
 * ---- Description ----
 *
 * JAVA programm for playing Philosopher's Football/ Phutball on the internet.
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

class AboutBox extends Frame implements ActionListener, WindowListener {

/* ********************************************************************
   * variables
   ******************************************************************** */

    java.awt.TextArea info;
    Button Ok;

/* ********************************************************************
   * initialization
   ******************************************************************** */

   AboutBox(String Str1, String Str2) {

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

public class Phutball extends Applet implements ActionListener, MouseListener {

/* ********************************************************************
   * variables
   ******************************************************************** */

   static int Empty = 0;        // piece of Playfield is empty
   static int None = 0;         // used for computer and winning condition
   static int Player = 1;       // piece of Playfield is occupied by a neutral Player
   static int Ball = 2;         // piece of Playfield is occupied by the Ball
   static int Jump = 3;         // possible jump
   static int Player1 = 4;      // Player1
   static int Player2 = 5;      // Player2
   static int NotInField = -1;  // clicked not in field
   static int UGoal = -2;       // clicked on upper goal
   static int LGoal = -3;       // clicked on lower goal

   int step = 0;    // size of a cell on the board
   int beginx = 0;  // left upper point of the grid
   int beginy = 0;  // dito

   int WhoIsNext = Player1;     // who's turn is next
   int Winner = None;           // who has won
   int Turn = 1;                // count turns

   boolean JumpsVisible = false;    // visualization of possible jumps
   boolean Jumping = false;         // jumping procedure of the move is just in progress

   int Size[] = new int[2];     // array containing the field sizes
   int Pos[] = new int[2];      // array containing the position in the field after a mouse click
   int Field[][];               // Playfield

   String Str1 = "Phutball 1.0 by Jan Keller (25th Mar 2002)";
   String Str2 = "Phutball:\nThis is a game for 2 players. The first player tries to get the blue ball to the upper line or in the upper goal while the second player tries to get the ball to the lower line.\nA turn consists of either the placement of a neutral player on an empty square of the playfield or in a jump or a series of jumps. A jump is a movement of the ball over a horizontal, vertical or diagonal line of players. The jump ends in an empty square und the line of player will be removed from the playground. The Upper Goal represents the area before the first line and Lower Goal the area after the last line.\nTo start a series of jumps please click on the ball. All possible jumps are displayed in lighter colors. You do not have to do more than one jump. Just stop jumping with the 'Done Turn' Button.\nSo, that's all. Have fun.";
   String Str3 = "Size of the Board:";
   String Str4 = ". turn: Player One\nGoal is to reach the upper line.\n";
   String Str5 = ". turn: Player Two\nGoal is to reach the lower line.\n";
   String Str6 = "Two Player Mode";
   String Str7 = "Computer is 2nd Player";
   String Str8 = "Player One has won.";
   String Str9 = "Player Two has won.";

   java.awt.Button NewGame;         // button for a new game
   java.awt.Button DoneTurn;        // button for turn done
   java.awt.Button Info;            // Information about the Game
   java.awt.Label  SizeLabel;       // Label before the choice Size
   java.awt.Choice SizeChoicex;     // Choice of the Size of the Board - x
   java.awt.Choice SizeChoicey;     // Choice of the Size of the Board - y
   java.awt.Choice WhoStarts;	    // Choice for "who starts"
   java.awt.TextArea ScoreBoard;    // actual reports in the play
   java.awt.Label  UGoalLabel;      // upper goal Label
   java.awt.Label  LGoalLabel;      // lower goal Label

/* ********************************************************************
   * initialization
   ******************************************************************** */

   public void init() {

     // resize, set background color
     setLayout(null);
     setSize(600,400);
     setBackground(new Color(32,92,32));

     NewGame = new java.awt.Button();
     add(NewGame);
     NewGame.setLabel("New Game");
     NewGame.setBounds(405,5,70,20);
     NewGame.setBackground(new Color(12632256));
     NewGame.setCursor(new Cursor(Cursor.HAND_CURSOR));

     DoneTurn = new java.awt.Button();
     add(DoneTurn);
     DoneTurn.setLabel("Done Turn");
     DoneTurn.setBounds(480,5,70,20);
     DoneTurn.setBackground(new Color(12632256));
     DoneTurn.setCursor(new Cursor(Cursor.HAND_CURSOR));

     Info = new java.awt.Button();
     add(Info);
     Info.setLabel("Info");
     Info.setBounds(555,5,40,20);
     Info.setBackground(new Color(12632256));
     Info.setCursor(new Cursor(Cursor.HAND_CURSOR));

     SizeLabel = new java.awt.Label(Str3, Label.CENTER);
     add(SizeLabel);
     SizeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     SizeLabel.setBounds(405,30,100,20);
     SizeLabel.setBackground(new Color(200,200,200));

     SizeChoicex = new java.awt.Choice();
     add(SizeChoicex);
     // 7 in the beginning
     SizeChoicex.addItem("7"); SizeChoicex.addItem("9");
     SizeChoicex.addItem("11"); SizeChoicex.addItem("13");
     SizeChoicex.addItem("15"); SizeChoicex.addItem("17");
     SizeChoicex.addItem("19");
     SizeChoicex.setBounds(510,30,40,20);
     SizeChoicex.setFont(new Font("Dialog", Font.PLAIN, 12));
     SizeChoicex.setCursor(new Cursor(Cursor.HAND_CURSOR));

     SizeChoicey = new java.awt.Choice();
     add(SizeChoicey);
     // 11 in the beginning
     SizeChoicey.addItem("11"); SizeChoicey.addItem("13");
     SizeChoicey.addItem("15"); SizeChoicey.addItem("17");
     SizeChoicey.addItem("19"); SizeChoicey.addItem("21");
     SizeChoicey.addItem("23"); SizeChoicey.addItem("25");
     SizeChoicey.setBounds(555,30,40,20);
     SizeChoicey.setFont(new Font("Dialog", Font.PLAIN, 12));
     SizeChoicey.setCursor(new Cursor(Cursor.HAND_CURSOR));

     WhoStarts = new java.awt.Choice();
     add(WhoStarts);
     WhoStarts.addItem(Str6); // two-player mode in the beginning
     WhoStarts.addItem(Str7);
     WhoStarts.setBounds(405,55,190,20);
     WhoStarts.setFont(new Font("Dialog", Font.PLAIN, 12));
     WhoStarts.setCursor(new Cursor(Cursor.HAND_CURSOR));


     ScoreBoard = new java.awt.TextArea("1" + Str4, 10, 10, TextArea.SCROLLBARS_NONE);
     add(ScoreBoard);
     ScoreBoard.setBounds(405,80,190,60);
     ScoreBoard.setEditable(false);
     ScoreBoard.setForeground(new Color(32,32,255));
     ScoreBoard.setBackground(new Color(255,255,255));
     ScoreBoard.setFont(new Font("Dialog", Font.PLAIN, 12));
     ScoreBoard.setCursor(new Cursor(Cursor.HAND_CURSOR));

     UGoalLabel = new java.awt.Label("Upper Goal", Label.CENTER);
     add(UGoalLabel);
     UGoalLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     UGoalLabel.setBounds(405,150,80,20);
     UGoalLabel.setBackground(new Color(200,200,200));

     LGoalLabel = new java.awt.Label("Lower Goal", Label.CENTER);
     add(LGoalLabel);
     LGoalLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     LGoalLabel.setBounds(405,220,80,20);
     LGoalLabel.setBackground(new Color(200,200,200));

     NewGame.addActionListener(this);
     DoneTurn.addActionListener(this);
     Info.addActionListener(this);

     addMouseListener(this);

     // set Size of the Board - first is horizontal
     Size[0] = 7; Size[1] = 11; 
     createField();

   }

/* ********************************************************************
   * Fieldmanipulations
   ******************************************************************** */

   void createField() {
     Field = new int[Size[0]][Size[1]];
     for (int i = 0; i < Size[0]; i++)
     for (int j = 0; j < Size[1]; j++)
       Field[i][j] = Empty;
     Field[Size[0]/2][Size[1]/2] = Ball;
     setGridConstants();
   }

   void setGridConstants() {
     // length = max(Size[i])|i
     int length = Size[0];
     if (length < Size[1]) length = Size[1];
     step = 400 / length;
     beginx = 200 - step * Size[0] / 2;
     beginy = 200 - step * Size[1] / 2;
   }

   int[] getPos(int x, int y) {
     
     int goalx = 490;    // x-position of goal-fields
     int goaluy = 150;   // y-pos of upper goal field
     int goally = 220;   // y-pos of lower goal field
     int goall = 20;     // width and height of goal fields

     int pos[] = new int[2];
     if ((x < beginx) || (x > 399 - beginx)) pos[0] = NotInField;
     else pos[0] = (x - beginx) / step;
     if ((y < beginy) || (y > 399 - beginy)) pos[1] = NotInField;
     else pos[1] = (y - beginy) / step;
     
     if ((x > goalx) && (x < goalx + goall)) {
       if ((y > goaluy) && (y < goaluy + goall)) { pos[0] = 0; pos[1] = UGoal; }
       if ((y > goally) && (y < goally + goall)) { pos[0] = 0; pos[1] = LGoal; }
     }

     return pos;
   }

   /* 123      mapping numbers to direction
      4*5
      678 */

   int Xnext(int x, int i) {
     if (((i == 3) || (i == 5) || (i == 8)) && (x < Size[0] - 1)) return x + 1;
     if (((i == 1) || (i == 4) || (i == 6)) && (x > 0))           return x - 1;
     return x;
   }

   int Ynext(int y, int i) {
     if (((i == 1) || (i == 2) || (i == 3)) && (y > 0))           return y - 1;
     if (((i == 6) || (i == 7) || (i == 8)) && (y < Size[1] - 1)) return y + 1;
     return y;
   }

   boolean Nsite(int x, int y, int i, int p) {
     // test if you are on the edge
     if ((((i == 3) || (i == 5) || (i == 8)) && (x == Size[0] - 1)) ||
         (((i == 1) || (i == 4) || (i == 6)) && (x == 0)) ||
         (((i == 1) || (i == 2) || (i == 3)) && (y == 0)) ||
         (((i == 6) || (i == 7) || (i == 8)) && (y == Size[1] - 1)))
       return false;
     int a = Xnext(x, i);
     int b = Ynext(y, i);
     if (Field[a][b] == p) return true;
     return false;
   }

   void setJumps(int Pos[]) {
     // for every of the eight directions
     for (int i = 1; i <= 8; i++) {
       int x = Pos[0];
       int y = Pos[1];
       while (Nsite(x, y, i, Player) == true) {
         x = Xnext(x, i);
         y = Ynext(y, i);
       }
       if ((Field[x][y] != Ball) && (Nsite(x, y, i, Empty) == true)) {
         x = Xnext(x, i);
         y = Ynext(y, i);
         Field[x][y] = Jump;
       }         
     }
     JumpsVisible = true;
     update(this.getGraphics());
   }

   void clearJumps() {
     for (int i = 0; i < Size[0]; i++)
     for (int j = 0; j < Size[1]; j++)
       if (Field[i][j] == Jump) Field[i][j] = Empty;
     JumpsVisible = false;
     update(this.getGraphics());
   }

   void clearLine(int x, int y, int a, int b) {
     // signum self made
     int sx = 0, sy = 0;
     if (a > x) sx = 1; if (a < x) sx = -1;
     if (b > y) sy = 1; if (b < y) sy = -1;
     // absolute value self made
     int dif = 0;
     if (a - x > 0) dif = a - x; else dif = x - a;
     // maximum self made
     if (dif == 0)
       if (b - y > 0) dif = b - y; else dif = y - b;
     for (int i = 1; i < dif; i++) Field[x + i * sx][y + i * sy] = Empty;
   }

   void makeJump(int Pos[]) {
     int x = 0; int y = 0;
     for (int i = 0; i < Size[0]; i++)
     for (int j = 0; j < Size[1]; j++)
       if (Field[i][j] == Ball) { x = i; y = j; }
     clearLine(x, y, Pos[0], Pos[1]);
     Field[x][y] = Empty;
     Field[Pos[0]][Pos[1]] = Ball;
     if (Pos[1] == 0) Winner = Player1;
     if (Pos[1] == Size[1] - 1) Winner = Player2;
   }

   boolean goalJumpPossible() {
     int x = 0; int y = 0;
     for (int i = 0; i < Size[0]; i++)
     for (int j = 0; j < Size[1]; j++)
       if (Field[i][j] == Ball) { x = i; y = j; }
     if (y == 1) {
       if (x > 0) if (Field[x - 1][0] == Player) return true;
       if (Field[x][0] == Player) return true;
       if (x < Size[0] - 1) if (Field[x + 1][0] == Player) return true;
     }
     if (y == Size[1] - 2) {
       if (x > 0) if (Field[x - 1][y + 1] == Player) return true;
       if (Field[x][y + 1] == Player) return true;
       if (x < Size[0] - 1) if (Field[x + 1][y + 1] == Player) return true;
     }
     return false;
   }

   void tryGoalJump(int Goal) {
     int x = 0; int y = 0;
     for (int i = 0; i < Size[0]; i++)
     for (int j = 0; j < Size[1]; j++)
       if (Field[i][j] == Ball) { x = i; y = j; }
     if (y == 1) {
       if (Goal != UGoal) return;
       if (x > 0) if (Field[x - 1][0] == Player) Winner = Player1;
       if (Field[x][0] == Player) Winner = Player1;
       if (x < Size[0] - 1) if (Field[x + 1][0] == Player) Winner = Player1;
     }
     if (y == Size[1] - 2) {
       if (Goal != LGoal) return;
       if (x > 0) if (Field[x - 1][y + 1] == Player) Winner = Player2;
       if (Field[x][y + 1] == Player) Winner = Player2;
       if (x < Size[0] - 1) if (Field[x + 1][y + 1] == Player) Winner = Player2;
     }
     if (Winner != None) Field[x][y] = Empty;
   }

   boolean isJump() {
     for (int i = 0; i < Size[0]; i++)
     for (int j = 0; j < Size[1]; j++)
       if (Field[i][j] == Jump) return true;
     if (goalJumpPossible() == true) return true;
     return false;
   }

/* ********************************************************************
   * scoreboard
   ******************************************************************** */

   void setScoreBoard(int player) {
     if (player == Player1) {
       ScoreBoard.setForeground(new Color(32,32,255));
       ScoreBoard.setText(String.valueOf(Turn) + Str4);
     }
     if (player == Player2) {
       ScoreBoard.setForeground(new Color(255,32,32));
       ScoreBoard.setText(String.valueOf(Turn) + Str5);
     }
   }

   void setWinnerMsg() {
     ScoreBoard.setForeground(Color.black);
     if (Winner == Player1) ScoreBoard.setText(Str8);
     if (Winner == Player2) ScoreBoard.setText(Str9);
   }

/* ********************************************************************
   * gui/ paint everything
   ******************************************************************** */

   public void paint(Graphics g) {
     paintGrid(g);
     paintField(g);
     super.paint(g);
   }

   public void update(Graphics g) {
     paint(g);
   }

   void paintGrid(Graphics g) {
     g.setColor(Color.white);

     {  // the lattice
       for (int i = 0; i <= Size[0]; i++) g.drawLine(beginx + i * step, beginy, beginx + i * step, 399 - beginy);
       for (int j = 0; j <= Size[1]; j++) g.drawLine(beginx, beginy + j * step, 399 - beginx, beginy + j * step);     
     }

     g.drawRect(490, 150, 20, 20);
     g.drawRect(490, 220, 20, 20);
     g.setColor(new Color(60, 140, 60));
     g.fillRect(491, 151, 18, 18);
     g.fillRect(491, 221, 18, 18);

   }

   void paintField(Graphics g) {
     for (int i = 0; i < Size[0]; i++)
     for (int j = 0; j < Size[1]; j++) {
       if (Field[i][j] == Ball)   g.setColor(Color.blue);
       if (Field[i][j] == Player) g.setColor(Color.darkGray);
       if ((Field[i][j] == Ball) || (Field[i][j] == Player))
         g.fillOval(beginx + i * step + 1, beginy + j * step + 1, step - 2, step - 2);
       if (Field[i][j] == Jump) {
         g.setColor(new Color(60, 140, 60));
         g.fillRect(beginx + i * step + 1, beginy + j * step + 1, step - 2, step - 2);
       }
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
       Size[0] = Integer.valueOf(SizeChoicex.getSelectedItem()).intValue();
       Size[1] = Integer.valueOf(SizeChoicey.getSelectedItem()).intValue();
       // new Size
       createField();
       WhoIsNext = Player1;
       Turn = 1;
       Winner = None;
       setScoreBoard(WhoIsNext);
       Jumping = false;
       update(this.getGraphics());
     }
     if (object == DoneTurn)
       if (Jumping == true) {  // end turn now
         clearJumps();
         Turn++;
         Jumping = false;
         if (WhoIsNext == Player1) WhoIsNext = Player2; else WhoIsNext = Player1;   // toggle player
         setScoreBoard(WhoIsNext);
         update(this.getGraphics());
       }
   }

/* ********************************************************************
   * mouse was clicked/ invoke legal moves if possible
   ******************************************************************** */

   public void mouseReleased(MouseEvent e) {
     int x = e.getX();	// get mouse position
     int y = e.getY();
     Pos = getPos(x, y);

     if ((Pos[0] != NotInField) && (Pos[1] != NotInField) && (Winner == None)) {    // a valid point in the field

       // first process upper and lower goal, then somewhere in the field

       if ((Pos[1] == UGoal) || (Pos[1] == LGoal)) {
         tryGoalJump(Pos[1]);
       } else {

         if ((Field[Pos[0]][Pos[1]] == Empty) && (Jumping == false)) {    // put a neutral men on the field
           Field[Pos[0]][Pos[1]] = Player;
           if (WhoIsNext == Player1) WhoIsNext = Player2; else WhoIsNext = Player1;   // toggle player
           Turn++;    // next turn
           setScoreBoard(WhoIsNext);
           if (JumpsVisible == true) clearJumps();
           update(this.getGraphics());
         }

         if (Field[Pos[0]][Pos[1]] == Ball)
           if (JumpsVisible == false) setJumps(Pos); else clearJumps();

         if (Field[Pos[0]][Pos[1]] == Jump) { // jumping now starts
           if (Jumping == false) Jumping = true;
           clearJumps();
           makeJump(Pos);
           setJumps(Pos);
           if (isJump() == false) {    // no more jumps possible
             if (WhoIsNext == Player1) WhoIsNext = Player2; else WhoIsNext = Player1;   // toggle player
             Turn++;
             Jumping = false;
             setScoreBoard(WhoIsNext);
           }
           update(this.getGraphics());           
         }

       }
       if (Winner != None) {
         setWinnerMsg();
         update(this.getGraphics());           
       }
     }
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

/* ********************************************************************
   * just nearly the end of the applet
   ******************************************************************** */

   public String getAppletInfo() {
     return Str1;
   }   
}