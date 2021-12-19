
 // Filename counters/BasicCounter.java.
 // Root class of the Counters hierarchy providing the 
 // essential counting functionality.
 //
 // Written for JFL book Chapter 3.
 // Fintan Culwin, v0.1, January 1997
// This version Oct 1999
 
 package counters;
 
 public class BasicCounter { 
 
 private int        counted          = 0;
 private int        initialCount     = 0;

 

    public BasicCounter() { 
       this( 0);
    } // End default constructor.
    
    public BasicCounter( int startFrom) { 
       counted      = startFrom;
       initialCount = startFrom;
    } // End principal constructor.
 

    public void count() { 
      counted++;
    } // End count.
    
    public void unCount() { 
      counted--;
    } // end unCount.

    public void reset() { 
       counted = initialCount;
    } // End reset.

    
    public int numberCountedIs(){ 
      return counted;
    } // End numberCountedIs.

    protected void setCountTo( int setTo) {
      counted = setTo;
    } // End setCountTo.

    public String toString() { 

      return "It has counted " + 
            this.numberCountedIs() + " occurences.";            
  } // End toString.

 
 } // end class BasicCounter



