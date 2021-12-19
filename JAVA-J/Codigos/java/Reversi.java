/**
 * ---- general information ----
 *
 * Reversi.java: A Reversi applet.
 * 
 * @version 1.0, 
 * @author  Jan Keller
 * @mail    post@jan-keller.de
 * @url     www.jan-keller.de
 * @place   Leipzig, Germany
 *
 * ---- Description ----
 *
 * JAVA programm for playing Reversi/Othello on the internet.
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
import java.util.EventObject;
import java.io.PrintStream;

class Playfield {

/* ********************************************************************
   * variables
   ******************************************************************** */

   static int Error = 0;		// if access out of bound
   static int Empty = 1;		// this site is still unoccupied
   static int Blue = 2;   		// the blue side/stones
   static int Red = 3;    		// the red side/stone
   int width = 0, height = 0;	// width and height of the Field
   int Field[][];				// the playground {Empty, Red, Blue}
   int Score[] = new int[2];	// the score of the two players (red first)
   int x, y;					// position on the Field

/* ********************************************************************
   * initialization
   ******************************************************************** */

   Playfield(int w, int h) {
     if ((w > 0) && (w < 20) && (h > 0) && (h < 20)) {
       width = w; height = h;
       Field = new int[width][height];	// create new Field
       for (int i = 0; i < width; i++)	// clear Field
       for (int j = 0; j < height; j++) Field[i][j] = Empty;
       Score[0] = Score[1] = 0;
     } else { 
       System.out.println("Playfield indices out of Bound - exit");
	 System.exit(1);
     }
   }

/* ********************************************************************
   * methods
   * get&put stones/ count scores/ change the stones /
   * move possible/ end of the game?
   ******************************************************************** */

   void clearBoard() {
      for (int i = 0; i < width; i++)	// clear Field
      for (int j = 0; j < height; j++) Field[i][j] = Empty;
      Field[3][3] = Field[4][4] = Blue;
      Field[3][4] = Field[4][3] = Red;
   }

   int get(int x, int y) {
     if ((x >= 0) && (x < width) && (y >= 0) && (y < height)) return Field[x][y];
     else return Error;
   }

   void set(int x, int y, int that) {
     if ((x >= 0) && (x < width) && (y >= 0) && (y < height) && ((that == Empty) || (that == Red) || (that == Blue)))
       Field[x][y]=that;
   }

   // count score
   int[] getScore() {
     Score[0] = Score[1] = 0;
     for (int i = 0; i < width; i++)	// count score (red first)
     for (int j = 0; j < height; j++) {
       if (Field[i][j] == Red) Score[0]++;
       if (Field[i][j] == Blue) Score[1]++;
     }
     return Score;
   }

   /* computes the next occupied position on the board of a given position in one the eight possible directions
        012
        3x4  eight directions
        567
   */
   // d - direction, a - horizontal, b - vertical position
   boolean NextSite(int a, int b, int d) {
     if (d == 0) { x = a-1; y = b-1; }
     if (d == 1) { x = a;   y = b-1; }
     if (d == 2) { x = a+1; y = b-1; }
     if (d == 3) { x = a-1; y = b; }
     if (d == 4) { x = a+1; y = b; }
     if (d == 5) { x = a-1; y = b+1; }
     if (d == 6) { x = a;   y = b+1; }
     if (d == 7) { x = a+1; y = b+1; }
     if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) return false; // only sites on the board
     if (Field[x][y] == Empty) return false; // we only want occupied sites
     return true;
   }

   // same as nextsite but only if stone  is of "player"-color
   boolean NextSiteNotPlayer(int a, int b, int d, int Player) {
     boolean h = NextSite(a, b, d);
     if (Field[x][y] == Player) h = false;
     return h;
   }


   /* a change in a line is possible if the next stone in this line is of a different color and then there is no empty stone
      but a stone of the same color somewhere in this line 
      a - horizontal/, b - vertical position, Player = {Red, Blue}, d - direction */
   boolean LinePossible(int a, int b, int Player, int d) {
     if (NextSite(a, b, d) == true) { // next site on the board and not empty
       if (Field[x][y] != Player) { // nextsite not same colour or empty
         while (NextSite(x, y, d) == true) { // still on the board and not empty
           if (Field[x][y] == Player) return true; // found a way
         }    
       }
     }
     return false;
   }

   // a move is possible, when a change in a line for at least one of the eight directions is possible
   boolean MovePossible(int a, int b, int Player) {
     if (Field[a][b] != Empty) return false;	// already occupied
     for (int i = 0; i < 8; i++) // try all eight directions
       if (LinePossible(a, b, Player, i) == true) return true;
     return false;
   }

   // check if Player can make any move
   boolean AnyMovePossible(int Player) {
     for (int i = 0; i < width; i++)
     for (int j = 0; j < height; j++)
     if (MovePossible(i, j, Player) == true) return true;
     return false;
   }

   // this checks the End
   boolean CheckEnd() {
     boolean h = true;
     if ((AnyMovePossible(Red) == true) || (AnyMovePossible(Blue) == true)) h = false;	// still any move left
     if ((Score[0] == 0) || (Score[1] == 0)) h = true;		// one player hasn't any stones left
     return h;
   }

   // update a current position
   void Update(int a, int b, int Player) {
     set(a, b, Player);
     for (int i = 0; i < 8; i++) {  // try all eight directions
       if (LinePossible(a, b, Player, i) == true) {
         x = a; y = b;
           // next site on the board that is not Players
         while (NextSiteNotPlayer(x, y, i, Player) == true) set(x, y, Player);
       }
     }
   }

}

