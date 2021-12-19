 // Filename Counters/WarningCounter.java.
 // Providing a non-abstract counter class with 
 // warning behaviour.
 //
 // Written for JFL book Chapter 3 see text.
 // Fintan Culwin, v0.1, January 1997
 
 package Counters;
 
 import Counters.CounterException;
 
 public class WarningCounter extends LimitedCounter { 


    public WarningCounter() {  
        super();
    }  // End default Constructor.
    
    
    public WarningCounter( int minToCount, int maxToCount) {  
        super( minToCount, maxToCount);
    }  // End principal Constructor.

 
    public void count()  { 
       if ( this.isAtMaximum()) { 
           throw new CounterException( "Attempt to count beyond limit.");
       } else { 
         super.count();
       }   
    } // End count.
    
    
    public void unCount()  { 
       if ( this.isAtMinimum()) { 
         throw new CounterException( "Attempt to count below limit.");
       } else { 
         super.unCount();
       }   
    } // End unCount.

 } // End WarningCounter
 
 

