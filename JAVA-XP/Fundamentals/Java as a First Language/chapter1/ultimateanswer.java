// Filename UltimateAnswer.java.
// Initial Java client program written for 
// the JFL book chapter 1 - see text.
//
// Fintan Culwin, V0.1, August 1997.

import TheAnswer;

public class UltimateAnswer { 

   public static void main( String args[] ){ 
   
   TheAnswer anAnswerObject = new TheAnswer();

      System.out.println();   
      System.out.println( "The answer to life,");
      System.out.print( "the Universe and everything is ");
      System.out.print( anAnswerObject.theAnswerIs());
      System.out.println( ".");
   } // End main.

} // End UltimateAnswer.
