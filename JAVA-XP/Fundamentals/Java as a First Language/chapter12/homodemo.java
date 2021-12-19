// Filename HomoDemo.java.
// Contains a demonstration harness for the
// HomogenousMonitors class.
//
// Written for JFL Book Chapter 12.
// Fintan Culwin, V 0.1, Jan 1997. 

import HomogenousMonitors;

class HomoDemo { 

   public static void main(String args[]) {
   
   HomogenousMonitors demoMonitors; 
   
       System.out.println( "\tHomogeneous File Demonstration\n");
    
       System.out.println( "\nPreparing and showing the monitors ... \n");
       demoMonitors = new HomogenousMonitors();
       System.out.println( demoMonitors);      

       System.out.println( "\nWriting the monitors ... ");
       demoMonitors.writeMonitors( "Homo.dat");

       System.out.println( "\nResetting and showing the monitors ... ");
       demoMonitors.resetMonitors();
       System.out.println( demoMonitors); 
       
       System.out.println( "\nReading and showing the monitors ... ");
       demoMonitors.readMonitors( "Homo.dat");
       System.out.println( demoMonitors);        
    } // End main.
} // End homodemo.
