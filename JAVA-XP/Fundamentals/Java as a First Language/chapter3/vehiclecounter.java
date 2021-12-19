// Filename Counters/VehicleCounter.java
// 
// Developed to count vehicles for Exercise 3.4
// Payman Rezania

package Counters;

public class VehicleCounter extends LimitedCounter{

private final static CYCLE_COUNT = 1;
private final static CAR_COUNT   = 2;
private final static BUS_COUNT   = 5;
private final static VAN_COUNT   = 10;


	public VehicleCounter() {
		super();
	} // End default constructor

	public VehicleCounter( int minToCount, int maxToCount) {  
		super( minToCount, maxToCount);
	} // End alternative constructor


        public void countCycle() { 
            this.count();
        } // End countCycle. 


        public void countCar() { 
            this.count(); 
            this.count();
        } // End countCar. 


        public void countBus() { 
           for ( int index = 0; index < BUS_COUNT; index++) { 
              this.count(); 
           } // End for.
        } // End countBus. 


        public void countVan() { 
            for ( int index = 0; index < VAN_COUNT; index++) { 
              this.count(); 
            } // End for.
        } // End countVan.

} // End VehicleCounter. 