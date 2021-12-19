/*########################################################################
# Developed by: Walter Hipp, E-Mail: Walter.Hipp@hipp-online.de		 #
#									 #
# This class will produce the window and all the stuff needed 	 	 #
# by a window (events for this window, drawing it, removing it, 	 #
# adding menu bars and menus and last but not least draw the things you	 #
# want to show in a window).						 #
#									 #
# needs the main class produced with WindowApplet.java			 #
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

import java.awt.*;
import java.awt.event.*;

public class appletsWindow extends Frame implements WindowListener, ActionListener
{
   
   MenuBar  m_menuBar = null;
   Menu     m_menu[];
   int      m_menuMax = 0;
   TextArea m_txtArea;
   private  Font m_sysFnt, m_newFnt;
   boolean  m_bClosed;

   public appletsWindow()
   {   
        addWindowListener(this);
        m_txtArea = new TextArea(5,40);
        m_txtArea.setEditable(false);
        add("Center", m_txtArea);
        m_sysFnt = getFont();
        m_bClosed = false;
   }

   public void addMenuPanel(int how_much)
   {
       m_menuMax = how_much;
       m_menuBar = new MenuBar();
       m_menu = new Menu[how_much];
       setMenuBar(m_menuBar);
       for (int i = 0; i <= how_much - 1; i++)
       {
          Menu m = new Menu();
          m_menu[i] = m;
          m_menu[i].addActionListener(this);
       }
   }

   public void setMenuString(int menuNr, String str)
   {
      if (m_menuBar != null)
      {
         m_menu[menuNr - 1].setLabel(str);
         m_menuBar.add(m_menu[menuNr - 1]);
      }
   }

   public void setMenuItem(int menuNr, int how_much, String str[])
   {
      if (m_menuBar != null)
      {
         for (int i = 0; i <= how_much -1; i++)
         {
             MenuItem mItem = new MenuItem(str[i]);
             m_menu[menuNr - 1].add(mItem);
         }
      }
   }

   public void destroy() 
   {
       removeWindowListener(this);
       if (m_menuMax > 0)
       {
           for (int i = 0; i <= m_menuMax -1; i ++)
             m_menu[i].removeActionListener(this);
       }
   }

   public void dispatchAction(String act, String act_from)
   {
      /* m_txtArea.append("Method dispatch Action act = " + act + "  actfrom = " + act_from
                       + System.getProperty("line.separator")); */
      if (act.compareTo("Open") == 0 && act_from.compareTo("File") == 0) onFileOpen();
      if (act.compareTo("Save") == 0 && act_from.compareTo("File") == 0) onFileSave();
   }

   public void actionPerformed(ActionEvent ae)
   {
     String act = ae.getActionCommand();
     String actFrom = ((MenuItem) ae.getSource()).getLabel();

     m_txtArea.append("You performed the Action    >" + act + "<"
                       + "    in Menu    >"  + actFrom + "<"
                       + System.getProperty("line.separator"));
     dispatchAction(act, actFrom);
   }

   public void useSystemFont()
   {
      setFont(m_sysFnt);
   }

   public void setwindowFont(String fnt_name, int fnt_w, int fnt_h)
   {
      m_newFnt = null;
      switch(fnt_w)
      {
         case 0:  m_newFnt = new Font(fnt_name, Font.PLAIN, fnt_h);
                  break;
         case 1:  m_newFnt = new Font(fnt_name, Font.BOLD, fnt_h);
                  break;
         case 2:  m_newFnt = new Font(fnt_name, Font.ITALIC, fnt_h);
                  break;
         case 10: m_newFnt = new Font(fnt_name, Font.PLAIN + Font.ITALIC, fnt_h);
                  break;
         case 11: m_newFnt = new Font(fnt_name, Font.BOLD + Font.ITALIC, fnt_h);
                  break;
      }
      if (m_newFnt != null) setFont(m_newFnt);
   }

   public void windowClosing(WindowEvent e)
   {
       dispose();
   }

   public void windowClosed(WindowEvent e)
   {
       m_bClosed = true;
   }

   public void windowActivated(WindowEvent e)
   {
   }

   public void windowDeactivated(WindowEvent e)
   {
   }

   public void windowIconified(WindowEvent e)
   {
       setIconImage(null);
   }

   public void windowDeiconified(WindowEvent e)
   {
   }

   public void windowOpened(WindowEvent e)
   {
   }

   public void onFileOpen()
   {  
      m_txtArea.append("Method onFileOpen"
                       + System.getProperty("line.separator"));
      FileDialog fDlg = new FileDialog(this, "File opening");
      fDlg.show();
   }

   public void onFileSave()
   {
      m_txtArea.append("Method onFileSave"
                       + System.getProperty("line.separator"));
      FileDialog fDlg = new FileDialog(this, "File saveing");
      fDlg.show();
   }

} // class appletsWindow
