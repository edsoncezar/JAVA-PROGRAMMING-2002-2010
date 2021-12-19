// Filename Type.java.
// Read a text file named on the command line  
// and output to the terminal.
//
// Written for JFL Book Chapter 12.
// Fintan Culwin, V 0.1, Jan 1997. 


import java.io.*;

class Type { 
 
    public static void main(String args[]) {
    
    DataInputStream      inStream;   

       if ( args.length != 1) { 
          System.out.println( 
             "To use this utility you must give the name\n" + 
             "of a single file which you want output.\n" + 
             "You do not seem to have done this. \n\n");
             System.exit( -1);
       } // End if.
    
       try { 
          inStream  = new DataInputStream( 
                              new FileInputStream( args[0]));
          
          while ( inStream.available() > 0 ) { 
             System.out.println( inStream.readLine());
          } // end While.
          
          inStream.close(); 
       } catch ( java.io.IOException exception)  { 
          if ( exception instanceof FileNotFoundException) { 
             System.out.println( 
                 "The file " + args[0] + " could not found,\n" +
                 "or could not be opened.\n");
          } // End if.
       } // End try/ catch block.
    } // end main.
    
 } // End class NumberedPrintStreamDemonstration.
 
