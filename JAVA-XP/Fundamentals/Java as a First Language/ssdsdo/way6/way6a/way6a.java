// Way6A.java
//
// A simple class containing several attributes, 
// a constructor, a setter rule and I/O methods. 
//
// A collection of these will be written to and 
// then read from a file. 
//
// Assumes BasicMenu & other terminal i/o classes 
// are available. 
//
// Fintan Culwin, V0.1, March 2000

package way6a;

import java.io.*;

public class Way6A extends Object { 

private char   aChar   = 'X';
private int    aNum    = 0; 
private String aString = "";

   public Way6A() { 
      super(); 
   } // End Way6A default constructor. 

   public Way6A( char conChar, int conNum) { 
      super();
      aChar = conChar;
      aNum  = conNum;
      this.setState();
   } // End Way6A

   public void setState() { 
      aString = new String();
      for ( int index =0; index < aNum; index++) { 
          aString = aString + aChar;
      } // End setState. 
   } // End Way6A

   public void writeDetails( DataOutputStream theStream) 
                               throws java.io.IOException {
      theStream.writeUTF( this.aString);
      theStream.writeInt( this.aNum);
   } // End writeDetails.   

   public void readDetails( DataInputStream theStream)
                               throws java.io.IOException {
      this.aString = theStream.readUTF();
      this.aNum    = theStream.readInt();
   } // End readDetails.

   public String toString() { 
      return aNum + " " + aChar + " " + aString; 
   } // End toString.

} // End Class Way6A.
