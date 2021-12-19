// Filename UnorderedList.java.
// Abstract class supplying the actions to 
// support the Stack and Queue classes.
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.

package Generics;

import Generics.GenericStructure;
import Generics.GenericException;

import java.util.Vector;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;

abstract class UnorderedList extends GenericStructure { 

private Vector theList = new Vector();

   public UnorderedList( Class theBaseClass) { 
      super( theBaseClass);     
   } // end UnorderedList constructor.
   

   protected void addAtFront( Object toAdd) {    
      if ( isAcceptableElement( toAdd)) { 
         theList.insertElementAt( toAdd, 0);
      } else { 
        throw new GenericException();
      } // End if.
   } // End addAtFront.
   
   
   protected Object obtainFromFront() 
                throws NoSuchElementException { 
      return theList.firstElement();            
   } // End removeFromFront.  
   

   protected Object removeFromFront()  
                throws NoSuchElementException { 
   
   Object hold;
                   
      try {
         hold = theList.firstElement();
         theList.removeElementAt( 0);  
         return hold;          
      } catch (ArrayIndexOutOfBoundsException exception) { 
         throw new NoSuchElementException();
      } // end try/catch.   
   } // End removeFromFront.  
   
   
   protected void addAtEnd( Object toAdd) {    
      if ( isAcceptableElement( toAdd)) { 
         theList.addElement( toAdd);
      } else { 
        throw new GenericException();
      } // End if.
   } // End addAtEnd.
      

      
   protected Object obtainFromEnd() 
                throws NoSuchElementException { 
      return theList.lastElement();            
   } // End removeFromFront.     
   

   protected Object removeFromEnd()  
                throws NoSuchElementException { 
   
   Object hold;
                   
      try {
         hold = theList.lastElement();
         theList.removeElementAt( theList.size());  
         return hold;          
      } catch (ArrayIndexOutOfBoundsException exception) { 
         throw new NoSuchElementException();
      } // end try/catch.   
   } // End removeFromFront.  
      
   
   public Enumeration elements() {    
     return theList.elements();
   } // End elements.
   
   
   public int size() { 
      return theList.size();
   } // End size.
   
   
   public boolean isEmpty() { 
      return theList.isEmpty();
   } // End isEmpty.
   
   
   public String toString(){ 

   int         thisElement = 0;
   Object      fromList;
   StringBuffer buffer     = new StringBuffer();
   Enumeration contents    = theList.elements();
   
      while( contents.hasMoreElements() ) { 
         fromList = contents.nextElement();      
         thisElement++;
         buffer.append( thisElement + "  " + fromList + "\n");       
      } // End while;
      return buffer.toString();   
   } // End toString.    

} // End UnorderedList.

