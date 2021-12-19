// Filename QueueDemo.java.
// Demonstration harness of the Queue data structure,
// using a queue of e-mail persons.
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.


import Generics.Queue;
import Generics.GenericException;
import Persons.*;

class QueueDemo {

  public static void main(String args[]) {
  
  NamedPerson demoNamedPerson= new NamedPerson(); 
  PhonePerson demoPhonePerson;
  String      demoString;
  
  Queue       demoQueue = new Queue( demoNamedPerson.getClass());
  
  
     System.out.println( "\tQueue Demonstration Program\n\n");
     
     System.out.println( "Adding three instances to the queue ...");
     
     demoNamedPerson = new NamedPerson( "anorak@boring.com",
                                        "Andy Anorak");
     demoQueue.add( demoNamedPerson);
     
     demoPhonePerson = new PhonePerson( "geek@boring.com",
                                        "Gary Geek",
                                        "111-2222");
     demoQueue.add( demoPhonePerson);                                            
  
  
    demoNamedPerson = new NamedPerson( "sad@boring.com",
                                       "Suzie Sad");
    demoQueue.add( demoNamedPerson);  
 
    System.out.println( "\nShowing the queue ...\n");
    System.out.println( demoQueue);
    
    System.out.println( "\n Retrieving and showing ...\n");
    System.out.println( demoQueue.retrieve());
    System.out.println( "\n The queue now contains " + 
                        demoQueue.size() + " elements.");

    System.out.println( "\nAttempting to add an element " + 
                        "of an inappropriate class ...");
    try { 
       demoString = new String( "Doesn't matter!");
       demoQueue.add( demoString);
       System.out.println( "No exception thrown ... " + 
                           "which is incorrect.");
    } catch ( GenericException exception){ 
       System.out.println( "GenericException thrown ... " + 
                           "which is correct.");    
    } // End try/catch.                        
   
  } // End main.
      
} // End Queue Demo.  