class logo {

/* ********************************************************************
   * variables
   ******************************************************************** */

   static int Size = 8; // okay, we are on a 8x8 field
   static int Blue = 2; // the blue side/stones
   static int Red = 3;  // the red side/stone

   int level;   // thinking level
   int color;   // color of the own stones/pieces
   int bMove[] = new int[2];    // coordinates of best move
   int bVal = -1;   // value of a configuration
   Playfield Game = new Playfield(Size, Size);



/* ********************************************************************
   * initialization
   ******************************************************************** */

   logo(Playfield x) {
     copy(x, Game);
   }

/* ********************************************************************
   * methods
   * setLevel/ setColor/ thinking/ bestMove
   ******************************************************************** */

   void copy(Playfield a, Playfield b) {
     for (int i = 0; i < Size; i++)
     for (int j = 0; j < Size; j++) b.set(i, j, a.get(i, j));
   }

   void setLevel(int l) { level = l; }

   void setColor(int c) { color = c; }

   void thinking() {
     // compute maxlevel - in the end and in the beginning we don't want to think so much
     int[] Score = new int[2];
     Score = Game.getScore();
     int maxlevel = Size * Size - Score[0] - Score[1];
     if (level > maxlevel) level = maxlevel;
     if (maxlevel > 40) level = 3;	// this can be changed
     
     // one sweep through the board
     for (int i = 0; i < Size; i++)
     for (int j = 0; j < Size; j++)
     if (Game.MovePossible(i, j, color) == true) {
       int val = recursion(Game, i, j, 1);
       // just to make sure, we do at least one move (the best of the worst)
       // val must always be greater of equal zero!
       if (val > bVal) {
         bVal = val;
         bMove[0] = i;
         bMove[1] = j;
       }
     }
   }

   int recursion(Playfield g, int a, int b, int l) {
     int val = -1;
     Playfield h = new Playfield(Size, Size);
     copy(g, h);
     // if computers turn set color of computer, else set color of opponent
     // computer is every half turn on turn -> odd thinking levels
     int col;
     if (l % 2 == 1) col = color; else if (color == Blue) col = Red; else col = Blue;
     h.Update(a, b, col);
     if (l == level) val = eval(h);
     else {
       int bval = -1;
       for (int i = 0; i < Size; i++)
       for (int j = 0; j < Size; j++)
       if (h.MovePossible(i, j, col) == true) { 
         val = recursion(h, i, j, l+1);
         if (val > bval) bval = val;
       }
     };

     return val;
   }

