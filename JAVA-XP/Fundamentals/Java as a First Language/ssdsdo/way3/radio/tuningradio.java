// Filename radio/TuningRadio.java.
//
// Adds Tuning attribute to a VolumeRadio.
//
// Produced for ssd way 3 sem 2 99/00
//
// Version 0.1 Fintan Feb 2000


package radio;

public class TuningRadio extends VolumeRadio { 

   public TuningRadio() { 
      super();
   } // End TuningRadio default constructor.


   public String toString() { 
      return "Tuning Radio!";
   } // End toString   

// It is likely that instead of obtaining an expected frequency value 
// such as 84.5 you might get something like 84.499999888887. You can 
// either ignore this and still regard your class as operating correctly.
// Or include the following line in an appropriate place ....
// frequency = ((double) Math.round( frequency * 10) /10.0);
// (You do not need to know how this line corrects the fault, but 
// if you are interetsed consult appendix A2 and A1 of the text book.)

} // End TuningRadio
