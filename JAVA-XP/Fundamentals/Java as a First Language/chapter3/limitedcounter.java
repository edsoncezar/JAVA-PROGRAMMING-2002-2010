 // Filename Counters/LimitedCounter.java.
 // First extension of the Counters hierarchy defining 
 // limits upon the range of values which can be counted. 
 //
 // Written for JFL book Chapter 3.
 // Fintan Culwin, v0.1, January 1997.
 
 package Counters;
 
 abstract class LimitedCounter extends BasicCounter { 
 
 static private final int defaultMinimum = 0;
 static private final int defaultMaximum = 999;
 
 private int miniumumCount;  
 private int maximumCount;  
 
 
    //  The principal Constructor.
    protected LimitedCounter( int minToCount, int maxToCount) { 
        super( minToCount);
        miniumumCount = minToCount;
        maximumCount  = maxToCount;
    }  // End principal Constructor.
    
    // Default Constructor.
    protected LimitedCounter() {
       this( defaultMinimum, defaultMaximum);
    }  // End Default Constructor.
    
    protected boolean isAtMinimum() { 
       return  this.numberCountedIs() == miniumumCount;
    } // End isAtMinimum.
 
    protected boolean isAtMaximum() { 
       return  this.numberCountedIs() == maximumCount;
    } // End isAtMaximum.
    
    protected int minimumIs() { 
       return miniumumCount;
    } // End minimumIs .
 
    protected int maximumIs() { 
       return maximumCount;
    } // End maximumIs.

 } // End class LimitedCounter.

