// Filename playtime\BasicTimeDemonstration.java
//
// Initial demo of the basicTime class 
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

package playtime;

public class BasicTimeDemonstration extends Object { 

   public static void main( String[] args) { 

   BasicTime demoTime = null;
  

      System.out.println( "\n\nBasic Time Demonstration\n\n");

      System.out.println( "This demonstration will construct an instance" + 
                          "\nof BasicTime representing 2 min 15.9 seconds"); 

      System.out.println( "\nConstructing ... \n");
      demoTime = new BasicTime( 1359);

      System.out.println( "\n... constructed showing ... " + 
                           demoTime);
 
      System.out.println( "\n??Are any more demonstrations needed???");

   } // End main. 

} // End class BasicTimeDemonstration.


