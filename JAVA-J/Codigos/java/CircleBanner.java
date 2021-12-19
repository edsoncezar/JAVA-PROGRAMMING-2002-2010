/**
  * @(#)CircleBanner.java
  * Version : 1.0 06/03/98.
  * Copyright (c) 1998 Michael Raillard. All Rights Reserved.
  * @Author : Michael Raillard
  * E-mail : raillard@arn.net
  *
  * Extends : java.applet.Applet.
  * Purpose : Draws a rotating circular banner.
  *
  */

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*; 
import java.util.Random;
import java.util.Vector;


public class CircleBanner extends Applet implements Runnable {
    
    private static int PARAM_message = 0;
    private static int PARAM_direction = 1;
    private static int PARAM_url = 2;
    private static int PARAM_fontName = 3;
    private static int PARAM_fontSize = 4;
    private static int PARAM_fontStyle = 5;
    private static int PARAM_fontPadding = 6;
    private static int PARAM_background = 7;
    private static int PARAM_foreground = 8;
    private static int PARAM_image = 9;
    private static int PARAM_imageAttribute = 10;
    private static int PARAM_charEffects = 11;
    private static int PARAM_seed = 12;
    private static int PARAM_pause = 13;
    private static int PARAM_rotationfactor = 14;
    private static int PARAM_radius = 15;
    private static int PARAM_x = 16;
    private static int PARAM_y = 17;
    private static double RAD = 180.0 / Math.PI;
    
    private boolean random, fixed, clockwise,
                    center, tile, scale, loaded;
    private double rotationfactor;
    private int pause, message_beg, increment, cH,
                fontSize, fontStyle, randCount, fontPadding, 
                parm_radius, parm_x, parm_y, parm_seed,  
                imageWidth, imageHeight, 
                centered_XCorner, centered_YCorner;
    private int[] mX;
    
    private Color background, foreground;               
    private Color[] randColors; 
    private Dimension appletSize;                     
    private FontMetrics fm;
    private Frame browserFrame;
    private Graphics bufferGraphics;
    private Image buffer, image;
    private MediaTracker tracker;
    private Rectangle[] rectangles;
    private String message, fontName, imageName; 
    private String[] m;
    private Thread animator;
    private URL url;
        
    //array stores HTML Applet Tag param name, and a default
    public String[][] p = {
        {"message","CircleBanner"},
        {"direction","counterclockwise"},
        {"url",""},
        {"fontname","Helvetica"},
        {"fontsize","12"},
        {"fontstyle","plain"},
        {"fontpadding","0"},
        {"background","000000"},
        {"foreground","FFFFFF"},
        {"image",""},
        {"imageattribute",""},
        {"effects",""},
        {"seed",""},
        {"pause","100"},
        {"rotationfactor", "100"},
        {"radius",""},
        {"x",""},
        {"y",""},
    };
    
