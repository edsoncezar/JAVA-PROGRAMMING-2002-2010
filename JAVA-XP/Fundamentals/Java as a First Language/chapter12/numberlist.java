// Filename SecondCommandLineDemonstration.java.
// Illustrates how to read command line arguments 
// and write to output files. 
//
// Written for JFL Book Chapter 12.
// Fintan Culwin, V 0.1, Jan 1997. 


import java.io.*;
import NumberedPrintStream;

class NumberList { 
 
    public static void main(String args[]) {
    
    DataInputStream      inStream;
    NumberedPrintStream  outStream;
    boolean              inFileOpen    = false;

       if ( args.length == 0 || args.length > 2) { 
          System.out.println( 
             "\nTo use this utility you must either give the name of a\n" + 
             "single file which you want line numbers put into and \n"    +  
             "sent to the screen.\n\n"                                    +
             "Or give the names of two files and the contents of the \n"  +
             "first file will be sent with line numbers to the  \n"       + 
             "second file.\n\n"                                           +
             "You do not seem to have done this. \n\n");
             System.exit( -1);
       } // End if. 
    
       try { 
          inStream  = new DataInputStream( 
                          new FileInputStream( args[0]));
          inFileOpen = true;
       
          if ( args.length == 2) { 
             outStream = new NumberedPrintStream(
                             new FileOutputStream( args[1])); 
          } else {                            
             outStream = new NumberedPrintStream( System.out);
          } // End if.                                                
          
          while ( inStream.available() > 0 ) { 
             outStream.println( inStream.readLine());
          } // End while.
          
          inStream.close(); 
          outStream.close();
       } catch ( java.io.IOException exception)  { 
          if ( exception instanceof FileNotFoundException) { 
             if ( inFileOpen) { 
                System.out.println( 
                   "The file " + args[ 1] + " could not opened.\n");
             } else { 
                System.out.println( 
                   "The file " + args[ 0] + " could not found,\n" +
                   "or could not be opened.\n");
             } // End if.       
          } // End if.
       } // End try/ catch block.
    } // End main.
    
 } // End class NumberList.
 
