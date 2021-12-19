// Way6ADemo.java
//
// ONLY THE saveTheList() METHOD IS INCOMPLETE - ALL 
// OTHER PARTS OF THE FILE SHOULD NOT BE CHANGED!!
//
// Fintan Culwin, V0.1, March 2000

package way6a;

import java.util.*;
import java.io.*; 

public class Way6ADemo { 
 
    public static void main( String argv[]) { 

    Vector demoVect = new Vector(); 

        System.out.println( "Demonstration harness for the " + 
                            "Waypoint 6 version A Assessment"); 

        System.out.println( "\n\nFruging the list . . . ");
        frugTheList( demoVect);
        System.out.println( "\n. . . list frugged showing . . . \n");  
        showTheList( demoVect);    
        System.out.println( "\n. . . saving the list in way6a.dat . . . \n");        
        saveTheList( demoVect);   
        System.out.println( "\n. . . saved\n. . . reloading . . . \n");
        demoVect = loadTheList();
        System.out.println( "\n. . . showing the reloaded list  . . . \n");       
        showTheList( demoVect);                   
        System.out.println( "\nend of demonstration program \n");
    } // End main.

    private static final void frugTheList( Vector frugVect) { 
       frugVect.addElement( new Way6A( 'a', 1));
       frugVect.addElement( new Way6A( 'b', 2));
       frugVect.addElement( new Way6A( 'c', 3));
       frugVect.addElement( new Way6A( 'd', 4));
       frugVect.addElement( new Way6A( 'e', 5));
    } // End frugTheList

    private static final void showTheList( Vector showVect) { 
       for ( int index = 0; index < showVect.size(); index++) { 
           System.out.println( (Way6A) showVect.elementAt( index));
       } // End showTheList
    } // End showTheList


    private static final void saveTheList( Vector saveVect) { 
  
    DataOutputStream writeTo    = null;
    Way6A            currentOne = null;

    try { 
       writeTo = new DataOutputStream( 
                    new FileOutputStream( "way6a.dat"));

       // CODE TO WRITE THE CONTENTS OF THE VECTOR TO THE 
       // FILE NEEDED HERE !!!!!
    } catch (IOException exception) { 
       System.err.println( "\n\n!!! ERROR WRITING FILE !!!!");
       System.exit( -1);
    } // End try/catch
    } // End saveTheList


    private static final Vector loadTheList() { 
  
    DataInputStream readFrom     = null;
    Way6A           fromFile     = null;
    Vector          tempVect     = null;
    boolean         fileFinished = false; 

       try { 
          readFrom = new DataInputStream( 
                       new FileInputStream( "way6a.dat"));
          tempVect = new Vector();
          while ( !fileFinished) { 
             try { 
                fromFile = new Way6A();
                fromFile.readDetails( readFrom);
                tempVect.addElement( fromFile);
             } catch (EOFException exception) { 
                fileFinished = true; 
             } // End try/catch.
           } // End while. 
           readFrom.close();
       } catch (IOException exception) { 
          System.err.println( "\n\n!!! ERROR READING FILE !!!!");
          System.exit( -1);
       } // End try/catch
       return tempVect;
    } // End loadTheList

} // End DangerousDemo.
