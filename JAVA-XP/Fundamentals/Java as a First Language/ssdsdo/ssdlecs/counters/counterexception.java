  // Filename counters/CounterException.java.
  // Provides an extension of the RuntimeException class 
  // for use with the Counters hierarchy.
  //
  // Written for JFL book Chapter 4 see text.
  // Fintan Culwin, v0.1, January 1997
  // This version Oct 1999

  package counters;
  
  class CounterException extends RuntimeException { 
  
     public CounterException(){
       // Do nothing.
     } // End default constructor
     
     public CounterException( String reason) { 
        super( reason);
     } // End principal constructor
     
  } // End CounterException


