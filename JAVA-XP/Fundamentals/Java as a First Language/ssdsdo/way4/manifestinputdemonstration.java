// Filename ManifestInputDemonstration.java
// demonstration harness for ManifestInput class.
//
// Written for Waypoint 5, 97/8
// Fintan Culwin, V0.1, January 1998.

import ManifestInput;

public class ManifestInputDemonstration { 

private static final String colours[] = { "red",    "orange",
                                          "yellow", "green",
                                          "blue",   "indigo",
                                          "violet"};

   public static void main( String args[]) { 

   ManifestInput colourInput = new ManifestInput( colours);
   int           colourValue = 0; 

      System.out.println( "\n\t Manifest Input Demonstration \n");

      System.out.println( "Input object constructed to input the values \n" +
                          "red, orange, yellow, green, blue, indigo, violet\n");

      System.out.println( "Demonstrating getManifest() action");

      System.out.print( "\nPlease input a manifest value NOT IN the list ");
      try { 
         colourValue = colourInput.getManifest();
         System.out.println( "No exception thrown ... which is NOT correct!" + 
                             "\nValue returned is " + colourValue);
   
      } catch ( java.io.IOException exception) { 
         System.out.println( "Exception thrown ... which is correct!");
      } // End try/ catch.

      System.out.print( "\nPlease input a manifest value IN the list ");
      try { 
         colourValue = colourInput.getManifest();
         System.out.println( "No exception thrown ... which is correct!"+ 
                             "\nValue returned is " + colourValue);
      } catch ( java.io.IOException exception) { 
         System.out.println( "Exception thrown ... which is NOT correct!");
      } // End try/ catch.

      System.out.println( "\n\nDemonstrating readManifest() action");

      System.out.println( "\nPlease input a manifest value(s) NOT in the list " + 
                          "\nfollowed by a value in the list.\n");

      colourValue = colourInput.readManifest( "Please enter a colour of the rainbow ");

      System.out.println( "\nValue returned is " + colourValue);


      System.out.println( "\n\nEnd of Manifest Input Demonstration \n");
   } // End main.



} // End class ManifestInputDemonstration.
