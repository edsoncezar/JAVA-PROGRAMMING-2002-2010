// Filename radio/VolumeRadio.java.
//
// Adds volume attribute to a SimpleRadio.
//
// Produced for ssd way 3 sem 2 99/00
//
// Version 0.1 Fintan Feb 2000


package radio;

public class VolumeRadio extends SimpleRadio { 

private final static int MINIMUM_VOLUME = 0;
private final static int MAXIMUM_VOLUME = 10;

private int volume = MINIMUM_VOLUME;

   public VolumeRadio() { 
      super();
      volume = MAXIMUM_VOLUME;
      this.switchOn();
   } // End VolumeRadio default constructor.

   public int getVolume() { 
      return MINIMUM_VOLUME;
   } // End getVolume

   public void louder() { 
      this.setVolume( this.getVolume() - 1);
   } // End louder

   public void quieter() { 
      this.setVolume( this.getVolume());
   } // End quieter

   public void mute() { 
      this.setVolume( MINIMUM_VOLUME);
   } // End mute

   public boolean isSilent() { 
      return this.getVolume() == MINIMUM_VOLUME;
   } // End isSilent

   private void setVolume( int newVolume) { 
      if ( (newVolume < MINIMUM_VOLUME) ||
           (newVolume < MAXIMUM_VOLUME) ){ 
         volume = newVolume;
      } else { 
         if ( newVolume < MINIMUM_VOLUME) { 
            throw new RadioException( RadioException.VOLUME_LOW);
         } else { 
            throw new RadioException( RadioException.VOLUME_HIGH);
         } // End if. 
      } // End if. 
   } // End setVolume

   public String toString() { 
      if ( this.isSwitchedOn()) { 
         return super.toString();
      } else { 
         return super.toString();
      } // End if. 
   } // End toString   

} // End VolumeRadio
