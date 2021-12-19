// Filename PlayTimeTerminalInputDemonstration.java.
// Input details of a PlayTime from the terminal.
//
// Produced for ssd/sdo 99/00 
// Fintan Culwin, v0.1, January 2000.

package playtime;


public class PlayTimeTerminalInputDemonstration extends Object  { 

   public static void main( String argv[]) {

   PlayTime toDemo = null;
   
      System.out.println( "\t Play Time Terminal Input Demonstration \n\n");
            
      System.out.println( "Demonstrating Input =Routine\n");
      toDemo = PlayTimeTerminalInput.readPlayTime( 
                             "Please enter a play time ");
      System.out.println( "\n\nThank you, you input " + toDemo);     

      System.out.println( "\n\nEnd of Play Time Terminal Input Demonstration");       
   } // End main.
} // End SimpleInputDemonstration.
