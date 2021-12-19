// Filename TrivialFileDemo.java.
// Read a text file called "test.txt" and output to 
// the terminal. 
//
// Written for JFL Book Chapter 12.
// Fintan Culwin, V 0.1, Jan 1997. 

import java.io.*;

class TrivialFileDemo { 
 
    public static void main(String args[]) {
    
    DataInputStream in;
    
       try { 
          in  = new DataInputStream( 
                        new FileInputStream( "test.txt"));
                        
          while ( in.available() > 0 ) { 
             System.out.println( in.readLine());
          } // end while
          
          in.close(); 
       } catch ( java.io.IOException exception)  { 
         if ( exception instanceof FileNotFoundException) { 
            System.out.println(
               "A file called test.txt could not be found, \n" +
               "or could not be opened.\n"                     +
               "Please investigate and try again.");
         } 
       } // End try/ catch block.
    } // end main.
    
 } // End class.
 
