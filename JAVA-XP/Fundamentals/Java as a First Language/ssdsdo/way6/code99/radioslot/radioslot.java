


// This version from way 6 - including writeSlot() & readSlot()


package radioslot;

import java.io.*;
import playtime.PlayTime;

public class RadioSlot extends Object { 

public static final int UNKNOWN = 0;
public static final int MUSIC   = 1;
public static final int ADVERT  = 2;
public static final int NEWS    = 3;
public static final int WEATHER = 4;

private int    slotType        = UNKNOWN;
private String slotDescription = null;
private PlayTime slotDuration  = null;
private PlayTime startTime     = null;


   public RadioSlot() { 
      slotType = UNKNOWN ;
      slotDescription = "";
      slotDuration    = new PlayTime( 0);
      startTime       = new PlayTime( 0);
   } // End RadioSlot default constructor.

   public RadioSlot( int type, 
                     String description, 
                     PlayTime duration) 
                       throws RuntimeException { 
      if ( type < UNKNOWN ||
           type > WEATHER ) { 
         throw new RuntimeException( "RadioSlot: " + 
                              "Invalid slot type [" + 
                              type + "]");
      } else { 
         slotType = type; 
         slotDescription = description;
         slotDuration    = duration;
         startTime       = new PlayTime( 0);
      } // End if. 
   } // End RadioSlot alternative constructor.


   public void setSlotStartTime( PlayTime startAt) { 
      startTime = startAt;
   } // End setSlotStartTime.

   public PlayTime getSlotStartTime() { 
      return startTime;
   } // End getSlotStartTime.

   public int getSlotType() { 
      return slotType;
   } // End getSlotType

   public String getSlotDescription() { 
      return slotDescription;
   } // End getSlotDescription.

   public PlayTime getSlotDuration() { 
      return slotDuration;
   } // End getSlotDuration.


   public PlayTime getSlotEndTime() { 

   PlayTime endTime = null;

      endTime = startTime.addTime( slotDuration);
      return endTime;
   } // End getSlotEndTime.


   public String getSlotTypeAsString() { 

   String toReturn = null;

      switch( slotType) { 

      case MUSIC: 
               toReturn = "Music";
               break;      
      case ADVERT: 
               toReturn = "Advert";
               break;
      case NEWS: 
               toReturn = "News";
               break;
      case WEATHER: 
               toReturn = "Weather";
               break;
      default: 
               toReturn = "Unknown";
               break;
      } // End switch. 
      return toReturn;
   } // End getSlotTypeAsString


   public String toString() { 

      return this.getSlotTypeAsString()            + " "  + 
             this.getSlotDescription()             + "\n" + 
             "Start    " + this.getSlotStartTime() + "\n" + 
             "Duration " + this.getSlotDuration()  + "\n" + 
             "End      " + this.getSlotEndTime()   + "\n";             
   } // End toString

   public void writeSlot( DataOutputStream theStream)
                                  throws java.io.IOException {
      theStream.writeUTF( this.slotDescription);
      theStream.writeInt( this.slotType);
      this.startTime.writeTime( theStream);
      this.slotDuration.writeTime( theStream); 
   } // End writeSlot.

   public void readSlot( DataInputStream theStream)
                               throws java.io.IOException {
      this.slotDescription = theStream.readUTF();
      this.slotType        = theStream.readInt();
      this.startTime.readTime( theStream);
      this.slotDuration.readTime( theStream);
   } // End readSlot.

} // End RadioSlot. 
