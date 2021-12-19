 // Filename Counters/RollOverCounter.java.
 // Providing a non-abstract counter class with 
 // roll over behaviour.
 //
 // Written for JFL book Chapter 3 see text.
 // Fintan Culwin, v0.1, January 1997
 
 package Counters;
 
 public class RollOverCounter extends LimitedCounter { 


    public RollOverCounter() {  
        super();
    }  // End principal Constructor.
    
    
    public RollOverCounter( int minToCount, int maxToCount) {  
        super( minToCount, maxToCount);
    }  // End principal Constructor.

 
    public void count(){ 
       if ( this.isAtMaximum()) { 
         this.setCountTo( this.minimumIs());
       } else { 
         super.count();
       }   
    } // End count.
    
    
    public void unCount(){ 
       if ( this.isAtMinimum()) { 
         this.setCountTo( this.maximumIs());
       } else { 
         super.unCount();
       }   
    } // End unCount.

 } // End RollOverCounter
 
