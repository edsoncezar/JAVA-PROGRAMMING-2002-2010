 // Filename JuklianDateTestHarness.java.
 // Client test harness for the JulianDate hierarchy. 
 //
 // Written for JFL Book Chapter 10.
 // Fintan Culwin, V 0.1, Jan 1997. 
 
 
 import JulianDates.JulianDate;
 import JulianDates.JulianDateException;
 
 class JulianDateTestHarness { 
 
    public static void main(String args[]) {
 
    JulianDate testDate;
 
       System.out.println( "\t Julian Date Test Harness \n");
 
 
       System.out.println( "Test run 1 - default constructor");
       try { 
          testDate = new JulianDate();
          System.out.println( "Date constructed without throwing exception.");
          System.out.println( testDate);
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed");
       } // End try/ catch.




       System.out.println( "\n\t Test run 2 - alternative constructor \n");

       System.out.println( "\n Trial 1 - attempt to construct 31/12/1899.");
       try { 
          testDate = new JulianDate( 1899, 12, 31, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.

       System.out.println( "\n Trial 2 - attempt to construct 1/1/1900.");
       try { 
          testDate = new JulianDate( 1900, 1, 1, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.
       
       System.out.println( "\n Trial 3 - attempt to construct 31/12/2199.");
       try { 
          testDate = new JulianDate( 2199, 12, 12, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.       
       
       System.out.println( "\n Trial 4 - attempt to construct 1/2/2200.");
       try { 
          testDate = new JulianDate( 2200, 1, 1, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.
       
       
       System.out.println( "\n Trial 5 - attempt to construct 1/0/1900.");
       try { 
          testDate = new JulianDate( 1900, 0, 1, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.       

       System.out.println( "\n Trial 6 - attempt to construct 1/13/1900.");
       try { 
          testDate = new JulianDate( 1900, 13, 1, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.

       System.out.println( "\n Trial 7 - attempt to construct 0/1/1900.");
       try { 
          testDate = new JulianDate( 1900, 1, 0, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.       


       System.out.println( "\n Trial 8 - attempt to construct 32/1/1900.");
       try { 
          testDate = new JulianDate( 1900, 1, 32, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.

       
       System.out.println( "\n Trial 9 - attempt to construct 31/1/1900.");
       try { 
          testDate = new JulianDate( 1900, 1, 31, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.   
           
       System.out.println( "\n Trial 10 - attempt to construct 31/4/1900.");
       try { 
          testDate = new JulianDate( 1900, 4, 31, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.

       
       System.out.println( "\n Trial 11 - attempt to construct 30/4/1900.");
       try { 
          testDate = new JulianDate( 1900, 4, 30, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.
       
          
       System.out.println( "\n Trial 12 - attempt to construct 30/2/1904.");
       try { 
          testDate = new JulianDate( 1904, 2, 30, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.   
       
       
       System.out.println( "\n Trial 13 - attempt to construct 29/2/1900.");
       try { 
          testDate = new JulianDate( 1904, 2, 29, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.   
                  
       System.out.println( "\n Trial 14 - attempt to construct 29/2/1905.");
       try { 
          testDate = new JulianDate( 1905, 2, 29, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test failed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test passed.");
       } // End try/ catch.          
       
       
       System.out.println( "\n Trial 15 - attempt to construct 28/2/1905.");
       try { 
          testDate = new JulianDate( 1905, 2, 28, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.   
                  
       
       System.out.println( "\n Trial 16 - attempt to construct 29/2/2000.");
       try { 
          testDate = new JulianDate( 2000, 2, 29, JulianDate.EUROPEAN);
          System.out.println( "No exception thrown,  ... test passed.");
          System.out.println( "Date is " + testDate + ".");
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown ... test failed.");
       } // End try/ catch.   
                  
           
     } // End main.
} // End JulianDateTestHarness.

