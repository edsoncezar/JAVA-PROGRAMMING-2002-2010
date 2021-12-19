// Filename OrderedStructure.java.
// Interface for a set of OrderedList actions,
// to be implemented by a number of different data 
// structures.
//
// Written for JFL book, chapter 14.
// Fintan Culwin, v0.1, Jan 1997.

package Generics;
         
import Generics.Orderable; 
import Generics.Keyable;                             
import java.util.Enumeration;

public interface OrderedStructure { 

   public void add( Keyable toAdd);
   
   public  Keyable remove( Orderable keyToRemove);
   
   public  Keyable obtain( Orderable keyToObtain); 
   
   public  boolean isPresent( Orderable keyToCheck); 

   public  boolean isEmpty();
   
   public  int size();
   
   public  Enumeration elements();

} // End interface OrderedStructure.

