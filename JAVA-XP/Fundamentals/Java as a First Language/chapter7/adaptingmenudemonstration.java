// Filename AdaptingMenuDemonstration.java.
// Providing a demonstration of the Adapting Menu.
//
// Written for JFL book Chapter 7 see text.
// Fintan Culwin, v0.1, January 1997.

import Menus.AdaptingMenu;

public class AdaptingMenuDemonstration { 


public static void main( String argv[]) { 

   String demoOptions[] =  { "This is the first option",
                             "This is the second option",
                             "This is the third option"};
                             

   int  demoIntChoice;  
   int  index;                           

   AdaptingMenu demoMenu = new AdaptingMenu( "Adapting Menu Demonstration",
                                             demoOptions,
                                             "");
      for ( index =1; index < 4; index++){ 
        demoMenu.enableMenuOption( index);
      } // End for.                                             


      System.out.println( "\n\t\t Adapting Menu demonstration ");
      
      System.out.println( "\n\n Testing the offerMenuAsInt() action ... \n\n");
      demoIntChoice = demoMenu.offerMenuAsInt();
      System.out.println( "\nYou chose " + demoIntChoice  + " from the menu.");

      System.out.println( "\n\t\t Disabling second option and offering again \n\n");
      
      demoMenu.disableMenuOption( 2);
      demoIntChoice = demoMenu.offerMenuAsInt();
      System.out.println( "\nYou chose " + demoIntChoice  + " from the menu.");      
   } // End main   
} // End  BasicMenuDemonstration
