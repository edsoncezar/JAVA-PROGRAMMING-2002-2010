




package playtime; 


public class PlayTime extends BasicTime { 

   public PlayTime() { 
      this( 0);
   } // End PlayTime default constructor.

   public PlayTime( int tenths) { 
      super( tenths);
   } // End PlayTime constructor.   

   public PlayTime timeBetween( PlayTime startTime) { 
      return new PlayTime( this.getDuration() - 
                           startTime.getDuration());
   } // End timeBetween

   public PlayTime addTime( PlayTime addTo) { 
      return new PlayTime( this.getDuration() + 
                           addTo.getDuration());
   } // End addTime   

} // End class PlayTime

