// Filename ClickCounter.java.
// Provides the ClickCounter application class.
// Written for the Java Interface book Chapter 1.
//
// Fintan Culwin, v 0.2, August 1997.


public class ClickCounter extends Object { 


private static final int DEFAULT_MAXIMUM = 999;
private static final int DEFAULT_MINIMUM = 0;
private static final int STRING_WIDTH    = 3;

private int              minimumCount; 
private int              maximumCount;
private int              clicksCounted;

   public ClickCounter(){ 
      this( DEFAULT_MINIMUM, DEFAULT_MAXIMUM);
   } // End default constructor.
   
   public ClickCounter( int minimum){ 
      this( minimum, DEFAULT_MAXIMUM);
   } // End alternative constructor.      
   
   public ClickCounter( int minimum, int maximum){
      super(); 
      minimumCount  = minimum;
      maximumCount  = maximum;
      clicksCounted = minimum;
   } // End default constructor.        

   
   public boolean isAtMinimum(){ 
      return clicksCounted == minimumCount;  
   } // End isAtMinimum.
   
   public boolean isAtMaximum(){ 
      return clicksCounted == maximumCount;  
   } // End isAtMaximum .


   public void count() { 
      if ( ! this.isAtMaximum()) { 
         clicksCounted++;
      } // End if.  
   } // End count.


   public void unCount() { 
      if ( ! this.isAtMinimum()) { 
         clicksCounted--;
      } // End if.  
   } // End unCount.


   public void reset() { 
      clicksCounted = minimumCount;  
   } // End reset.
   
   
   public int countIs() { 
      return clicksCounted;
   } // End countIs.
   
   public String countIsAsString() { 
   
   StringBuffer buffer;
   
      buffer = new StringBuffer( Long.toString( 
                                          this.countIs(), 10));
      while ( buffer.length() < STRING_WIDTH) { 
         buffer.insert( 0, 0);
      } // End while.                                                                          
      return buffer.toString();   
   } // End countIsAsString.

} // End ClickCounter;