	public void init()
	{
		setLayout(null);
		appletSize = this.size();    
		
		try { browserFrame = (Frame) getParent(); }
		catch(Exception e) { browserFrame = null; }
		
		//get parameters from HTML doc
		getParameters();
		
		//initiate offscreen buffer.... 
		buffer = this.createImage(appletSize.width, appletSize.height);
		setBufferGraphics();
		
		//dimension of area to hold a character of this font....
        Dimension fontSpaceDim = new Dimension(
            (fm.getMaxAdvance() + 2 * fm.getLeading()) + fontPadding,
            (fm.getMaxAscent() + fm.getMaxDescent() + 2 * fm.getLeading()) + fontPadding );
	    int fontSpace = (fontSpaceDim.width > fontSpaceDim.height) ? fontSpaceDim.width : fontSpaceDim.height;
		
		//determine properties of circle....
		int cX, cY, minR, maxR;
		double r;
		int square = (appletSize.width > appletSize.height) ? 
		    appletSize.height : appletSize.width;               
		cX = cY = square/2;                               //default center
		
		if(parm_x != -1000000) {cX = parm_x;} 
		if(parm_y != -1000000) {cY = parm_y;}
		
		//determine radius....
		r = (double) (cX - ((square/4)/2));               //default radius
		if (parm_radius > -1) r = (double)parm_radius;

		//plot the the Cartesian co-ordinates of a circle that will fit in the applet....
		Vector c = CirclePoints.circle(cX, cY, r, 1);
		
        //do math....
		double angle = (Math.atan2((double) fontSpace, (double) r)) * RAD;
		double num_m_rect = 360.00 / angle;
		double anim_angle = (Math.atan2(((double) fontSpace) * rotationfactor, (double) r)) * RAD;
		double num_anim_rect = 360.00 / anim_angle;
		double anim_IncValue = ((double) c.size()/num_anim_rect);
		if (anim_IncValue < 1) anim_IncValue = 1.00;
		
		if (rotationfactor > 1.00) num_m_rect = num_anim_rect;
		
		//create an array of rectangles, where chars will be placed....  
    	double dw = (double)fontSpaceDim.width + fm.getLeading();
    	double dh = (double)fontSpaceDim.height;
    	
	    Rectangle[] rec = new Rectangle[c.size()];
	    int prevX = (int)Math.rint(((DoublePoint)c.lastElement()).x - dw/2.00);
	    int prevY = (int)Math.rint(((DoublePoint)c.lastElement()).y - dh/2.00);
	    int nextX, nextY;
	    int j = -1;
		double node = anim_IncValue;
	    
	    for (int i = 0; ((int)node) < c.size(); node += anim_IncValue){               
	        if ((i = (int) Math.rint(node)) >= c.size()) break;  
	    
	        nextX = (int)Math.rint(((DoublePoint)c.elementAt(i)).x - dw/2.00);
	        nextY = (int)Math.rint(((DoublePoint)c.elementAt(i)).y - dh/2.00);
	        if (nextX != prevX || nextY != prevY) {
	            rec[++j] = new Rectangle(nextX, nextY, (int)dw, (int)dh);
	            prevX = nextX;
	            prevY = nextY;
	        }
	    }
	    System.arraycopy(rec, 0, rectangles = new Rectangle[j+1], 0, j+1);
	    increment = rectangles.length /(int)num_m_rect;
		
		//create arrays to hold the characters of the message & their x positions....
		if(message.length() > (int)num_m_rect) message = message.substring(0, (int)num_m_rect);
		
		message_beg = clockwise ? 0 : (message.length() * increment) - increment;   
        
        m = new String[message.length()];
        mX = new int[m.length];
        
        int maxascent = fm.getMaxAscent();
        cH = (fontSpaceDim.height - (maxascent + fm.getMaxDescent() + fm.getLeading()))/2 + maxascent;
        char[] mC = message.toCharArray();
        for (int i = 0; i < message.length(); i++) {
            m[i] = new String(mC,i,1);
            mX[i]= (fontSpaceDim.width - fm.charWidth(message.charAt(i)))/2;
        }
        
        //color effects for characters....
        if (!p[PARAM_charEffects][1].equals("")) buildColorArray();

        //special handling for images....
        if(!imageName.equals("")) {
            tracker = new MediaTracker(this);
            image = this.getImage(this.getDocumentBase(), imageName);
            tracker.addImage(image, 0);
            loaded = false;
        }
        else {
            image = null;
            loaded = false;
        }
        if(image != null) {
            if (!loaded) {
                try {
                    tracker.waitForID(0);
                }
                catch (InterruptedException e) {;}
                if(tracker.isErrorID(0)) {
                    this.showStatus("Error loading image " + imageName + ", quitting.");
                    return;
                }
                else loaded = true;
                while((imageWidth = image.getWidth(this)) < 0)
                    try {Thread.sleep(100);}catch(InterruptedException e) {}
                while((imageHeight = image.getHeight(this)) < 0)
                    try {Thread.sleep(100);}catch(InterruptedException e) {}
                
                if (center ) {
                    centered_XCorner = appletSize.width/2 - imageWidth/2;
                    if (centered_XCorner < 0) centered_XCorner = 0;
                    centered_YCorner = appletSize.height/2 - imageHeight/2;
                    if (centered_YCorner < 0) centered_YCorner = 0;
                }
            }
        }
    }
    
