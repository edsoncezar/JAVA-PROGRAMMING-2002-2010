// Filename RadioSlotTerminalInputDemonstration.java.
// Input details of a RadioSlot from the terminal.
//
// Produced for ssd/sdo 99/00 
// Fintan Culwin, v0.1, January 2000.

package radioslot;


public class RadioSlotTerminalInputDemonstration extends Object  { 

   public static void main( String argv[]) {

   RadioSlot toDemo = null;
   
      System.out.println( "\t Radio Slot Terminal Input Demonstration \n\n");
            
      System.out.println( "Demonstrating Input Routine\n");
      toDemo = RadioSlotTerminalInput.readRadioSlot( 
                             "Please enter a radio slot ");
      System.out.println( "\n\nThank you, you input " + toDemo);     

      System.out.println( "\n\nEnd of Radio Slot Terminal Input Demonstration");       
   } // End main.
} // End SimpleInputDemonstration.
