// Filename DangerousWriter.java
// Providing class to read the dangerous attributes. 
//
// Written for JFL book Chapter 8 see text.
// Fintan Culwin, v0.1, January 1997

import Dangerous;

public class DangerousWriter extends Thread { 

Dangerous writeThis;

   public DangerousWriter( Dangerous toWrite ) { 
       writeThis = toWrite; 
   } // End DangerousWriter Constructor.


   public void run() {
   
   int index = 0;
   
      while ( true ) { 
         writeThis.update( index++);
      } // End while.   
   } // End run.

} // End DangerousWriter. 
