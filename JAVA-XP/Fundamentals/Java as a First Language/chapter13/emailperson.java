// Filename EmailPerson.java.
// Base class of the Persons hierarny supporting 
// the e-mail address attribute.
//
// Written for JFL Book Chapter 12.
// Fintan Culwin, V 0.1, Jan 1997.

package Persons;

import Generics.Keyable;
import Generics.Orderable;
import Persons.EmailAddress;

abstract class EmailPerson extends    Object 
                           implements Keyable { 

private EmailAddress thierEmail;

   public EmailPerson() {    
      super();
      thierEmail = new EmailAddress();
   } // End EmailPerson default constructor.
   

   public EmailPerson( String personsEmail) {
      thierEmail = new EmailAddress( personsEmail);
   } // End EmailPerson default constructor.   
   

    public void read(){ 
       thierEmail.read();        
    } // End read. 


    public EmailAddress eMailIs(){     
       return thierEmail;
    } // End eMailIs.


    public String toString(){    
      return( "E-mail : " + thierEmail);
    } // End toString.


    public int hashCode(){ 
       return thierEmail.hashCode(); 
    } // End hashCode.


   public Orderable keyValueIs(){
      return thierEmail;
   } // End keyValueIs

} // End class EmailPerson.       
