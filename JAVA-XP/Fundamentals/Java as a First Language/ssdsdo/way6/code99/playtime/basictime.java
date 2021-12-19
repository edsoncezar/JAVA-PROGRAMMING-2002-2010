// Filename playtime\BasicTime.java
//
// A class which records a time to an accuracy 
// of one tenth of a second, supplying methods 
// to obtain mins, secs and tenths in the format 
// mm:ss:t.
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.
//
// This version v0.2 for way 6 march 2000 
// including writeTime() and readTime() methods. 

package playtime; 

import java.io.*;


public class BasicTime extends Object { 

private static final int TENTHS_PER_SECOND = 10;
private static final int TENTHS_PER_MINUTE = 
                            TENTHS_PER_SECOND * 60;

private int duration = 0;

   public BasicTime() { 
      this( 0);
   } // End BasicTime

   public BasicTime( int tenths) { 
      super();
      duration = tenths;
   } // End BasicTime.

   public int getMinutes() {
      return duration / TENTHS_PER_MINUTE;
   } // End getMinutes

   public int getTenthsOver() { 
      return duration % TENTHS_PER_SECOND; 
   } // End getTenthsOver

   public int getSecondsOver() { 
      return (duration % TENTHS_PER_MINUTE) / 
                        TENTHS_PER_SECOND; 
   } // End getTenthsOver

   protected int getDuration() { 
      return duration;
   } // End getDuration.

   public String toString() {    
      return this.getMinutes()     + " min " + 
             this.getSecondsOver() + "." + 
             this.getTenthsOver() + " seconds";
   } // End toString

   public void writeTime( DataOutputStream theStream)
                                   throws java.io.IOException {
      theStream.writeInt( this.duration);
   } // End writeTime.

   public void readTime( DataInputStream theStream)
                               throws java.io.IOException {
      this.duration  = theStream.readInt();
   } // End readTime.
} // End BasicTime.
