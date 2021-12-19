// Filename radio/FMRadioDemonstration.java.
//
// SSD assessment varion A

package radio;

 public class FMRadioDemonstration extends Object { 
 
    public static void main( String argv[]) { 
 
    FMRadio  aRadio = null; 
 
        System.out.println( "\n\n\t FMRadio Demonstration");
        
        System.out.print( "\n\nConstructing an instance ... ");
        aRadio = new FMRadio();      
        System.out.print( "Instance created ... ");

        System.out.print( "\n\nDemonstrating getVolume() "+
                          ", it should be 5 ... ");
        System.out.println(  aRadio.getVolume());

        System.out.print( "\n\nDemonstrating getFrequency() "+
                          ", it should be 93.5 ... ");
        System.out.println(  aRadio.getFrequency());

        
        System.out.print( "\n\nSetting the frequency to 98.4\n" + 
                          "& getting it again,\n"+
                          "it should be 98.4 ... ");
        aRadio.setFrequency( 98.4);
        System.out.println(  aRadio.getFrequency());

        System.out.print( "\n\nSetting the volume to 115.6\n" + 
                          "& getting it again,\n"+
                          "it should sill be be 98.4 ... ");
        aRadio.setFrequency( 115.6);
        System.out.println(  aRadio.getFrequency());



        System.out.println( "\n\nDemonstrating toString().");
        System.out.println( aRadio);

        System.out.println( "\n\nEnd of FMRadio Demonstration");
   } // End main.

} // end class FMRadioDemonstration.    