    private void setBufferGraphics(){
		bufferGraphics = buffer.getGraphics();
        fm = bufferGraphics.getFontMetrics(new Font(fontName, fontStyle, fontSize));
        bufferGraphics.setFont(fm.getFont());
        bufferGraphics.setColor(background);
        bufferGraphics.fillRect(0,0,appletSize.width,appletSize.height);
    }
    
    public void destroy() {}
	
	public void update(Graphics g){ 
	    if (image != null) {
   	        if (scale) {
	            bufferGraphics.drawImage(image,
	                    0,0,appletSize.width,appletSize.height,0,0,100,100,this);
	        }
	        else if (tile) {
	            for(int x = 0; x < appletSize.width; x += imageWidth)
	                for(int y = 0; y < appletSize.height; y += imageHeight)
	                    bufferGraphics.drawImage(image, x, y, this);
	        }
	        else if (center) {
    	        bufferGraphics.setColor(background);
    	        bufferGraphics.fillRect(0,0,appletSize.width,appletSize.height);
	            
	            bufferGraphics.drawImage(image, centered_XCorner, centered_YCorner, this);
	        }
	        else {
    	        bufferGraphics.setColor(background);
    	        bufferGraphics.fillRect(0,0,appletSize.width,appletSize.height);
	            
	            bufferGraphics.drawImage(image, 0, 0, this);
	        }
	    }
	    else {
    	    bufferGraphics.setColor(background);
    	    bufferGraphics.fillRect(0,0,appletSize.width,appletSize.height);
	    }
    	    
	    if (!random || !fixed) bufferGraphics.setColor(foreground);
        
       	if (clockwise) {
    	    for(int i = 0, j = message_beg; i < m.length; i++, j += increment) {
    	        int k = ( j >= rectangles.length) ? (j - rectangles.length) : j ;
    	        if (random) {
    	            bufferGraphics.setColor(randColors[randCount]);
    	            if (randCount < randColors.length - 1) randCount++; else randCount = 0;
    	        }
    	        if (fixed) bufferGraphics.setColor(randColors[i]);
    	        
                bufferGraphics.drawString(m[i], 
                            rectangles[k].x + mX[i],
                            rectangles[k].y + cH); 
    	    }
    	    message_beg = (message_beg + 1 >= rectangles.length) ? 0 : message_beg + 1;
    	}
    	else {
    	    for(int i = ((message.length()) - 1), j = message_beg; 
    	                 i > -1; i--, j -= increment) {
    	        int k = ( j < 0) ? (j + rectangles.length) : j ;
    	        if (random) {
    	            bufferGraphics.setColor(randColors[randCount]);
    	            if (randCount < randColors.length - 1) randCount++; else randCount = 0;
    	        }
    	        if (fixed) bufferGraphics.setColor(randColors[i]);
    	        
                bufferGraphics.drawString(m[i], 
                            rectangles[k].x + mX[i],
                            rectangles[k].y + cH); 
    	    }
    	    message_beg = (message_beg - 1 < 0) ? rectangles.length - 1: message_beg - 1;
    	}
	    paint(g); 
	}
	
	public void paint(Graphics g){
	    g.drawImage(buffer, 0, 0, this);
	    g.dispose();
	}
	
	public void start(){
	    if(animator == null) {
	        Thread current = Thread.currentThread();
	        current.setPriority(current.MIN_PRIORITY + 1);
	        animator = new Thread(this);
			animator.setPriority(animator.MIN_PRIORITY);
	        animator.start();
	    }
	}
	
	public void stop() {
	    if(animator != null) {
	        animator.stop();
	        animator = null;
	    }
	}
	
	public void run(){
	    while(animator != null) {
	        repaint();
	        try {Thread.sleep(pause);} catch (InterruptedException e) { ; }
	    }
	}
	
	public boolean mouseDown(java.awt.Event evt, int x, int y) {
	    try {
	        if(url != null)
	            getAppletContext().showDocument(url);
	    }
		catch(Exception e) {}
		finally {return true;}
	}

	public boolean mouseEnter(java.awt.Event evt, int x, int y) {
        if (p[PARAM_url][1] != null &&
           !p[PARAM_url][1].equals("")) {
            showStatus(p[PARAM_url][1]);
            if (browserFrame != null)
                browserFrame.setCursor(Frame.HAND_CURSOR);
        }
		return true;
	}
	
