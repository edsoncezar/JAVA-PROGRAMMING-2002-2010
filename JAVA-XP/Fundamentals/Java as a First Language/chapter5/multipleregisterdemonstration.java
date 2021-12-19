// Filename MultipleRegisterdemonstration.java.
// demonstration of the initial CashRegister model.
//
// Written for JFL book Chapter 5 see text.
// Fintan Culwin, v0.1, January 1997

import Counters.MultipleRegister;

public class MultipleRegisterDemonstration { 


public static void main( String argv[]) { 

   MultipleRegister  demoRegister = new MultipleRegister( 100.00); 

      System.out.println( "\n\t\t Cash Register demonstration \n");
    
      System.out.print( "The Cash Register has been created ");
      System.out.println( "with a cash float of 100.00");

    
      System.out.println( "\nDepositing 5:00, 4.56 and 8.93 by cash ... ");
      demoRegister.deposit( 5.00, MultipleRegister.BYCASH);
      demoRegister.deposit( 4.56, MultipleRegister.BYCASH);   
      demoRegister.deposit( 8.93, MultipleRegister.BYCASH); 
                      
      System.out.println( "\nDepositing 3.22 by cheque ... ");
      demoRegister.deposit( 3.22, MultipleCashRegister.BYCHEQUE);
      
      System.out.println( "\nDepositing 9.75, 0.56 by debit card ... ");
      demoRegister.deposit( 9.75, MultipleRegister.BYDEBIT); 
      demoRegister.deposit( 0.56, MultipleRegister.BYDEBIT);  
      
      System.out.println( "\nDepositing 34.67 by credit card ... ");
      demoRegister.deposit( 34.67, MultipleRegister.BYCREDIT);           
           
      System.out.println( "\nThe state of the register is ...\n");
      System.out.println( demoRegister);

    
   } // End main

} // End MultipleRegisterDemonstration
