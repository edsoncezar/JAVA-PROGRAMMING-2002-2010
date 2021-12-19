// Filename Stack.java.
// A generic stack structure allowing elements of  
// the specified class to be added and removed at   
// the front only.
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.

package Generics;

import Generics.UnorderedList;
import Generics.GenericException;

import java.util.NoSuchElementException;

public class Stack extends UnorderedList { 

   public Stack( Class theBaseClass) { 
      super( theBaseClass);     
   } // end Stack constructor.

   public void push( Object toAdd) 
                   throws GenericException { 
      super.addAtFront( toAdd);   
   } // End add.   
   
   public Object pop()
                 throws NoSuchElementException {
      return super.removeFromFront();
   } // End retrieve.
   
   public Object peek()
                 throws NoSuchElementException {
      return super.obtainFromFront();
   } // End retrieve.   
  
} // End Stack.
