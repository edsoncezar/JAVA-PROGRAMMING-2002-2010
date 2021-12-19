// Filename OutputFormatterDemonstration.java.
// Demonstration harness for the OutputFormatter class.
//
// Produced for JFL book Chapter 9.
// Fintan Culwin, v0.2, January 1997.
// This version January 1999.

import OutputFormatter;

public class OutputFormatterDemonstration { 


   public static void main( String argv[]) {

   int    demoInt   = 255;
   float  demoFloat = 123.456F;
   
      System.out.println( "\t Output Formatter Demonstration \n");
      

      System.out.println( "\nOutputting " + demoInt + " in decimal with "  +
                          "leading zeros and width 12 ... ");
      System.out.println( OutputFormatter.formatLong( (long) demoInt, 
                                                      12, true, 
                                                      OutputFormatter.DECIMAL));                            
         
      System.out.println( "\nOutputting " + demoInt + " in hex with "  +
                          "leading spaces and width 12 ... ");
      System.out.println( OutputFormatter.formatLong( (long) demoInt, 
                                                      12, false, 
                                                      OutputFormatter.HEX));       

      System.out.println( "\nOutputting " + demoFloat + " with leading zeros, " );
      System.out.println( "12 characters before and 2 after the decimal point ... ");
      System.out.println( OutputFormatter.formatFloat( demoFloat, 
                                                        12, 2, true));
      System.out.println( "\nOutputting " + demoFloat + " with leading spaces, ");
      System.out.println( "12 characters before and 4 after the decimal point ... ");
      System.out.println( OutputFormatter.formatFloat( demoFloat, 
                                                        12, 4, false)); 
      System.out.println( "\nOutputting " + demoFloat + " with leading spaces, ");
      System.out.println( "12 characters before and 0 after the decimal point ... ");
      System.out.println( OutputFormatter.formatFloat( demoFloat, 
                                                        12, 0, false)); 
                                                                                                                
      System.out.println( "\nOutputting zero  ");
      System.out.println( OutputFormatter.formatFloat( 0.0F, 
                                                       0, 0, false));     
   } // End main.


} // End OutputFormatterDemonstration.
