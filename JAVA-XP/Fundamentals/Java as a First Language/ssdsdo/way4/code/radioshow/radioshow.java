// FIRST ITERATION!!!
//
// Filename RadioShow.java.
// Providing a demonstration of the initial Interactive Menu.
//
// Written for SDO 1999/2000 waypoint 4
// Fintan Culwin, v 0.1, jan 2000

package radioshow; 

import menus.BasicMenu;
import radioslot.*;
import java.util.Vector;

public class RadioShow extends Object  { 

   public static void main( String args[]) { 

   final char LIST_SLOTS   = 'A';
   final char EXIT_CHOICE  = 'B';

   final String menuOptions[] =  { 
                          "List Radio Slot Items",                             
                          "Exit"
                           };

   final BasicMenu mainMenu = new BasicMenu( 
                                      "\n\tRadio Show Main Menu",
                                       menuOptions,
                                       "");
   char menuChoice = ' ';


   Vector theList = new Vector();
                             
      System.out.println( "\n\t\t Radio Show (First Iteration)");
      
      while ( menuChoice != EXIT_CHOICE) { 
         menuChoice = mainMenu.offerMenuAsChar();
         switch ( menuChoice) { 

         case LIST_SLOTS : 
               listTheSlots( theList);
               break;

         } // End switch.
      } // End while.

      System.out.println( "\n\nEnd of Radio Show (First Iteration)");    
   } // End main
   

   private static void listTheSlots( Vector listToShow) { 

      if ( listToShow.isEmpty()) { 
         System.out.println( "\n\tThe list is empty!");
      } else { 
         System.out.println( "\n\tThe list is NOT empty!");
      } // End showThePumps.
   } // End listTheSlots.

} // End RadioShow.
