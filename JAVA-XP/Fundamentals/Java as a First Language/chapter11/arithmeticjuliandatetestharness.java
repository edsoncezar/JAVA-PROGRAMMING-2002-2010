 // Filename ArithmeticJulianDateTestHarness.java.
 // Client test harness for the JulianDate hierarchy. 
 //
 // Written for JFL Book Chapter 10.
 // Fintan Culwin, V 0.1, Jan 1997. 
 
 
 import JulianDates.JulianDate;
 import JulianDates.JulianDateException;
 
 class ArithmeticJulianDateTestHarness { 
                        
    public static void main(String args[]) {
 
    JulianDate aDate       = new JulianDate();
    JulianDate anotherDate = new JulianDate();
     
       System.out.println( "\t Arithmentic Julian Date Test Harness \n");
 
 
       System.out.println( "\t Test run 3 - today and equals");
       
       System.out.println( "\nTrial 1 - today ");
       try { 
          aDate.today();
          System.out.println( "Today's date obtained without throwing exception.");
          System.out.println( "Todays date is " +  aDate);
       } catch ( JulianDateException exception) { 
          System.out.println( "Exception thrown trying to obtanin today's date ... test failed");
       } // End try/ catch.

       System.out.println( "\nTrial 2 - equals ");
       try { 
          anotherDate.today();
          System.out.println( "Today's date obtained again without without throwing exception.");
       } catch ( JulianDateException exception) { 
          // Do nothing!
       } // End try/ catch.
       System.out.print( aDate);
       if ( aDate.equals( anotherDate)) { 
          System.out.println( " is equal to " + anotherDate);
          System.out.println( "which is correct ... trial passed."); 
       } else { 
          System.out.println( "is not equal to " + anotherDate);
          System.out.println( "which is not correct ... trial failed.");        
       } // end if. 

       System.out.println( "\n\t Test run 4 - inverse testing of " + 
                           " ArithmeticJulianDate actions.\n");
                           
       System.out.println( "\nTrial 1 - tomorrow's yesterday should be today ...");                           
       aDate.today();
       anotherDate.today();
       anotherDate.tomorrow();
       anotherDate.yesterday();
       if ( aDate.equals( anotherDate)) { 
          System.out.println( "it is  trial passed."); 
       } else { 
          System.out.println( "it isn't trial failed.");        
       } // end if.        

       System.out.println( "\nTrial 2 - 1 day before 1 day after today should be today ...");                           
       aDate.today();
       anotherDate.today();
       anotherDate.daysHence( 1);
       anotherDate.daysPast( 1);
       if ( aDate.equals( anotherDate)) { 
          System.out.println( "it is  trial passed."); 
       } else { 
          System.out.println( "it isn't  trial failed.");        
       } // end if.
               
       System.out.println( "\nTrial 3 - yesterday's tomorrow should be today ...");                           
       aDate.today();
       anotherDate.today();
       anotherDate.yesterday();
       anotherDate.tomorrow();
       if ( aDate.equals( anotherDate)) { 
          System.out.println( "it is  trial passed."); 
       } else { 
          System.out.println( "it isn't  trial failed.");        
       } // end if.        

       System.out.println( "\nTrial 4 - 1 day after 1 day before today should be today ...");                           
       aDate.today();
       anotherDate.today();
       anotherDate.daysPast( 1);
       anotherDate.daysHence( 1);
       if ( aDate.equals( anotherDate)) { 
          System.out.println( "it is  trial passed."); 
       } else { 
          System.out.println( "it isn't  trial failed.");        
       } // end if.


       System.out.println( "\nTrial 5 - 109572 days before 109572 after " + 
                           " 01/01/1900 should be 01/01/1900 ...");                           
       aDate       = new JulianDate( 1900, 1, 1, JulianDate.EUROPEAN);
       anotherDate = new JulianDate( 1900, 1, 1, JulianDate.EUROPEAN);
       anotherDate.daysHence( 109572);
       anotherDate.daysPast(  109572);
       if ( aDate.equals( anotherDate)) { 
          System.out.println( "it is  trial passed."); 
       } else { 
          System.out.println( "it isn't  trial failed.");        
       } // end if.
       
       System.out.println( "\nTrial 6 - 109572 days after 109572 before " + 
                           " 31/12/2199 should be 31/12/2199 ...");                           
       aDate       = new JulianDate( 2199, 12, 31, JulianDate.EUROPEAN);
       anotherDate = new JulianDate( 2199, 12, 31, JulianDate.EUROPEAN);
       anotherDate.daysPast(  109572);
       anotherDate.daysHence( 109572);
       if ( aDate.equals( anotherDate)) { 
          System.out.println( "it is  trial passed."); 
       } else { 
          System.out.println( "it isn't  trial failed.");        
       } // end if.       

       System.out.println( "\nTrial 7 - yesterday of 01/01/1900 " + 
                           " should throw an exception ...");                           
       aDate       = new JulianDate( 1900, 1, 1, JulianDate.EUROPEAN);
       try {
          aDate.yesterday();
          System.out.println( "it doesn't trial failed.");
       } catch ( JulianDateException exception) { 
          System.out.println( "it does trial passed.");        
       } // end if.              

       System.out.print( "\nTrial 8 - tomorrow of 31/12/2199 " + 
                         " should throw an exception ...");                           
       aDate       = new JulianDate( 2199, 12, 31, JulianDate.EUROPEAN);
       try {
          aDate.tomorrow();
          System.out.println( "it doesn't trial failed.");
       } catch ( JulianDateException exception) { 
          System.out.println( "it does trial passed.");        
       } // end if.
       
       System.out.println( "\n\t Test run 5 - relational testing of " + 
                           " ArithmeticJulianDate actions.\n");
                           
       System.out.println( "\nTrial 1 - tomorrow should be later than today," + 
                           " by 1 day ... ");                           
       aDate.today();
       anotherDate.today();
       aDate.tomorrow();
       if ( aDate.isLaterThan( anotherDate)) { 
          System.out.print( "it is " );
          if ( aDate.daysBetween( anotherDate) == 1) {
             System.out.println( "by 1 day trial passed."); 
          } else { 
             System.out.println( "by " +  aDate.daysBetween( anotherDate) +
                                 " day trial failed."); 
          } // End if.
       } else { 
          System.out.println( "it isn't trial failed.");        
       } // End if.        
                     
       System.out.println( "\nTrial 2 - yesterday should be earlier than today," + 
                           " by 1 day ... ");                           
       aDate.today();
       anotherDate.today();
       aDate.yesterday();
       if ( aDate.isEarlierThan( anotherDate)) { 
          System.out.print( "it is " );
          if ( aDate.daysBetween( anotherDate) == -1) {
             System.out.println( "by 1 day trial passed."); 
          } else { 
             System.out.println( "by " +  aDate.daysBetween( anotherDate) +
                                 " day trial failed."); 
          } // End if.
       } else { 
          System.out.println( "it isn't trial failed.");        
       } // End if.      
       
       System.out.println( "\n\t Test run 6 - composite testing of " + 
                           " ArithmeticJulianDate actions.\n");
                                  
       System.out.print( "\nTrial 1 - A date constructed from the component" +
                         " parts of another date should be equal to it ... ");
                                       
       aDate.today();
       anotherDate = new JulianDate( aDate.yearIs(), aDate.monthIs(),
                                     aDate.dayIs(),  JulianDate.EUROPEAN);
       if ( aDate.equals( anotherDate)) { 
          System.out.println( "it is  trial passed."); 
       } else { 
          System.out.println( "it isn't  trial failed.");        
       } // end if.                                            
       
       
                
     } // End main.
} // End ArithmeticJulianDateTestHarness.

