 // Filename counters/LimitedCounter.java.
 // First extension of the Counters hierarchy defining 
 // limits upon the range of values which can be counted. 
 //
 // Written for JFL book Chapter 3.
 // Fintan Culwin, v0.1, January 1997.
// This version Oct 1999
 
 package counters;
 
 public class LimitedCounter extends BasicCounter { 
 
 static private final int DEFAULT_MIMIMUM = 0;
 static private final int DEFAULT_MAXIMUM = 999;
 
 private int miniumumCount = DEFAULT_MIMIMUM;  
 private int maximumCount  = DEFAULT_MAXIMUM;  
 

    public LimitedCounter() {
       this( DEFAULT_MIMIMUM, DEFAULT_MAXIMUM);
    }  // End Default Constructor.

    public LimitedCounter( int maxToCount) {
       this( DEFAULT_MIMIMUM, maxToCount);
    }  // End  Constructor.

    public LimitedCounter( int minToCount, int maxToCount) { 
        super( minToCount);
        miniumumCount = minToCount;
        maximumCount  = maxToCount;
    }  // End principal Constructor.

    
    public boolean isAtMinimum() { 
       return  this.numberCountedIs() == miniumumCount;
    } // End isAtMinimum.
 
    public boolean isAtMaximum() { 
       return  this.numberCountedIs() == maximumCount;
    } // End isAtMaximum.
    
    public int minimumIs() { 
       return miniumumCount;
    } // End minimumIs.
 
    public int maximumIs() { 
       return maximumCount;
    } // End maximumIs.

    public String toString() { 
       return "\nMinimum value " + this.minimumIs() + 
              ", maximum value " + this.maximumIs() + 
              ".\n" +  super.toString(); 
    } // End  toString.

 } // End class LimitedCounter.


