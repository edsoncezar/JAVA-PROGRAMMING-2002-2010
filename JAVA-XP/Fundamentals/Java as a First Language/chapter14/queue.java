// Filename Queue.java.
// A generic queue struture allowing elements of the 
// specified class to be removed from the front and 
// added to the end only.
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.

package Generics;

import Generics.UnorderedList;
import Generics.GenericException;

import java.util.NoSuchElementException;

public class Queue extends UnorderedList { 

   public Queue( Class theBaseClass) { 
      super( theBaseClass);     
   } // end Queue constructor.

   public void add( Object toAdd) 
                  throws GenericException { 
      super.addAtEnd( toAdd);   
   } // End add.   
   
   public Object retrieve()
                  throws NoSuchElementException {
      return super.removeFromFront();
   } // End retrieve. 
   
} // End Queue.
