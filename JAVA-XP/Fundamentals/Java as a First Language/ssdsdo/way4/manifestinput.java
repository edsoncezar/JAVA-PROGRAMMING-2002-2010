// Filename ManifestInput.java
// Supplies input routine for a list of manifest values.
//
// Written for Waypoint 5, 97/8
// Fintan Culwin, V0.1, January 1998.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ManifestInput { 

private String manifestValues[];

private static java.io.BufferedReader keyboard 
                       = new java.io.BufferedReader( 
                                  new InputStreamReader(System.in));


   public ManifestInput( String manifestList[]) { 

      manifestValues = new String[ manifestList.length];
      
      for ( int index = 0;
            index < manifestValues.length;
            index++                      ){ 
          manifestValues[ index] = new String( manifestList[ index]);
      } // End for.
   } // End ManifestInput constructor.



   public int getManifest() throws java.io.IOException { 

   String  possible; 
   boolean listIsExhausted = false;
   boolean foundInList     = false; 
   int     index; 

      System.out.flush();

      try { 

          possible = keyboard.readLine().trim();

          index = 0;
          while ( !listIsExhausted && !foundInList) { 
             if ( possible.equalsIgnoreCase( manifestValues[ index])){ 
               foundInList = true;
             } else { 
               index++;
               if ( index == manifestValues.length) { 
                  listIsExhausted = true;
               } // End if.
             } // End if. 
          } // End while.

          if ( listIsExhausted) { 
             throw new java.lang.Exception();
          } // End if.

      } catch ( java.lang.Exception exception) { 
          throw new java.io.IOException();
      } // End try/ catch.
      return index;
   } // End getManifest.



   public int readManifest( String prompt) { 

   int     localManifest = 0;
   boolean inputNotOk    = true;

      if ( prompt == null) { 
         prompt = new String( "Please input a manifest value ");
      } // End if.

      while ( inputNotOk) { 
         System.out.print( prompt);
         try { 
             localManifest = this.getManifest();
             inputNotOk    = false;
         } catch ( java.io.IOException exception) { 
             System.out.println( "Please enter one of the manifest values ...");
             for ( int index = 0; 
                   index < manifestValues.length;
                   index++                      ){ 
                System.out.print( manifestValues[ index]);
                if ( index == (manifestValues.length -1) ) { 
                   System.out.println( ".\n");
                } else { 
                   System.out.print( ", ");
                } // End if.
             } // End for.
         } // End try/ catch.
      } // End while

      return localManifest;
   } // End readManifest.

} // End class ManifestInput.
