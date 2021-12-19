// Filename OutputFormatterException.java.
// Providing specific exception for number formatting.
//
// Written for JFL book Chapter 9 see text.
// Fintan Culwin, v0.1, January 1997.
// This version January 1999.

 
 
 
class OutputFormatException extends RuntimeException { 
 
public static final int UNKNOWN_REASON   = 0; 
public static final int INVALID_BASE     = 1;
public static final int FP_TOO_LARGE     = 2; 

private int theReason; 
    
    public OutputFormatException( int reason) {
       super();
       theReason = reason;
    } // End principal constructor
    

    public int obtainReason(){ 
       return theReason;    
    } // End obtainReason.
    
    
    public String toString(){ 
     String toReturn;
     
        switch ( theReason) {  
        case INVALID_BASE: 
           toReturn = new String( "Output format exception : Invalid base.");
           break;
        case FP_TOO_LARGE: 
           toReturn = new String( "Output format exception : Value too large.");
           break;          
        default: 
           toReturn = new String( "Output format exception : Unknown reason.");
        } // End switch. 
        return toReturn;    
    } // End toString.

} // End OutputFormatException.
 