   // evaluate a configuration
   int eval(Playfield h) {
     int val = -1;

     int stones = 0;
     int pmoves = 0;
     int conval = 0;

     // count stones [0..64]
     // count possible moves [0..60]
     // measures configuration val
     // 1+ for every in the inner 16 squares, 5+ for a corner, 5- for an adjacent field

     for (int i = 0; i < Size; i++)
     for (int j = 0; j < Size; j++) {
       if (h.get(i, j) == color) stones++;
       if (h.MovePossible(i, j, color) == true) pmoves++;
       if ((i > 1) && (i < 6) && (j > 1) && (j < 6) && (h.get(i, j) == color)) conval++;
       if ((i < 2) && (j < 2) && (h.get(i, j) == color))
         if ((i == 0) && (j == 0)) conval +=5; else conval -=5;
       if ((i > 5) && (j > 5) && (h.get(i, j) == color))
         if ((i == 7) && (j == 7)) conval +=5; else conval -=5;
       if ((i > 5) && (j < 2) && (h.get(i, j) == color))
         if ((i == 7) && (j == 0)) conval +=5; else conval -=5;
       if ((i < 2) && (j > 5) && (h.get(i, j) == color))
         if ((i == 0) && (j == 7)) conval +=5; else conval -=5;
     }

     // doesn't really matter, just to make it positive
     conval += 60;

     // permitting no stone
     if (stones == 0) return val;

     // compute the phase of the game
     int[] Score = h.getScore();
     int phase = Score[0] + Score[1];

     // in the late phase you want to have many stones, in the early phase not
     if (phase < 40) val = stones + pmoves + conval; else val = pmoves + conval;

     // so maximal val is about 64 + 60 + 20 + 16 = 160 and minimal about 0

     // if opponent is valued change the result according to the min-max-principle
     if (level % 2 == 0) val = 160 - val;

     return val;
   }

   int[] bestMove() { return bMove; }

}

public class Reversi extends Applet implements ActionListener, ItemListener, MouseListener {

/* ********************************************************************
   * variables
   ******************************************************************** */

   static int None = 0;		// computer does not play
   static int Empty = 1;	// this site is still unoccupied
   static int Blue = 2;   	// the blue side/stones
   static int Red = 3;    	// the red side/stone
   int WhoIsNext = Blue;	// next move (Red, Blue)
   int Computer = None;		// determines the Color of the Computer (Red, Blue, None)
   boolean TheEnd = false;	// indicates if the game is over

   MediaTracker tracker;
   Image eye[]    = new Image[14];	// images of the eyes
   Image stones[] = new Image[6];	// images for blue and red
   int ex, ey;						// position of the eyes
   int ol = -1, ok = -1;			// old state of the eyes

   String Str1 = "Blue's turn";				// status line next turn
   String Str2 = "Red's turn";				// status line next turn
   String Str3B = "Blue has won";			// status line
   String Str3R = "Red has won";			// status line
   String Str4 = "The Computer has won";	// status line
   String Str5 = "Incorrect move";			// status line
   String Str6 = "You move first";			// choice "who starts"
   String Str7 = "Computer moves first";	// choice "who starts"
   String Str8 = "Two player modus";		// choice "who starts"
   String Str9 = "Draw";					// status line
   String Str12 = "Reversi";				// status line
   String Str13 = "Reversi by Jan Keller (24th Mar 2002)";
   String Str14 = "Reversi 1.1 by Jan Keller";
   String Status = Str12;					// status line

   int Size = 8;       			// 8x8 lattice - play field
   Playfield Game;   			// the playground (Empty, Red, Blue)
   int Score[] = new int[2];	// the score (red first, blue next)
//    int x, y;					// position on the board    

