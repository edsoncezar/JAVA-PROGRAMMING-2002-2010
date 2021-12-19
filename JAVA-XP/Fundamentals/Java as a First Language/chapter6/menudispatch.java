// Filename MenuDispatch.java.
// Providing a demonstration of the initial Interactive Menu,
// showing its use with manifest values.
//
// Written for JFL book Chapter 6 see text.
// Fintan Culwin, v0.1, January 1997

import Menus.BasicMenu;

public class MenuDispatch { 

static final int FIRSTOPTION  = 1;
static final int SECONDOPTION = 2;
static final int THIRDOPTION  = 3;

public static void main( String argv[]) { 
   
   String demoOptions[] =  { "This is the first option",
                             "This is the second option",
                             "This is the third option"};
                             
   int  demoIntChoice;                             

   BasicMenu demoMenu = new BasicMenu( "",
                                       demoOptions,
                                       "");


      System.out.println( "\n\t\t Basic Menu demonstration ");
      
      System.out.println( "\n\n Testing the offerMenuAsInt() action ... \n\n");
      demoIntChoice = demoMenu.offerMenuAsInt();

      switch ( demoIntChoice ) { 
         case FIRSTOPTION:
            System.out.println( "\n\n You chose the first option.");
            break;
         case SECONDOPTION:
            System.out.println( "\n\n You chose the second option.");
            break;         
         case THIRDOPTION:
            System.out.println( "\n\n You chose the third option.");
      } // end switch.                  
   } // End main
   
} // End  MenuDispatch.
