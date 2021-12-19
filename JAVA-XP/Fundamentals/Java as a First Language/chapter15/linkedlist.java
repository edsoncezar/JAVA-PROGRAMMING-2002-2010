// Filename LinkedList.java.
// Class to implement the OrderedStructure interface
// making use of the a single linked list. 
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.


package Generics;

import Generics.GenericStructure;
import Generics.GenericException;
import Generics.OrderedStructure;
import AlreadyPresentException;

import java.util.Vector;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;

public class LinkedList extends     GenericStructure
                        implements  OrderedStructure{ 

private ListNode theList;
private int      numberOfElements = 0;


   public LinkedList( Class theElementClass) { 
      super( theElementClass);     
   } // end LinkedList constructor.
   

   public void add( Keyable   toAdd) {

   Orderable  keyToAdd     = toAdd.keyValueIs();
   Orderable  nextKeyValue;
   ListNode   newNode      = new ListNode( toAdd.copy(), null);
   ListNode   thisNode;
   ListNode   nextNode     = null;
                                                                                      
      if ( !isAcceptableElement( toAdd) ){ 
         throw new GenericException();
      } else {      
         if ( this.isEmpty()) {
            theList = newNode;
            numberOfElements++;
         } else {
             if ( keyToAdd.keyIsLessThan(
                          theList.dataIs().keyValueIs()) ){          
                newNode.setNextNode( theList);
                theList = newNode;
                numberOfElements++;
             } else {
                thisNode     = theList;
                nextNode     = thisNode.nextIs();         
                if ( nextNode != null ) {            
                   nextKeyValue = nextNode.dataIs().keyValueIs();
                } else {            
                   nextKeyValue = null;
                } // End if. 
                while( (nextKeyValue != null)                     &&
                       (!(keyToAdd.keyIsLessThan( nextKeyValue))) ){                  
                   thisNode = nextNode;
                   nextNode = thisNode.nextIs();
                   if ( nextNode != null ) { 
                      nextKeyValue = nextNode.dataIs().keyValueIs();
                   } else { 
                      nextKeyValue = null;
                   } // End if.              
                } // End while.
                if ( (nextKeyValue != null)  &&
                     (keyToAdd.keyIsEqualTo( nextKeyValue)) ){         
                   throw new AlreadyPresentException();
                } else { 
                     thisNode.setNextNode( newNode);  
                     newNode.setNextNode(  nextNode);
                     numberOfElements++; 
                } // End if.
             } // End if before existing.
         } // End if empty list.             
      } // End if unacceptable.
   } // End add.
   
   
      
   public Keyable obtain( Orderable keyToObtain) {
    
   ListNode thisNode  = theList;
   boolean  found     = false;
   boolean  exhausted = false;
   
      while ( !found && !exhausted) { 
        if ( thisNode == null) { 
           exhausted = true;
        } else if ( keyToObtain.keyIsEqualTo( 
                         thisNode.dataIs().keyValueIs()) ){ 
           found = true;  
        } else { 
           thisNode = thisNode.nextIs();
        } // End if.
      } // End while. 
                 
      if ( found ) { 
         return thisNode.dataIs().copy();
      } else { 
         throw new NoSuchElementException();
      } // End if.            
   } // End obtain.  
   
   
   
   public Keyable remove( Orderable keyToRemove) { 

   Keyable  hold; 
   ListNode thisNode  = theList;
   ListNode lastNode  = null;
   boolean  found     = false;
   boolean  exhausted = false;
   
      while ( !found && !exhausted) { 
        if ( thisNode == null) { 
           exhausted = true;
        } else if ( keyToRemove.keyIsEqualTo( 
                         thisNode.dataIs().keyValueIs()) ){ 
           found = true;  
        } else { 
           lastNode = thisNode;        
           thisNode = thisNode.nextIs(); 
        } // End if.
      } // End while.                  
   
      if ( !found ) { 
         throw  new NoSuchElementException();
      } else {
         hold  = thisNode.dataIs();
         if ( thisNode == theList) { 
            theList = theList.nextIs();
         } else { 
            lastNode.setNextNode( thisNode.nextIs());
         } // End if.
         numberOfElements--;
         return hold;
      } // End if.     
   } // End remove.  
   
   
   
   public  boolean isPresent( Orderable keyToCheck){
   
   ListNode thisNode  = theList;
   boolean  found     = false;
   boolean  exhausted = false;
   
      while ( !found && !exhausted) { 
        if ( thisNode == null) { 
           exhausted = true;
        } else if ( keyToCheck.keyIsEqualTo( 
                         thisNode.dataIs().keyValueIs())) { 
           found = true;  
        } else { 
           thisNode = thisNode.nextIs();
        } // End if.
      } // End while.            
      return found;
   } // End isPresent.    
   
   
   public Enumeration elements() {    
     return new LinkedListEnumeration( theList);
   } // End elements.
   

   public int size() { 
      return numberOfElements;
   } // End size.
   
   
   public boolean isEmpty() { 
      return theList == null;
   } // End isEmpty.
   
   
   public String toString(){ 

   StringBuffer buffer      = new StringBuffer();
   int          thisElement = 0;
   Enumeration  theElements = this.elements();
   
      while ( theElements.hasMoreElements() ) { 
         buffer.append( ++thisElement + "  " + 
                        theElements.nextElement() + ".\n");                             
      } // end while;
      return buffer.toString();   
   } // End toString.    

} // End OrderedList.


/////////////////////////////////////////////////////////
// Private ListNode class, providing the nodes which   //
// are linked together to implement the linkedList.    //
///////////////////////////////////////////////////////// 


class ListNode { 

Keyable  theData;
ListNode nextNode;


    protected ListNode( Keyable  toStore,
                        ListNode linkTo){ 
       theData  = toStore;
       nextNode = linkTo;    
    } // End ListNode constructor.

    
    protected Keyable dataIs(){ 
       return theData;
    } // End dataIs.
    

    protected ListNode nextIs(){ 
       return nextNode;
    } // End nextIs. 
    

    protected void setNextNode( ListNode setTo){ 
       nextNode = setTo;         
    } // End setNextNode.
    
} // End class ListNode.



/////////////////////////////////////////////////////////
// Private LinkedListEnumeration class, implementing   //
// an Enumeration to traverse the list.                //
///////////////////////////////////////////////////////// 

class LinkedListEnumeration implements Enumeration { 


ListNode currentNode;

   public LinkedListEnumeration( ListNode startNode){ 
      currentNode = startNode;
   } // End LinkedListEnumeration constructor.


   public boolean hasMoreElements(){ 
       return currentNode != null;
   } // end hasMoreElements.
   

   public Object nextElement(){ 
   
   Object hold;
   
      if ( currentNode == null) { 
         throw new NoSuchElementException();
      } else { 
         hold = currentNode.dataIs();
         currentNode = currentNode.nextIs();
         return (Object) ( ((Keyable) hold).copy());
      } // End if.
   } // End nextElement.

} // End class LinkedListEnumeration;
 
