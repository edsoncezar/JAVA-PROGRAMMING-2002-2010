// Filename Counters/RoomMonitor.java.
// Providing a non-abstract class which monitors 
// entry and exit occurrences, first implementation.
//
// Written for JFL book Chapter 3 see text.
// Fintan Culwin, v0.1, January 1997

package Counters;

public class RoomMonitor extends WarningCounter { 

   // Largest possible positive int value, 2147483647 in decimal.
   private static final int MAXINTEGER = 0x7FFFFFFF; 


   private int maxEverInRoom         = 0;
   private int totalNumberEntered    = 0;


   public RoomMonitor() { 
      super(0, MAXINTEGER);
      maxEverInRoom       = 0;
      totalNumberEntered  = 0;
   } // End default constructor.

   public void enterRoom() {
      this.count();
      totalNumberEntered++;
      if ( this.numberCurrentlyInRoomIs() > maxEverInRoom ) { 
         maxEverInRoom++;
      } 
   } // End enterRoom.

   public void exitRoom() { 
      this.unCount(); 
   } // End exitRoom.

   public int numberCurrentlyInRoomIs(){ 
      return  this.numberCountedIs();
   } // End numberCurrentlyInRoomIs.


   public int maxEverInRoomIs(){ 
      return maxEverInRoom;
   } // End maxEverInRoomIs.


   public int totalNumberEnteredIs(){ 
      return totalNumberEntered;
   } // End totalNumberEnteredIs.

   public String toString(){
      return "Now in room : "    + this.numberCurrentlyInRoomIs() + 
             " Max in room : "   + this.maxEverInRoomIs()         +
             " Total entered : " + this.totalNumberEnteredIs();
   } // End toString.

} // End RoomMonitor.
