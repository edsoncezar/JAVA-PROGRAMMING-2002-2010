// Filename ValidatedInputDemonstration.java.
// Demonstration harness for the ValidatedInput class.
//
// Produced for JFL book Chapter 9.
// Fintan Culwin, v0.1, January 1997.

import ValidatedInput;

public class ValidatedInputDemonstration { 


   public static void main( String argv[]) {

   long         demoLong;
   int          demoInt;

   
      System.out.println( "\t Validated Input Demonstration \n\n");
      
      
      System.out.println( "Inputting a long value between 200 and 300\n");
      demoLong = ValidatedInput.readLong( 
                             "Please enter your value : ",
                             200, 300);
      System.out.println( "Thank you, you input " + demoLong);  
   
      System.out.println( "\n\nInputting a positive int value \n");      
      demoInt = (int) ValidatedInput.readLong( 
                             "Please enter your value : ",
                             0, Integer.MAX_VALUE);                                
      System.out.println( "Thank you, you input " + demoInt);           
   } // End main.


} // End SimpleInputDemonstration.