   java.awt.Choice WhoStarts;	// choice for "who starts"
   java.awt.Choice AiLevel;	    // choice for "thinking level"
   java.awt.Label  AiLabel;     // label for "thinking level"
   java.awt.Checkbox ShowMoves; // checkbox for "show possible moves"
   java.awt.Button NewGame;	 	// button for a new game
   java.awt.Button Info;        // button for the info label
   java.awt.Label  InfoLabel;	// info label
   java.awt.Label  StatusTurn;	// status line for who is next
   java.awt.Label  StatusLine;	// status line
   java.awt.Label  ScoreLine; 	// score

/* ********************************************************************
   * initialization/ loading the images
   ******************************************************************** */

   public void init() {

     // resize, set background color
     setLayout(null);
     setSize(600,400);
     setBackground(new Color(32,92,32));
     Dimension d = getSize();

     // initialization of the board
     Game = new Playfield(Size, Size);
     Game.clearBoard();
     Score = Game.getScore();

     // position of the eyes
     ex = 405; ey = 199 - 20 + 10;

     // read pics
     tracker = new MediaTracker(this);
     for (int i = 0; i < 14; i++) {
       eye[i] = getImage(getCodeBase(), "eye"+i+".gif");
	 tracker.addImage(eye[i], i);
       try { tracker.waitForID(i); } catch (InterruptedException e) {}
     }
     for (int i = 0; i < 6; i++) {
       stones[i] = getImage(getCodeBase(), "trans"+i+".gif");
       tracker.addImage(stones[i],14+i);
       try {tracker.waitForID(i+14); } catch (InterruptedException e) {}
     }

     // diverse labels, choices and buttons
     WhoStarts = new java.awt.Choice();
     add(WhoStarts);
     WhoStarts.addItem(Str8); // two-player mode in the beginning
     WhoStarts.addItem(Str6);
     WhoStarts.addItem(Str7);
     WhoStarts.setBounds(405,5,180,20);
     WhoStarts.setFont(new Font("Dialog", Font.PLAIN, 12));
     WhoStarts.setCursor(new Cursor(Cursor.HAND_CURSOR));

     AiLabel = new java.awt.Label("Thinking Level", Label.CENTER);
     add(AiLabel);
     AiLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
     AiLabel.setBounds(405,32,140,16);
     AiLabel.setBackground(new Color(200,200,200));

     AiLevel = new java.awt.Choice();
     add(AiLevel);
     AiLevel.addItem("1"); // 1 in the beginning
     AiLevel.addItem("2");
     AiLevel.addItem("3");
     AiLevel.addItem("4");
     AiLevel.addItem("5");
     AiLevel.addItem("6");
     AiLevel.setBounds(550,30,35,20);
     AiLevel.setFont(new Font("Dialog", Font.PLAIN, 12));
     AiLevel.setCursor(new Cursor(Cursor.HAND_CURSOR));

     NewGame = new java.awt.Button();
     add(NewGame);
     NewGame.setLabel("New Game");
     NewGame.setBounds(405,55,80,20);
     NewGame.setBackground(new Color(12632256));
     NewGame.setCursor(new Cursor(Cursor.HAND_CURSOR));

     Info = new java.awt.Button();
     add(Info);
     Info.setLabel("Info");
     Info.setBounds(405,80,60,20);
     Info.setBackground(new Color(12632256));
     Info.setCursor(new Cursor(Cursor.HAND_CURSOR));

     InfoLabel = new java.awt.Label(Str14, Label.CENTER);
     add(InfoLabel);
     InfoLabel.setBounds(200-80,200-20,160,40);
     InfoLabel.setBackground(new Color(12632256));
     InfoLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
     InfoLabel.setVisible(false);

     ShowMoves = new java.awt.Checkbox("Show possible Moves", true);
     add(ShowMoves);
     ShowMoves.setBounds(405,105,180,16);
     ShowMoves.setBackground(new Color(200,200,200));
     ShowMoves.setCursor(new Cursor(Cursor.HAND_CURSOR));

     StatusTurn = new java.awt.Label(Str1, Label.CENTER);
     add(StatusTurn);
     StatusTurn.setFont(new Font("Dialog", Font.BOLD, 12));
     StatusTurn.setBounds(405,125,180,16);
     StatusTurn.setBackground(new Color(200,200,200));
     StatusTurn.setForeground(new Color(32,32,255));


     StatusLine = new java.awt.Label(Status,Label.CENTER);
     add(StatusLine);
     StatusLine.setBounds(405,145,180,16);
     StatusLine.setBackground(new Color(200,200,200));

     ScoreLine = new java.awt.Label("Blue 2 - Red 2",Label.CENTER);
     add(ScoreLine);
     ScoreLine.setBounds(405,165,180,16);
     ScoreLine.setBackground(new Color(200,200,200));

     addMouseListener(this);
     Info.addActionListener(this);
     NewGame.addActionListener(this);
     ShowMoves.addItemListener(this);
     WhoStarts.addItemListener(this);
     // control mouse motion
     this.addMouseMotionListener (new mouseMotionHandler());
    }

/* ********************************************************************
   * gui/ paint everything
   ******************************************************************** */

