 // Filename counters/StoppingCounter.java.
 // Providing a non-abstract counter class with 
 // roll over behaviour.
 //
 // Written for JFL book Chapter 3 see text.
 // Fintan Culwin, v0.1, January 1997
 // This version Oct 1999

 package counters;
 
 public class StoppingCounter extends LimitedCounter { 


    public StoppingCounter() {  
        super();
    }  // End principal Constructor.
    

    public StoppingCounter( int maxToCount) {
       super(  maxToCount);
    }  // End  Constructor.
    
    public StoppingCounter( int minToCount, int maxToCount) {  
        super( minToCount, maxToCount);
    }  // End principal Constructor.

 
    public void count(){ 
       if ( ! this.isAtMaximum()) { 
         super.count();
       }  // End if. 
    } // End count.
    
    
    public void unCount(){ 
       if ( !this.isAtMinimum()) { 
         super.unCount();
       } // End if.  
    } // End unCount.

    public String toString() { 
       return "A Stopping Counter " + 
              super.toString();  
    } // End  toString.

 } // End RollOverCounter
 

