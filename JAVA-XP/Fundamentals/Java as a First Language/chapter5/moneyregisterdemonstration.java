//

import Counters.MoneyRegister;

public class MoneyRegisterDemonstration { 


public static void main( String argv[]) { 

   MoneyRegister  demoRegister = new MoneyRegister( 100.00); 

      System.out.println( "\n\t\t Cash Register demonstration \n");
    
      System.out.print( "The Cash Register has been created ");
      System.out.println( "with a cash float of 100.00");

    
      System.out.println( "\nDepositing 5:00, 4.56 and 8.93");
      demoRegister.deposit( 5.00);
      demoRegister.deposit( 4.56);   
      demoRegister.deposit( 8.93); 
                      

      System.out.println( "\n The state of the register is ...");
      System.out.println( demoRegister);

    
   } // End main

} // End MoneyRegisterDemonstration
