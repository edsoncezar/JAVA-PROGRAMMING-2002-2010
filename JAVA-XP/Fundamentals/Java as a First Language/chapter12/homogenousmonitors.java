// Filename HomogenousMonitors.java.
// Contains an indeterminate number of WarningCounter 
// instances with actions to write and read them from 
// a stream. 
//
// Written for JFL Book Chapter 12.
// Fintan Culwin, V 0.1, Jan 1997. 

import Counters.WarningCounter;
import java.io.*;
import java.util.Random;
import java.util.Date;


class HomogenousMonitors { 

private  Random generator = new Random( new Date().getTime()); 
private  WarningCounter theMonitors[];

   public HomogenousMonitors(){
   
   int numberOfMonitors = 2 + (int) (generator.nextDouble() * 5.0);
   int index; 
   int anotherIndex;
   int numberOfOccurrences; 
                    
      theMonitors = new WarningCounter[ numberOfMonitors];
      for ( index =0; index < numberOfMonitors; index++) { 
         theMonitors[ index] = new WarningCounter();
         numberOfOccurrences = 10 + 
                       (int) (generator.nextDouble() * 20.0);
         for ( anotherIndex =0; 
               anotherIndex < numberOfOccurrences;
               anotherIndex++) {                      
            if ( (generator.nextDouble() > 0.5)              &&
               (theMonitors[ index].numberCountedIs() > 0) ){
               theMonitors[ index].unCount();  
            } else { 
               theMonitors[ index].count(); 
            } // End if.
         } // End for anotherIndex.                         
      } // End for index.  
   } // End HomogenousMonitors constructor.


   public void resetMonitors() {
    
   int index; 
          
      for ( index =0; index < theMonitors.length; index++) {
         theMonitors[ index] = new WarningCounter();
      } // End for.
   } // End resetMonitors.


   public void writeMonitors( String filename) { 

   int index;     
   DataOutputStream theStream;

      try { 
         theStream = new DataOutputStream( 
                             new FileOutputStream( filename));
         theStream.writeInt( theMonitors.length);                    
         for ( index =0; index < theMonitors.length; index++) {
            theMonitors[ index].write( theStream);           
         } // End for.  
         theStream.close();
      } catch ( java.io.IOException exception) { 
         System.out.println( "Exception thrown on writing ... abending");
         System.exit( -1);                                                                
      } // End try/catch.   
   } // End writeMonitors.


   public void readMonitors( String filename) { 

   int index; 
   int numberOfMonitors;    
   DataInputStream theStream;

      try { 
         theStream = new DataInputStream( 
                             new FileInputStream( filename));
         numberOfMonitors = theStream.readInt();
         theMonitors = new WarningCounter[ numberOfMonitors];                    
         for ( index =0; index < theMonitors.length; index++) {
            theMonitors[ index] = new WarningCounter();
            theMonitors[ index].read( theStream);           
         } // End for.  
         theStream.close();
      } catch ( java.io.IOException exception) { 
         System.out.println( "Exception thrown on writing ... abending");
         System.exit( -1);                                                                
      } // End try/catch.   
   } // End writeMonitors.


   public String toString(){ 

   StringBuffer theString = new StringBuffer();
   int          index;
   char         theRoom = 'A';
   
      for ( index =0; index < theMonitors.length; index++) {  
         theString.append( "Room " + theRoom + "   " + 
                           theMonitors[ index].toString() + "\n");
         theRoom++;                           
      } // End for.
      return theString.toString(); 
   } // End toString;

} // End class HomogenousMonitors.
