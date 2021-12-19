// Filename ValidatedInputDemonstration.java.
// Demonstration harness for the ValidatedInput class.
//
// Produced for JFL book Chapter 9.
// Fintan Culwin, v0.1, January 1997.
// This version January 1999.


import ValidatedInput;

public class ValidatedInputDemonstration { 


   public static void main( String argv[]) {

   long         demoLong;
   int          demoInt;
   double       demoDouble;
   float        demoFloat;

   
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

      System.out.println( "\n\nInputting a double value between -100.0 and +100.0\n");
      demoDouble = ValidatedInput.readDouble( 
                             "Please enter your value : ",
                             -100.0, +100.0);
      System.out.println( "Thank you, you input " + demoDouble);  


      System.out.println( "\n\nInputting a float value\n");
      demoFloat = (float) ValidatedInput.readDouble( 
                             "Please enter your value : ",
                             -Float.MAX_VALUE, Float.MAX_VALUE);
      System.out.println( "Thank you, you input " + demoFloat);  

      System.out.println( "\n\nEnd of Validated Input Demonstration");
       
   } // End main.


} // End SimpleInputDemonstration.
