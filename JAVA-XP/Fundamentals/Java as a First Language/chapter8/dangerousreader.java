// Filename DangerousReader.java
// Providing class to read the dangerous attributes. 
//
// Written for JFL book Chapter 8 see text.
// Fintan Culwin, v0.1, January 1997

import Dangerous;

public class DangerousReader extends Thread { 

Dangerous  readThis;

   public DangerousReader( Dangerous toRead ) { 
       readThis = toRead; 
   } // End RoomFiller Constructor;


   public void run() {
   
   int firstAttribute;
   int secondAttribute;
   
       while ( true ) { 
          firstAttribute  = readThis.getAnAttribute(); 
          secondAttribute = readThis.getAnotherAttribute();
          if ( firstAttribute != secondAttribute) { 
              System.out.println( "Danger proved ... " + 
                         firstAttribute  + " and "     + 
                         secondAttribute + ".");
          } // End if
       } // End while.   
   } // End run.

} // End DangerousReader.
