/*########################################################################
# Developed by: Walter Hipp, E-Mail: Walter.Hipp@hipp-online.de		 #
#									 #
# This java code demonstrates how to get windows within applets.	 #
# As you see, you can use menus, so you can do many things. Now give	 #
# this code the functianality you want to.				 #
#									 #
# appletsWindow.java is the class that keeps care of all window actions	 #
# and is needed by the main class produced with WindowApplet.java	 #
#									 #
#			      done at: 04-26-2000			 #
#									 #
#									 #
# You can go ahead and change this program free of charge as long as you #
# keep intact this header. Also by using this program you agree to keep  #
# the author out of any trouble that may occur during the use of this    #
# program. In other words, if one day you were modifying this program    #
# and all of a sudden your computer blows up, it isn't my fault. 	 #
#									 #
# Redistributing/selling the code of this program without our knowledge  #
# is a really big no no!						 #
########################################################################*/



import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class WindowApplet extends Applet implements ActionListener, Runnable
{
    
    Thread mainThread;
    String         m_strWindTitle = "Hi, here is the appletsWindow !";
    appletsWindow  m_window = null;
    Button         button1, button2;
    final String   BUTTON1 = "Show Window";
    final String   BUTTON2 = "Close Window";
    static boolean m_bBut1_sel = false, m_bBut2_sel = false;

    public void createWindow()
    {
       m_window = new appletsWindow();
       m_window.setTitle(m_strWindTitle);
       m_window.setSize(320, 200);
       m_window.m_txtArea.append("This is a window produced by an applet."
                          + System.getProperty("line.separator")
       			  + "This demo will show you, how you can create Windows by applets."
		 	  + System.getProperty("line.separator")
       			  + "If you want it, download the code and or source for free."
			  + System.getProperty("line.separator"));
       m_window.m_txtArea.append( "Due to security reasons the file Menu doesn' t work,"
                                 + System.getProperty("line.separator")
                                 + "if you are runnig this applet whithin a browser !"
                                 + System.getProperty("line.separator")
                                 + "Try the applet viewer from sun."
                                 + System.getProperty("line.separator"));
       m_window.m_txtArea.append(System.getProperty("line.separator") + 
                                 "Try to use the menus in the menu bar !"
                                 + System.getProperty("line.separator"));
       m_window.addMenuPanel(4);
       m_window.setMenuString(1, "File");
       m_window.setMenuString(2, "Second");
       m_window.setMenuString(3, "Third");
       m_window.setMenuString(4, "Fourth");
       String menuItem[] = { "Save", "Open", "", "" };
       m_window.setMenuItem(1, 2, menuItem);
       menuItem[0] = "Second 1"; menuItem[1] = "Second 2";
       menuItem[2] = "Second 3"; menuItem[3] = "Second 4";
       m_window.setMenuItem(2, 4, menuItem);
       menuItem[0] = "Third 1"; menuItem[1] = "Third 2";
       menuItem[2] = "Third 3"; menuItem[3] = "Third 4";
       m_window.setMenuItem(3, 4, menuItem);
       menuItem[0] = "Fourth 1"; menuItem[1] = "Fourth 2";
       menuItem[2] = "Fourth 3"; menuItem[3] = "Fourth 4";
       m_window.setMenuItem(4, 4, menuItem);
    } // end of window defination

    public void init() 
    {
       add (button1 = new Button(BUTTON1));
       add (button2 = new Button(BUTTON2));
       button1.addActionListener(this);
       button2.addActionListener(this);
    }

    public void destroy() 
    {
       button1.removeActionListener(this);
       button2.removeActionListener(this);
       if (m_window != null)
         if ( m_window.isVisible()) { m_window.removeAll(); m_window.dispose(); }
    }

    // Paint is the main part of the program
    public void paint(Graphics g)
    {
    }

    public void start() 
    {
        mainThread = new Thread(this);
        mainThread.start();
    }

    public void stop() 
    {
        mainThread = null;
    }

    public void run() 
    {
        Thread thisThread = Thread.currentThread();
        while (mainThread == thisThread) 
        {
            try {
                  Thread.currentThread().sleep(100);
            } catch (InterruptedException e) { }
            repaint();
        }
    }

    public void update(Graphics g) 
    {
        paint(g);
    }

    public void actionPerformed(ActionEvent ae)
    {
       Object source = ae.getSource();

       if (source == button1)
       {
           m_bBut1_sel = !m_bBut1_sel;
           if (m_window != null && m_window.m_bClosed == true) m_window = null;
           if (m_window == null) createWindow();
           m_window.show();
       }
       if (source == button2)
       {
           m_bBut2_sel = !m_bBut2_sel;
           if (m_window != null)
             if (m_window.isVisible()) m_window.dispose();
           m_window = null;
           System.gc();
       }
    }

    public String getAppletInfo() 
    {
        return "Title: WindowApplet \nAuthor: Walter Hipp, 2000 \n"
               + "Just a Demo how to do Windows whithin an applet.";
    }
  
    public String[][] getParameterInfo() 
    {
        String[][] info = 
        {
            {"no", "parameters", "required."},
        };
        return info;
    }
} // class windowApplet
