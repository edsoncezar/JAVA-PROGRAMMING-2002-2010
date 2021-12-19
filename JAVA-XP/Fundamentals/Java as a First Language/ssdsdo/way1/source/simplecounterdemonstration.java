// filename : SimpleCounterDemonstration.java
// purpose  : Demonstrates the creation of an instance of an
//            object of class SimpleCounter
// P. Campbell v0.1 September 2001
//-----------------------------------------

public class SimpleCounterDemonstration extends Object{

   public static void main (String[] args){

         SimpleCounter count = new SimpleCounter(21);

         System.out.print( "First  value  : ");
         System.out.println( count.getValue());
         count.click();
         System.out.print( "Second value  : ");
         System.out.println( count.getValue());
         count.click();
         count.click();
         System.out.print( "Third  value  : ");
         System.out.println( count.getValue());
     } // End of main
} // end of class SimpleCounterDemonstration