   public void paint(Graphics g) {
     gridPaint();
     stonesPaint();
     eyes(ex, ey, 0, 0);  // paint the eyes (closed)
     if (ShowMoves.getState() == true) PosMovesPaint();
     showStatus("Freedom, Peace and Happiness to all of the world! :-)");  // hidden goodie
     super.paint(g);
   }

   public void update(Graphics g) {
     paint(g);
   }

   private void gridPaint() {
     Graphics g = getGraphics();
     g.setColor(Color.white);

     g.drawRect(0, 0, 399, 399);  // nice frames
     g.drawRect(399, 0, 579, 399);
    
     {  // the lattice
       int i = 0;
       for(i = 1; i < Size; i++) g.drawLine(0, i * 50 - 1, 399, i * 50 - 1);
       for(i = 1; i < Size; i++) g.drawLine(i * 50 - 1, 0, i * 50 - 1, 399);     
     }
   }
    
   private void stonesPaint() {

     Graphics g = getGraphics();

     StatusLine.setText(Status);  // status & score
     Score = Game.getScore();
     ScoreLine.setText("Blue " + String.valueOf(Score[1]) + " - Red " + String.valueOf(Score[0]));
    
     {  // configuration of play ground
       for (int i = 0; i < Size; i++)
       for (int j = 0; j < Size; j++) {
         if (Game.get(i, j) == Blue) g.drawImage(stones[5],i * 50 + 25 - 20, j * 50 + 25 - 20, this); // blue
         if (Game.get(i, j) == Red)  g.drawImage(stones[0],i * 50 + 25 - 20, j * 50 + 25 - 20, this); // red
         // pics are 40x40 pixel
       }
     }
   }

   private void PosMovesPaint() {
   
     if (WhoIsNext == Computer) return;

     Graphics g = getGraphics();
     g.setColor(new Color(60, 140, 60));

     for (int i = 0; i < Size; i++)
     for (int j = 0; j < Size; j++)
       if (Game.MovePossible(i, j, WhoIsNext) == true) g.fillRect(i * 50 + 1, j * 50 + 1, 47, 47);
   }

   private void PosMovesClear() {

     if (WhoIsNext == Computer) return;

     Graphics g = getGraphics();
     g.setColor(new Color(32, 92, 32));

     for (int i = 0; i < Size; i++)
     for (int j = 0; j < Size; j++)
       if (Game.MovePossible(i, j, WhoIsNext) == true) g.fillRect(i * 50 + 1, j * 50 + 1, 47, 47);
   }

/* ********************************************************************
   * handler for the "new game" & the "Info" buttons and "show pos moves" choice
   ******************************************************************** */

