


import HeterogenousMonitors;


class HeteroDemo { 

   public static void main(String args[]) {
   
   HeterogenousMonitors demoMonitors; 
   
       System.out.println( "\tHeterogeneous File Demonstration\n");
    
       System.out.println( "\nPreparing and showing the monitors ... \n");
       demoMonitors = new HeterogenousMonitors();
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

} // End HeteroDemo.
