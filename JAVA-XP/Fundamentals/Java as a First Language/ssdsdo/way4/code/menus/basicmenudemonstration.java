// Filename BasicMenuDemonstration.java.
// Providing a demonstration of the initial Interactive Menu.
//
// Written for JFL book Chapter 7 see text.
// Fintan Culwin, v0.1, January 1997
//
// This version January 2000.

package menus;

public class BasicMenuDemonstration { 


public static void main( String argv[]) { 

   String demoOptions[] =  { "This is the first option",
                             "This is the second option",
                             "This is the third option"};
                             

   char demoCharChoice = ' ';
   int  demoIntChoice  = 0;                             

   BasicMenu demoMenu = new BasicMenu( "This is the title",
                                       demoOptions,
                                       "This is the prompt");


      System.out.println( "\n\t\t Basic Menu demonstration ");
      
      System.out.println( "\n\n Testing the offerMenuAsChar() action ... \n\n");
      demoCharChoice = demoMenu.offerMenuAsChar();
      System.out.println( "\nYou chose " + demoCharChoice  + " from the menu.");

      System.out.println( "\n\n Testing the offerMenuAsInt() action ... \n\n");
      demoIntChoice = demoMenu.offerMenuAsInt();
      System.out.println( "\nYou chose " + demoIntChoice  + " from the menu.");

   } // End main
   
} // End  BasicMenuDemonstration
