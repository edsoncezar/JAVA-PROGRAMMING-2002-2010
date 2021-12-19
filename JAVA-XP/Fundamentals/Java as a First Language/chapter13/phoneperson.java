// Filename PhonePerson.java.
// Extends the EmailPersons class by adding
// a phone number attribute.
//
// Written for JFL Book Chapter 12.
// Fintan Culwin, V 0.1, Jan 1997.

package Persons;

import Persons.EmailPerson;
import java.io.*;

public class PhonePerson extends NamedPerson { 

private String thierNumber;

private DataInputStream theKeyboard 
            = new DataInputStream( System.in);


   public PhonePerson() {    
      super();   
   } // End PhonePerson default constructor.


   public PhonePerson( String personsEmail,
                       String personsName,
                       String personsNumber) {
      super(  personsEmail, personsName);
      thierNumber = new String( personsNumber);
   } // End PhonePerson constructor.


   public void read() { 
   
   boolean numberOK = false;

      super.read();
      
      while ( ! numberOK) { 
          try { 
             System.out.print( "Please enter the phone number : ");
             System.out.flush();
             thierNumber = theKeyboard.readLine();
             if ( thierNumber.length() == 0) { 
                throw new IOException();
             } // End if.
             numberOK = true;             
          } catch ( IOException exception) { 
             System.out.println( 
                        "Sorry there seems to be a problem! "      +
                        "Could you please try again.");
          } // end try/catch.    
       } // End while.          
    } // End read.

    public String phoneNumberIs(){ 
       return thierNumber;    
    } // End phoneNumberIs.
    
    public String toString(){ 
    
      return super.toString() + " Phone : " +
             thierNumber;    
    } // End toString.

} // End class PhonePerson.