   public void itemStateChanged(ItemEvent e) {
     Object object = e.getSource();
     if (object == ShowMoves)
       if (ShowMoves.getState() == true) PosMovesPaint(); else PosMovesClear();
     if (object == WhoStarts) {}
   }

   public void actionPerformed(java.awt.event.ActionEvent event) {
     Object object = event.getSource();
     if (object == Info) InfoLabel.setVisible(!InfoLabel.isVisible());

     if (object == NewGame) {
       InfoLabel.setVisible(false);
	   Game.clearBoard();
	   Score = Game.getScore();
	   TheEnd = false;
       String Str = WhoStarts.getSelectedItem();
       if (Str == Str6) Computer = Red; 	// you move first
       if (Str == Str7) Computer = Blue;	// computer moves first
       if (Str == Str8) Computer = None;	// two player modus
       WhoIsNext = Blue;
       Status = Str12;

       // if the computer moves first make a random move first (since it doesn't matters)
       if (Computer == Blue) {
         // there are 4 possibilities
         int x = (int)(Math.random() * 4);
         if (x == 0) { Game.set(4, 3, Blue); Game.set(4, 2, Blue); }
         if (x == 1) { Game.set(4, 3, Blue); Game.set(5, 3, Blue); }
         if (x == 2) { Game.set(3, 4, Blue); Game.set(2, 4, Blue); }
         if (x == 3) { Game.set(3, 4, Blue); Game.set(3, 5, Blue); }
         Score = Game.getScore();
         WhoIsNext = Red;
       }
       if (WhoIsNext == Blue) StatusTurn.setText(Str1); else StatusTurn.setText(Str2);
       update(this.getGraphics());
     }

   }

/* ********************************************************************
   * UpdateGame/ ComputerMove
   ******************************************************************** */

   /* this makes a move at a given position and _animates_ this by the images
      in stones[] with a length of about 600 ms */
   void UpdateGame(int a, int b, int Player) {
     Graphics g = getGraphics();
     if (ShowMoves.getState() == true) PosMovesClear();
     for (int j = 1; j < 5; j++) {  // animation of the turning stones
       if (Player == Blue) g.drawImage(stones[j],a * 50 + 25 - 20, b * 50 + 25 - 20, this);  // was red
       if (Player == Red) g.drawImage(stones[5-j],a * 50 + 25 - 20, b * 50 + 25 - 20, this);  // was blue
       for (int i = 0; i < 8; i++) {  // try all eight directions
         if (Game.LinePossible(a, b, Player, i) == true) {
           Game.x = a; Game.y = b;
           while (Game.NextSiteNotPlayer(Game.x, Game.y, i, Player) == true) {
		     // next site on the board that is not Players
             if (Player == Blue) g.drawImage(stones[j],Game.x * 50 + 25 - 20, Game.y * 50 + 25 - 20, this);  // was red
             if (Player == Red) g.drawImage(stones[5-j],Game.x * 50 + 25 - 20, Game.y * 50 + 25 - 20, this);  // was blue
           }
         }
       } 
       try { Thread.sleep(100);} catch (InterruptedException f) {}
     }
     Game.Update(a, b, Player);
     if (ShowMoves.getState() == true) PosMovesPaint();
   }

   /* artificial intelligence moves */
   void ComputerMove() {

     logo ai = new logo(Game);

     // determines thinking level
     int level = Integer.valueOf(AiLevel.getSelectedItem()).intValue();
     ai.setLevel(level);

     // set color for computer
     ai.setColor(Computer);

     // start thinking...
     ai.thinking();

     // get results
     int pos[] = new int[2];
     pos = ai.bestMove();
     
     // update Gameboard
     UpdateGame(pos[0], pos[1], Computer);

   }



/* ********************************************************************
   * mouse was clicked/ invoke legal moves if possible
   ******************************************************************** */

