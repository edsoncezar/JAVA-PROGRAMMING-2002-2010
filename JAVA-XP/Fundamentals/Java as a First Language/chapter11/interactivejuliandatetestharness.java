 // Filename JuklianDateTestHarness.java.
 // Client test harness for the JulianDate hierarchy. 
 //
 // Written for JFL Book Chapter 10.
 // Fintan Culwin, V 0.1, Jan 1997. 
 
 
 import JulianDates.JulianDate;
 import JulianDates.JulianDateException;
 
 class InteractiveJulianDateTestHarness { 
 
    public static void main(String args[]) {
 
    JulianDate europeanDate = new JulianDate( JulianDate.EUROPEAN);
    JulianDate americanDate = new JulianDate( JulianDate.AMERICAN);
     
       System.out.println( "\t Interactive Julian Date Test Harness \n");
 
       System.out.println( "\n Trial 1 - attempt to construct 31st Dec 2199.");
       try { 
          System.out.print( "\nPlease enter 31st Jan 2199 as 31/12/2199 ");
          europeanDate.readDate();       
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "The date is " +  europeanDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.

       try { 
          System.out.print( "\nPlease enter 31st Jan 2199 as 12/31/2199 ");
          americanDate.readDate();       
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "The date is " +  americanDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.

         
     } // End main.
} // End InteractiveJulianDateTestHarness.

