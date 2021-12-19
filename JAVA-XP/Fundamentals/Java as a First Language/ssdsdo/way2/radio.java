// Filename radio/Radio.java
//
// SSD Assessment version A

package radio;

public class Radio extends Object { 

private int volume = 5;

   public Radio() { 
      super();
      volume = 5; 
   } // End Radion constructor. 


   public void setVolume( int newVolume) { 
      volume = newVolume;
   } // End setVolume

   public int getVolume() { 
      return volume;
   } // End getVolume.


   public String toString() { 
      return "The volume is set to " + 
             this.getVolume() + ".";
   } // End toString
} // End class Radio. 