   public void mouseReleased(MouseEvent e) {
     InfoLabel.setVisible(false);  // just turn off the "Info" field in case it was turned on

     if ((WhoIsNext != Computer) && (TheEnd == false)) {			// its not the computer move
       int x = e.getX();	// get mouse position
       int y = e.getY();
       if ((x < 400) && (y < 400)) {
         // figure out the position of the click in terms of the playfield
         int a = x * Size / 400;
         int b = y * Size / 400;
         if (Game.MovePossible(a, b, WhoIsNext) == false) {
           Status = Str5;
         } else {
           UpdateGame(a, b, WhoIsNext); // the clou of the game - changes pieces/stones
           if (WhoIsNext == Red) WhoIsNext = Blue; else WhoIsNext = Red;
           Status = Str12;
           if (WhoIsNext == Blue) StatusTurn.setText(Str1); else StatusTurn.setText(Str2);
           TheEnd = Game.CheckEnd();
         }
       }
     }

     if (TheEnd == false) {  // computer or other player cannot move
       if (Game.AnyMovePossible(WhoIsNext) == false) {
         if (WhoIsNext == Red) WhoIsNext = Blue; else WhoIsNext = Red;            
       }
     }

     while ((WhoIsNext == Computer) && (TheEnd == false)) {			// the computer moves
       Status = null;
       ComputerMove();
       if (WhoIsNext == Red) WhoIsNext = Blue; else WhoIsNext = Red;  // can player move
       if (Game.AnyMovePossible(WhoIsNext) == false) if (WhoIsNext == Red) WhoIsNext = Blue; else WhoIsNext = Red;  // can player move
       Status = Str2;
       TheEnd = Game.CheckEnd();
     }


     if (TheEnd == true) {
       Score=Game.getScore();
       if (Score[1] == Score[0]) Status = Str9;  // we're all winners - what a happy world
       if (Score[1] > Score[0]) if (Computer == Blue) Status = Str4; else Status = Str3B;
       if (Score[1] < Score[0]) if (Computer == Red) Status = Str4; else Status = Str3R;
       StatusTurn.setText(null);
     }

     update(this.getGraphics());
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
   * paint & computes the state of the eyes
   ******************************************************************** */

   private void eyes(int x, int y, int k, int l) {
     Graphics g = getGraphics();
     if (k != ok) {
       g.drawImage(eye[k],x , y, this);
       ok = k;
     }
     if (l != ol) {
       g.drawImage(eye[l],x + 40 , y, this);
       ol = l;
     }
   }

   public class mouseMotionHandler extends MouseMotionAdapter {

     int i, j, k, l;   // integers
     double h, alpha;  // angle at with the eyes look into the world

     public void mouseMoved(MouseEvent e) {

       i = e.getX() - (ex + 20);  // left eye
       j = e.getY() - (ey + 20);
       h = (double) i / j;
       alpha = Math.atan(1 / h) * 180 / Math.PI;

       // after the next computation we continue clockwise
       if (i >= 0) alpha += 90; else alpha += 270;
       // now map 15 - 45 Grad (1 Uhr) to 1, 45 - 75 to 2, ... , 345 - 15 to 12
       k = ((int) alpha) / 30 + 1;  // or the frightening eye?
       if ((Math.abs(i) < 5) && (Math.abs(j) < 5)) k = 13;

       i = e.getX() - (ex + 20 + 40);  // right eye
       j = e.getY() - (ey + 20);
       h = (double) i / j;
       alpha = Math.atan(1 / h) * 180 / Math.PI;
       if (i >= 0) alpha += 90; else alpha += 270;
       alpha -= 15;
       if (alpha < 0) alpha += 360;
       l = ((int) alpha) / 30 + 1;
       if ((Math.abs(i) < 5) && (Math.abs(j) < 5)) l = 13;

       eyes(ex, ey, k, l);  // paint the eyes
     }

  }

/* ********************************************************************
   * just nearly the end of the applet
   ******************************************************************** */

   public String getAppletInfo() {
     return Str13;
   }

}