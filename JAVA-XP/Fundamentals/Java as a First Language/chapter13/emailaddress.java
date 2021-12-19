// Filename EmailAddress.java.
// Provides an Orderable object which can be 
// used as a key class for an OrderedStructure.
//
// Written for JFL book chapter 14.
// Fintan Culwin, v 0.1, Jan 1997.

package Persons;

import Generics.Orderable;
import java.io.*;

public class EmailAddress implements Orderable { 

private String theAddress;

private DataInputStream theKeyboard 
            = new DataInputStream( System.in);
 
   public EmailAddress(){ 
      super();
   } // end default EmailAddress constructor.

 
   public EmailAddress( String anAddress){
      super(); 
      theAddress = anAddress;
   } // end default EmailAddress constructor.
                    
   public boolean keyIsEqualTo( Orderable other){
    return this.theAddress.equals( ((EmailAddress)other).theAddress);
   } // End keyIsEqualTo. 
   
   public boolean keyIsGreaterThan( Orderable other){
      return this.theAddress.compareTo( 
                     ((EmailAddress)other).theAddress ) > 0;
   } // End keyIsGreaterThan.
   
   public boolean keyIsLessThan( Orderable other){
      return this.theAddress.compareTo( 
                      ((EmailAddress)other).theAddress ) < 0;
   } // End keyIsLessThan.
   
   public void read(){ 
   
   boolean addressOK   = false;    
   String  fromKeyboard;
    
       while ( ! addressOK) { 
          try { 
             System.out.print( "Please enter the e-mail address :");
             System.out.flush();
             theAddress = new String( theKeyboard.readLine().trim());
             if ( theAddress.length() == 0) { 
                throw new IOException();
             } // End if.
             addressOK = true;             
          } catch ( IOException exception) { 
             System.out.println( 
                        "Sorry there seems to be a problem!\n" +
                        "Could you please try again.");
          } // End try/catch   
       } // End while.                 
   } // End read.

    public String toString(){    
      return( theAddress);
    } // End toString.


} // end EmailAddress.
