// Filename NamedPerson.java.
// Base class of the Persons hierarchy, 
// supporting the name attribute.
//
// Written for JFL Book Chapter 12.
// Fintan Culwin, V 0.1, Jan 1997.

package Persons;

import java.io.*;

public class NamedPerson extends EmailPerson {  

private String  thierName;

private DataInputStream theKeyboard 
            = new DataInputStream( System.in);
                    

    public NamedPerson() { 
       super();    
    } // End BasicBirthday default constructor.
    
    public NamedPerson( String personsEmail,
                        String personsName) { 
       super( personsEmail);
       thierName = new String( personsName);
    } // End BasicBirthday default constructor.    


    public void read(){ 
    
    boolean nameOK   = false;    
    String  fromKeyboard;
    
       super.read();
       while ( ! nameOK) { 
          try { 
             System.out.print( "Please enter the name   :");
             System.out.flush();
             thierName = new String( theKeyboard.readLine());
             if ( thierName.length() == 0) { 
                throw new IOException();
             } // End if.
             nameOK = true;             
          } catch ( IOException exception) { 
             System.out.println( 
                        "Sorry there seems to be a problem!\n" +
                        "Could you please try again.");
          }    
       } // End while.              
    } // End read. 
    
    
    public String nameIs(){ 
       return thierName;    
    } // End nameIs.
    
    public String toString(){     
       return super.toString() + " Name : " + thierName;
    } // End toString.
    
} // End class NamedPerson.
