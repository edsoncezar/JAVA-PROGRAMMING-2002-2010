// Filename GenericStructure.java.
// Abstract base class for a collection of List,
// actions which will implement  a number of   
// different data structures.
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.

package Generics;

abstract class GenericStructure {

private Class elementClass;

   protected GenericStructure( Class theElementClass) { 
      super();   
      elementClass = theElementClass;
   } // end GenericStructure constructor.

   
   protected boolean isAcceptableElement( Object toTest) { 
   
   boolean exhausted = false;
   boolean found     = false;
   Class   thisClass = toTest.getClass();
   
        while ( (!exhausted) && (!found)){ 
           if ( elementClass == thisClass) { 
              found = true;
           } else {    
              thisClass = thisClass.getSuperclass();
              if ( thisClass == null){ 
                 exhausted = true;
              } // End if.
           } // End if.
        } // End while.
        return found;      
   } // End isAcceptableElement.
        
} // End GenericStructure.


