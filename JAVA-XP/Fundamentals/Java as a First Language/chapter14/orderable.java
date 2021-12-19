// Filename Orderable.java.
// Orderable Interface definition, allowing
// two objects to be compared for a defined relationship
// Required for the OrderedStructure generic class.
//
// Written for JFL book Chapter 14.
// Fintan Culwin, V 0.1, Jan. 1997.

package Generics;

public interface Orderable { 

   public boolean keyIsEqualTo( Orderable other);
   
   public boolean keyIsGreaterThan( Orderable other);
   
   public boolean keyIsLessThan( Orderable other);

} // End interface Orderable.
