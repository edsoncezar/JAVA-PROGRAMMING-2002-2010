// Filename DangerousDemo.java.
// Providing a demonstration of the possibility of 
// an invalid update happening to a dangerous object.
//
// Written for JFL book Chapter 8 see text.
// Fintan Culwin, v0.1, January 1997.


public class DangerousDemo { 

   public static void main( String argv[]) { 

   Dangerous    dangerous = new Dangerous();
   DangerousWriter   aWriter   = new DangerousWriter(  dangerous);
   DangerousReader   aReader   = new DangerousReader(  dangerous);

      aWriter.start();
      aReader.start();
   } // End main.   
} // End  DangerousDemo.
