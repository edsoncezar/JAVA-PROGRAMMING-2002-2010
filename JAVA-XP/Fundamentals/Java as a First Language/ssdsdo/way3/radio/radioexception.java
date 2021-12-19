// Filename radio/RadioException.java.
//
// Specific exception class for radio hierarchy
//
// Produced for ssd way 3 sem 2 99/00
//
// Version 0.1 Fintan Feb 2000


package radio;

public class RadioException extends RuntimeException { 

public final static int UNKNOWN        = 0;
public final static int VOLUME_LOW     = 1;
public final static int VOLUME_HIGH    = 2;
public final static int FREQUENCY_LOW  = 3;
public final static int FREQUENCY_HIGH = 4;

private int reason = UNKNOWN;

   public RadioException() { 
      this( UNKNOWN);
   } // End RadioException.


   public RadioException( int theReason) { 
      super();
      reason = theReason;
   } // End RadioException.
   

   public String toString() { 

   String toReturn = null;

      switch( reason) { 

      case VOLUME_LOW:
         toReturn = "Volume too low.";
         break;

      case VOLUME_HIGH:
         toReturn = "Volume too high.";
         break;

      case FREQUENCY_LOW:
         toReturn = "Frequency too low.";
         break;

      case FREQUENCY_HIGH:
         toReturn = "Frequency too high.";
         break;

      default: 
         toReturn = "Unknown reason.";
         break;         
      } // End switch.
      return "Radio Exception - " + toReturn;
   } // End toString.
} // End RadioException