	public boolean mouseExit(java.awt.Event evt, int x, int y) {
        showStatus("");
		return true;
	}
	
	private void buildColorArray(){
	    double r;
        Color[] colors = {Color.black, Color.blue, Color.cyan, Color.green,
                          Color.lightGray, Color.magenta, Color.orange, Color.pink,
                          Color.red, Color.white, Color.yellow};
	    
	    randCount = 0;
	    boolean seed, pastel;
	    seed = pastel = random = fixed = false;
	    Random ran;
	    
	    String effect = p[PARAM_charEffects][1].toUpperCase();
	    
	    if (effect.equals("RANDOMPASTELS")) {
	        random = true;
	        pastel = true;
	    }
	    else if (effect.equals("RANDOMCOLORS")) {
	        random = true;
	    }
    	else if (effect.equals("FIXEDPASTELS")) {
	        fixed = true;
	        pastel = true;
	    }
    	else if (effect.equals("FIXEDCOLORS")) {
	        fixed = true;
	    }
	        
		randColors = new Color[random ? 100 : m.length];
		if (seed = (parm_seed == -1000000) ? false : true ) ran = new Random((long)parm_seed);
		else ran = null;
		
		if (pastel) 
		    for(int i = 0; i < randColors.length; i++) {
		        r = (seed ? ran.nextDouble() : Math.random());
		        randColors[i] = 
		            Color.getHSBColor((float) r,
		                              (float) 0.3,
		                              (float) 0.99);
		    }
		else 
		    for(int i = 0; i < randColors.length; i++)
		        do {
		        r = seed ? ran.nextDouble() : Math.random(); 
		        randColors[i] = colors[((int) Math.ceil(r * 11))- 1];
		        } while (randColors[i] == background);
	}
	
    private void getParameters(){
        
        String param;
        
        for(int i = 0; i < p.length; i++) {
            param = getParameter(p[i][0]);
            if (param != null) 
                p[i][1] = param;
        }
        
        message = p[PARAM_message][1];
        imageName = p[PARAM_image][1];
        fontName = p[PARAM_fontName][1];
        
        String[] fonts = Toolkit.getDefaultToolkit().getFontList();
        for(int i = 0; i < fonts.length; i++) {
            if (p[PARAM_fontName][1].equalsIgnoreCase(fonts[i])){
                fontName = fonts[i];
                break;
            }
        }
        
        if (p[PARAM_fontStyle][1].equalsIgnoreCase("PLAIN")){
            fontStyle = Font.PLAIN;
        }
        else if (p[PARAM_fontStyle][1].equalsIgnoreCase("BOLD")){
            fontStyle = Font.BOLD;
        }
        else if (p[PARAM_fontStyle][1].equalsIgnoreCase("ITALIC")){
            fontStyle = Font.ITALIC;
        }
        else
            fontStyle = Font.PLAIN;
        
        fontSize = parse(p[PARAM_fontSize][1], 12);
        pause = parse(p[PARAM_pause][1], 100);
        rotationfactor = ((double) parse(p[PARAM_rotationfactor][1], 100)) * 0.01;
        parm_radius = parse(p[PARAM_radius][1], -1);
        fontPadding = parse(p[PARAM_fontPadding][1], 0);
        parm_x = parse(p[PARAM_x][1], -1000000);
        parm_y = parse(p[PARAM_y][1], -1000000);
        parm_seed = parse(p[PARAM_seed][1], -1000000);
        
        clockwise = p[PARAM_direction][1].equalsIgnoreCase("CLOCKWISE") ? 
                  true : false ;
        scale = p[PARAM_imageAttribute][1].equalsIgnoreCase("SCALE") ? 
                  true : false ;
        tile = p[PARAM_imageAttribute][1].equalsIgnoreCase("TILE") ? 
                  true : false ;
        center = p[PARAM_imageAttribute][1].equalsIgnoreCase("CENTER") ? 
                  true : false ;
     
        background = getColor(p[PARAM_background][1], Color.black);
        foreground = getColor(p[PARAM_foreground][1], Color.white);


		try {url = new URL(p[PARAM_url][1]);}
		catch(MalformedURLException e){url = null;}
    }
	
