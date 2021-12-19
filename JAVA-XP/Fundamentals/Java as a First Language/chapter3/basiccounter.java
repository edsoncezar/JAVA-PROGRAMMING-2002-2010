
 // Filename Counters/BasicCounter.java.
 // Root class of the Counters hierarchy providing the 
 // essential counting functionality.
 //
 // Written for JFL book Chapter 3.
 // Fintan Culwin, v0.1, January 1997
 
 package Counters;
 
 abstract class BasicCounter { 
 
 private int counted;
 
    // Principal constructor.
    protected BasicCounter( int initialCount) { 
       counted = initialCount;
    } // End principal constructor.
 
    // Default constructor.
    protected BasicCounter() { 
       this( 0);
    } // End default constructor.
    
    protected void count() { 
      counted++;
    } // End count.
    
    protected void unCount() { 
      counted--;
    } // end unCount.

    protected void setCountTo( int setTo) {
      counted = setTo;
    } // End setCountTo.
    
    public int numberCountedIs(){ 
      return counted;
    } // End numberCountedIs.
 
 } // end class BasicCounter


