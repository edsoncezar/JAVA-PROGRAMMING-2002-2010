// Filename radio/RadioDemonstration.java.
//
// SSD assessment varion A

package radio;

 public class RadioDemonstration extends Object { 
 
    public static void main( String argv[]) { 
 
    Radio  aRadio = null; 
 
        System.out.println( "\n\n\t Radio Demonstration");
        
        System.out.print( "\n\nConstructing an instance ... ");
        aRadio = new Radio();      
        System.out.print( "Instance created ... ");

        System.out.print( "\n\nDemonstrating getVolume() "+
                          ", it should be 5 ... ");
        System.out.println(  aRadio.getVolume());

        
        System.out.print( "\n\nSetting the volume to 9\n" + 
                          "& getting it again,\n"+
                          "it should be 9 ... ");
        aRadio.setVolume( 9);
        System.out.println(  aRadio.getVolume());

        System.out.print( "\n\nSetting the volume to 20\n" + 
                          "& getting it again,\n"+
                          "it should sill be be 9 ... ");
        aRadio.setVolume( 20);
        System.out.println(  aRadio.getVolume());



        System.out.println( "\n\nDemonstrating toString().");
        System.out.println( aRadio);

        System.out.println( "\n\nEnd of Radio Demonstration");
   } // End main.

} // end class RadioDemonstration.    



