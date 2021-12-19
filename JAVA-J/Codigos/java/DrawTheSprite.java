/*########################################################################
# Developed by: Walter Hipp,   E-Mail: Walter.Hipp@hipp-online.de	 #
#									 #
#			       done at: 03-14-2000			 #
#									 #
#									 #
# You can go ahead and change this program free of charge as long as you #
# keep intact this header. Also by using this program you agree to keep  #
# the author out of any trouble that may occur during the use of this    #
# program. In other words, if one day you were modifying this program    #
# and all of a sudden your computer blows up, it isn't my fault. 	 #
#									 #
# Redistributing/selling the code of this program without my knowledge   #
# is a really big no no!						 #
########################################################################*/


import java.lang.Thread;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;

public class DrawTheSprite extends SpriteEngine implements Runnable
{
	private Thread t;
	private int s_x, s_y, s2_x, s2_y, s_ydir, s2_ydir, s_xdir, s2_xdir;
        private int speed;  // sets the delay time between 2 pictures of tht same sprite, after
                            // this value, the next sprite picture will be displayed.
	
	public void init()
	{
		
      		try {					        // this parameters we will recieve
             		s_x = (Integer.parseInt(getParameter("SpX"),10));       // Sprite 1 x pos.
      		
             		s_y = (Integer.parseInt(getParameter("SpY"),10));       // Sprite 1 y pos.

                        s_xdir = (Integer.parseInt(getParameter("SpXdir"),10)); // Sprite 1 moving x direction
      		
             		s_ydir = (Integer.parseInt(getParameter("SpYdir"),10)); // Sprite 1 moving y direction
      		
             		s2_x = (Integer.parseInt(getParameter("Sp2X"),10));     // sprite 2 see sprite 1
      		
             		s2_y = (Integer.parseInt(getParameter("Sp2Y"),10));

			s2_xdir = (Integer.parseInt(getParameter("Sp2Xdir"),10));
      		
             		s2_ydir = (Integer.parseInt(getParameter("Sp2Ydir"),10));
      		
             		speed = (Integer.parseInt(getParameter("Speed"),10));  // delay time to 
								//display the next sprite picture
      		} catch (Exception E) { }
		super.init();
		t = new Thread(this);
		t.start();
	}
	
	public void run()
	{
		init = true;
		Sprite s = new Sprite(this, "./img/frog1.gif");
		Sprite s2 = new Sprite(this, "./img/frog2.gif");  // this is the only change to DrawTheSprite 2
                //Sprite s2 = new Sprite(this, "img/wheel.gif", 3); 
		setBackground("img/Atlantis.gif");
		s.speed = speed;
                s2.speed = speed;
		
		addSprite(s);
		addSprite(s2);
		s.xPos = s_x;
		s.yPos = s_y;
		s2.xPos = s2_x;
		s2.yPos = s2_y;
		
		while (! done)
		{
			s.xPos += s_xdir;	// greater values will increase the horizontal speed
                        s.yPos -= s_ydir;	// greater values will increase the vertikal speed
                        s2.yPos -= s2_ydir;
			s2.xPos -= s2_xdir;
			if ((s.xPos > screenWidth) || (s.xPos < 0) || 
                            (s.yPos > screenHeight) || (s.yPos < 0))
			{
				s.xPos = s_x;
				s.yPos = s_y;
			}
                        if ((s2.xPos > screenWidth) || (s2.xPos < 0) || 
                            (s2.yPos > screenHeight) || (s2.yPos < 0))
			{
				s2.xPos = s2_x;
				s2.yPos = s2_y;
			}
			advance();
			blit();
		}
	}

} // class DrawTheSprite