	private int parse(String s, int exp) {
        try {return Integer.parseInt(s);}
        catch(NumberFormatException e){return exp;}
	}
	
	private Color getColor(String s, Color exp) {
	    try {
    	    return new Color(HexToInt(s.substring(0,2)),
    		                 HexToInt(s.substring(2,4)),
    					     HexToInt(s.substring(4)));
        } 
        catch(Exception e){return exp;}
	}
	
	public int HexToInt(String value) {
		int answer = 0;

		if(value.substring(0,1).equalsIgnoreCase("a"))
			answer = 160;
		else if(value.substring(0,1).equalsIgnoreCase("b"))
			answer = 176;
		else if(value.substring(0,1).equalsIgnoreCase("c"))
			answer = 192;
		else if(value.substring(0,1).equalsIgnoreCase("d"))
			answer = 208;
		else if(value.substring(0,1).equalsIgnoreCase("e"))
			answer = 224;
		else if(value.substring(0,1).equalsIgnoreCase("f"))
			answer = 240;
		else
			answer = Integer.valueOf(value.substring(0,1)).intValue() * 16;

		if(value.substring(1).equalsIgnoreCase("a"))
			answer += 10;
		else if(value.substring(1).equalsIgnoreCase("b"))
			answer += 11;
		else if(value.substring(1).equalsIgnoreCase("c"))
			answer += 12;
		else if(value.substring(1).equalsIgnoreCase("d"))
			answer += 13;

		else if(value.substring(1).equalsIgnoreCase("e"))
			answer += 14;
		else if(value.substring(1).equalsIgnoreCase("f"))
			answer += 15;
		else
			answer += Integer.valueOf(value.substring(1)).intValue();
		return answer;
	}
}

abstract class Comparer {
    public abstract int compare(Object a, Object b);
}

class XComparer extends Comparer {
    public void XComparer() {
    }
    
    public int compare(Object a, Object b) {
        DoublePoint p1 = (DoublePoint) a;
        DoublePoint p2 = (DoublePoint) b;

        if(p1.x > p2.x) return +1;
        if(p1.x == p2.x) return 0;
        return -1;
    }
}

class YComparer extends Comparer {
    public void YComparer() {
    }
    
    public int compare(Object a, Object b) {
        DoublePoint p1 = (DoublePoint) a;
        DoublePoint p2 = (DoublePoint) b;

        if(p1.y > p2.y) return +1;
        if(p1.y == p2.y) return 0;
        return -1;
    }
}

class CirclePoints {
    
    private static void sort(Object[] a, Object[] b,
                            int from, int to,
                            boolean up, Comparer c)
    {
        // If there is nothing to sort, return
        if ((a == null) || (a.length < 2)) return;

        int i = from, j = to;
        Object center = a[(from + to) / 2];
        do {
            if (up) {  // an ascending sort
                while((i < to) && (c.compare(center, a[i]) > 0)) i++;
                while((j > from) && (c.compare(center, a[j]) < 0)) j--;
        } else {   // a descending sort
                while((i < to) && (c.compare(center, a[i]) < 0)) i++;
                while((j > from) && (c.compare(center, a[j]) > 0)) j--;
        }
        if (i < j) {
            Object tmp = a[i];  a[i] = a[j];  a[j] = tmp;          // swap elements
            if (b != null) { tmp = b[i]; b[i] = b[j]; b[j] = tmp; }// swap b, too
        }
        if (i <= j) { i++; j--; }
        } while(i <= j);
        if (from < j) sort(a, b, from, j, up, c); // recursively sort the rest
        if (i < to) sort(a, b, i, to, up, c);
    }

