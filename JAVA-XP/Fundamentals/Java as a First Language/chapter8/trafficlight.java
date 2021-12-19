// Filename TrafficLight.java.
// Providing a simple model of a traffic light.
//
// Written for JFL book Chapter 8 see text.
// Fintan Culwin, v0.1, January 1997.


public class TrafficLight extends Thread { 

String lightName;

public static final int RED       = 0;
public static final int RED_AMBER = 1;
public static final int GREEN     = 2;
public static final int AMBER     = 3;

private int state = AMBER;
Thread  peer;

   public TrafficLight( String name){ 
      lightName = new String( name);
   } // End TrafficLight constructor.



   public void setPeer( Thread thePeer) { 
      peer = thePeer;
   } // end setPeer.

   public void run() {

       while ( true) { 
          switch ( state ) {  
             case  RED :
                this.holdOn();
                state = RED_AMBER;
                this.report();
                break;
             case RED_AMBER :   
                this.holdOn();
                state = GREEN;
                this.report();
                break; 
             case GREEN :  
                this.holdOn();
                state = AMBER;
                this.report();
                break;
             case AMBER :  
                this.holdOn();
                state = RED;
                this.report();
                peer.resume();
                this.suspend();
                break;                                  
          } // End switch
       } // End while.
   } // End run.


   
   private void holdOn() { 
      try {         
         this.sleep( 500);
      } catch ( InterruptedException exception ){ 
            // Do nothing.
      } // End try/ catch.         
   } // End holdOn.
   


   private void report() { 

   String stateString = ""; 
   
       switch ( state ) { 
          case 0 : // RED
             stateString = new String( " red.");
             break;
          case 1 : // RED_AMBER
             stateString = new String( " red amber.");
             break;
          case 2 : // GREEN
             stateString = new String( " green.");
             break; 
          case 3 : // AMBER
             stateString = new String( " amber."); 
             break;                        
       } // End switch
       System.out.println( "The " +  lightName        +
                           " lights have changed to " + 
                           stateString);
    } // End report.
    
} // End  TrafficLight.
