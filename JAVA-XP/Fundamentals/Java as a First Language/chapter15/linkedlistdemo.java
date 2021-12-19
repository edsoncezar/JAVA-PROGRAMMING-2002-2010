// Filename LinkededListDemo.java.
//
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.


import Generics.LinkedList;
import Generics.GenericException;
import Persons.*;

import java.util.NoSuchElementException;

class LinkedListDemo {

  public static void main(String args[]) {

  EmailAddress demoAddress;  
  NamedPerson  demoNamedPerson = new NamedPerson(); 
  PhonePerson  demoPhonePerson;
  String       demoString;
  
    LinkedList demoList = new LinkedList( demoNamedPerson.getClass());

  
     System.out.println( "\tOrdered List Demonstration Program\n\n");
     
     System.out.println( "Adding five instances to the list ...\n");
     
     demoNamedPerson = new NamedPerson( "sad@boring.com",
                                        "Suzie Sad");
     demoList.add( demoNamedPerson);      

    
     demoPhonePerson = new PhonePerson( "geek@boring.com",
                                        "Gary Geek",
                                        "111-2222");
     demoList.add( demoPhonePerson); 

                                                   
     demoNamedPerson = new NamedPerson( "anorak@boring.com",
                                        "Andy Anorak");
     demoList.add( demoNamedPerson);
     

     demoPhonePerson = new PhonePerson( "dull@boring.com",
                                        "Daisy Dull",
                                        "222-3333");
     demoList.add( demoPhonePerson);
                   
                   
     demoNamedPerson = new NamedPerson( "zzz@snooze.gov",
                                        "zzz zzzz");
     demoList.add( demoNamedPerson);     

     System.out.println( "Showing the list ...\n");
     System.out.println( demoList);
     
     
     System.out.println( "\nDemonstrating IsPresent ...");
     demoAddress = new EmailAddress( "anorak@boring.com");
     System.out.print( "At front " + demoAddress + " ... ");
     if ( demoList.isPresent( demoAddress)) { 
        System.out.println( " is present, which is correct.");
     } else { 
        System.out.println( " is not present, which is not correct.");
     } // End if.
     
     demoAddress = new EmailAddress( "zzz@snooze.gov");
     System.out.print( "At end " + demoAddress + " ... ");
     if ( demoList.isPresent( demoAddress)) { 
        System.out.println( " is present, which is correct.");
     } else { 
        System.out.println( " is not present, which is not correct.");
     } // End if. 
     
     demoAddress = new EmailAddress( "geek@boring.com");
     System.out.print( "In the middle " + demoAddress + " ... ");
     if ( demoList.isPresent( demoAddress)) { 
        System.out.println( " is present, which is correct.");
     } else { 
        System.out.println( " is not present, which is not correct.");
     } // End if.      


     demoAddress = new EmailAddress( "notKnown@nowhere.org");
     System.out.print( "Not in the list " + demoAddress + " ... ");
     if ( demoList.isPresent( demoAddress)) { 
        System.out.println( " is present, which is not correct.");
     } else { 
        System.out.println( " is not present, which is correct.");
     } // End if.      


     System.out.println( "\nDemonstrating obtain ...");
     demoAddress = new EmailAddress( "anorak@boring.com");
     System.out.println( "At front " + demoAddress + " ... ");
     try { 
         System.out.println( demoList.obtain( demoAddress));
         System.out.println( "No exception thrown .. which is correct.");        
     } catch ( NoSuchElementException exception) { 
         System.out.println( "Exception thrown .. which is not correct.");  
     } // End try/catch.
     

     demoAddress = new EmailAddress( "zzz@snooze.gov");
     System.out.println( "At end " + demoAddress + " ... ");
     try { 
         System.out.println( demoList.obtain( demoAddress));
         System.out.println( "No exception thrown .. which is correct.");        
     } catch ( NoSuchElementException exception) { 
         System.out.println( "Exception thrown .. which is not correct.");  
     } // End try/catch.

     demoAddress = new EmailAddress( "geek@boring.com");
     System.out.println( "In the middle " + demoAddress + " ... ");
     try { 
         System.out.println( demoList.obtain( demoAddress));
         System.out.println( "No exception thrown .. which is correct.");        
     } catch ( NoSuchElementException exception) { 
         System.out.println( "Exception thrown .. which is not correct.");  
     } // End try/catch. 
     
     demoAddress = new EmailAddress( "notKnown@nowhere.org");
     System.out.println( "Not in the list " + demoAddress + " ... ");
     try { 
         System.out.println( demoList.obtain( demoAddress));
         System.out.println( "No exception thrown .. which is not correct.");        
     } catch ( NoSuchElementException exception) { 
         System.out.println( "Exception thrown .. which is correct.");  
     } // End try/catch.      
     
     System.out.println( "\nDemonstrating remove ..."); 
     
     demoAddress = new EmailAddress( "notKnown@nowhere.org");
     System.out.println( "Not in the list " + demoAddress + " ... ");
     try { 
         System.out.println( demoList.remove( demoAddress));
         System.out.println( "No exception thrown .. which is not correct."); 
         System.out.println( demoList);       
     } catch ( NoSuchElementException exception) { 
         System.out.println( "Exception thrown .. which is correct.\n");  
     } // End try/catch.      

     
     demoAddress = new EmailAddress( "geek@boring.com");
     System.out.println( "In the middle " + demoAddress + " ... ");
     try { 
         System.out.println( demoList.remove( demoAddress));
         System.out.println( "No exception thrown .. which is correct."); 
         System.out.println( demoList);       
     } catch ( NoSuchElementException exception) { 
         System.out.println( "Exception thrown .. which is not correct.");  
     } // End try/catch.
          
     demoAddress = new EmailAddress( "zzz@snooze.gov");
     System.out.println( "At end " + demoAddress + " ... ");
     try { 
         System.out.println( demoList.remove( demoAddress));
         System.out.println( "No exception thrown .. which is correct."); 
         System.out.println( demoList);       
     } catch ( NoSuchElementException exception) { 
         System.out.println( "Exception thrown .. which is not correct.");  
     } // End try/catch.     
    

     demoAddress = new EmailAddress( "anorak@boring.com");
     System.out.println( "At front " + demoAddress + " ... ");
     try { 
         System.out.println( demoList.remove( demoAddress));
         System.out.println( "No exception thrown .. which is correct."); 
         System.out.println( demoList);       
     } catch ( NoSuchElementException exception) { 
         System.out.println( "Exception thrown .. which is not correct.");  
     } // End try/catch.         
                                              
  } // End main.
      


} // End Queue Demo.  