	public static Vector circle(int xCenter, int yCenter, double radius, int spacing) {
	    double xC = (double) xCenter;
	    double yC = (double) yCenter;
	    double r = radius;
	    double s = (double) spacing;
	    
		Vector c = new Vector();
		Vector octant[] = new Vector[8];
		for(int i = 0; i < octant.length; i++) {
		    octant[i] = new Vector();
		}

	    /*   1 2
	        0   3         (octants 0 - 7)
            7   4
	         6 5
	    */

		double x = 0;
		double y = r;
		double p = s - r;

		//plot first set of points
		plotPoints(octant, xC, yC, x, y);

		while (x < y) {
			x += spacing;
			if(p < 0) p += 2.00 * x + s;
			else {
				y -= s;
				p += 2.00 * (x - y) + s;
			}
			plotPoints(octant, xC, yC, x, y);
		}


		//sort octants, separately in ascending/descending order
		octant = sort_by_x(octant);

		for (int i = 0; i < octant.length; i++)
		    for(int j = 0; j < octant[i].size(); j++) 
		        if (!c.contains(octant[i].elementAt(j))) 
		            c.addElement(octant[i].elementAt(j));
		return c;
	}

	private static Object[] sort_by_y(Object[] points, int from, int to, int octant) {
	    boolean ascending = (octant > 1 && octant < 6) ? true : false;

	    sort(points, null, from, to, ascending, new YComparer()); 
	    return points;
	}

	private static Vector[] sort_by_x(Vector[] vectorArray) {
		for (int i = 0; i < 8; i++) {

		    //first, copy the vector, object by object, into
		    // an array of objects
	        Object[] points = new DoublePoint[vectorArray[i].size()];

	        for(int j = 0; j < points.length; j++)
	            points[j] = vectorArray[i].elementAt(j);

		    //second, sort each octant along the x-axis
	        boolean ascending_x = i > 3 ? false : true;

	        sort(points, null, 0, points.length - 1, ascending_x,
	            new XComparer());

		    //third, sort each octant along the y-axis
		    boolean end_array = false;
		    int hold_pos = 0;
		    double hold_x = ((DoublePoint)points[0]).x;
            int k = 0;
		    while(!end_array){
	            for(k = hold_pos;;k++) {
	                if (k == points.length) {
	                    end_array = true;
	                    break;
	                }
	                if (((DoublePoint)points[k]).x != hold_x) {
	                    points = sort_by_y(points, hold_pos, k - 1, i);
	                    hold_x = ((DoublePoint)points[k]).x;
	                    hold_pos = k;
	                    break;
	                }
	            }
	        //sort the last x-grouping
	        points = sort_by_y(points, hold_pos, k - 1, i);
	        }

		    //finally, turn the reordered array of objects back into a vector
	        Vector v = new Vector();
	        for(int j = 0; j < points.length; j++) {
	            v.addElement(points[j]);
	        }
	        vectorArray[i] = v;
		}
		return vectorArray;
	}

	private static void plotPoints(Vector[] v, double xC, double yC, double x, double y){
	    v[3].addElement(new DoublePoint(xC + y, yC - x));
		v[0].addElement(new DoublePoint(xC - y, yC - x));
		v[4].addElement(new DoublePoint(xC + y, yC + x));
		v[7].addElement(new DoublePoint(xC - y, yC + x));
		v[2].addElement(new DoublePoint(xC + x, yC - y));
		v[1].addElement(new DoublePoint(xC - x, yC - y));
		v[5].addElement(new DoublePoint(xC + x, yC + y));
		v[6].addElement(new DoublePoint(xC - x, yC + y));
	}
}

class DoublePoint {
    public double x;

    public double y;

    public DoublePoint() {
	this(0.0, 0.0);
    }

    public DoublePoint(DoublePoint p) {
	this(p.x, p.y);
    }

    public DoublePoint(double x, double y) {
	this.x = x;
	this.y = y;
    }

    public DoublePoint getLocation() {
	return new DoublePoint(x, y);
    }

    public void setLocation(DoublePoint p) {
	setLocation(p.x, p.y);
    }

    public void setLocation(double x, double y) {
	move(x, y);
    }

    public void move(double x, double y) {
	this.x = x;
	this.y = y;
    }

    public void translate(double x, double y) {
	this.x += x;
	this.y += y;
    }

    public boolean equals(Object obj) {
	if (obj instanceof DoublePoint) {
	    DoublePoint pt = (DoublePoint)obj;
	    return (x == pt.x) && (y == pt.y);
	}
	return false;
    }

    public String toString() {
	return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}

