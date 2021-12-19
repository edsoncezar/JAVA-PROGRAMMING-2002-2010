// Filename OrderedList.java.
// Class to implement the OrderedStructure interface
// making use of the a Vector. 
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

public class OrderedList extends     GenericStructure
                         implements  OrderedStructure{ 

private Vector theList = new Vector();

private static final int KEY_NOT_PRESENT = -1;


   public OrderedList( Class theElementClass) { 
      super( theElementClass);     
   } // end UnorderedList constructor.
   

   public void add( Keyable toAdd) {

   Orderable  keyToAdd         = toAdd.keyValueIs(); 
   Orderable  thisKeyValue;
   int        thisComparison;
   int        thisLocation     = 0;
   int        numberOfElements = theList.size();
   boolean    locationFound    = false;
                                                                             
      if ( !isAcceptableElement( toAdd) ){ 
         throw new GenericException();
      } else {      
         if ( this.isEmpty()) { 
            theList.addElement( toAdd.copy());
         } else { 
            while ( !locationFound) {     
               thisKeyValue = ( (Keyable) theList.elementAt( 
                                          thisLocation)).keyValueIs();                                          
               if ( thisKeyValue.keyIsEqualTo( keyToAdd) ){ 
                  throw new AlreadyPresentException();
               } else if ( thisKeyValue.keyIsGreaterThan( keyToAdd) ){ 
                  locationFound  = true;
               } else { 
                  locationFound = ++thisLocation == numberOfElements;                    
               } // End if.                    
            } // End while.                     
            theList.insertElementAt( toAdd.copy(), thisLocation);          
         } // end if.
      } // End if.
   } // End add.
   
   
   public Keyable obtain( Orderable keyToObtain) { 
   
   int location = findKey( keyToObtain);
   
      if ( location == KEY_NOT_PRESENT ) { 
         throw  new NoSuchElementException();
      } else { 
         return ((Keyable) 
                 theList.elementAt( location)).copy();            
      } // End if.
   } // End obtain.  
   

   public Keyable remove( Orderable keyToRemove) { 

   int     location = findKey( keyToRemove);
   Keyable hold;
   
      if ( location == KEY_NOT_PRESENT ) { 
         throw  new NoSuchElementException();
      } else { 
         hold =  ((Keyable) theList.elementAt( location)); 
         theList.removeElementAt( location);
         return hold;           
      } // End if.     
   } // End remove.  
   
   public  boolean isPresent( Orderable keyToCheck){ 
      return (this.findKey( keyToCheck)) != KEY_NOT_PRESENT;
   } // End isPresent.    


   protected int findKey( Orderable keyToFind){

   Orderable thisKeyValue;
   int       lowerBound     = 0;
   int       upperBound     = theList.size();
   int       thisLocation   = (upperBound + lowerBound) /2;
   int       nextLocation;
   boolean   found          = false;
   boolean   exhausted      = false;
   
      while ( (!found) && (!exhausted) ){ 
         thisKeyValue   = ( (Keyable) 
                             theList.elementAt( thisLocation)).
                             keyValueIs();
         if ( thisKeyValue.keyIsEqualTo( keyToFind)) { 
             found = true;
         } else { 
             if ( thisKeyValue.keyIsGreaterThan( keyToFind))  {                      
                upperBound = thisLocation -1;
            } else {             
                lowerBound = thisLocation +1;
            } // End if.   
            nextLocation = (upperBound + lowerBound) /2;         
            exhausted    = (thisLocation == nextLocation);            
            thisLocation = nextLocation;         
         } // End if.
      } // End while.
   
      if ( found) { 
         return thisLocation;
      } else { 
         return KEY_NOT_PRESENT;
      } // End if.         
   } // End findKey. 


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

} // End OrderedList.
