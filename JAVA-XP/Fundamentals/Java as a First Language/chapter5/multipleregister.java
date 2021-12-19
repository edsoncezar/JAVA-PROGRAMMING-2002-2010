// Filename Counters/MultipleRegister.java.
// Providing a CashRegister able to take cash,
// cheque, debit card and credit card deposits.
//
// Written for JFL book Chapter 5 see text.
// Fintan Culwin, v0.1, January 1997
 
package Counters;
 

 
public class MultipleRegister extends CashRegister { 

public static final int BYCASH   = 1;
public static final int BYCHEQUE = 2;
public static final int BYDEBIT  = 3;
public static final int BYCREDIT = 4;

private MoneyRegister moneyRegister  = new MoneyRegister( 0.0);
private MoneyRegister chequeRegister = new MoneyRegister( 0.0);
private MoneyRegister debitRegister  = new MoneyRegister( 0.0);
private MoneyRegister creditRegister = new MoneyRegister( 0.0);
 
   public MultipleRegister( double initialCashFloat ) {  
        super(  initialCashFloat);
    }  // End default Constructor.


   public void deposit( double amount,
                        int    method) { 

      this.deposit( amount);
      switch ( method ) { 
         case BYCASH:
            moneyRegister.deposit( amount);
            break;
         case BYCHEQUE:
            chequeRegister.deposit( amount);
            break;
         case BYDEBIT:
            debitRegister.deposit( amount);
            break;         
         case BYCREDIT:
            creditRegister.deposit( amount);
            break;
         default:
            // unknown deposit method - throw exception!            
      } // End switch.                                                
   } // End deposit. 
   
   public double depositedByCashIs(){ 
       return moneyRegister.takingsIs();
   } // End depositedByCash                       

   public int numberOfCashTransactionsIs() { 
       return moneyRegister.numberOfDepositsIs();
   } // End numberOfCashTransactionsIs.
   
   public double depositedByChequeIs(){ 
       return chequeRegister.takingsIs();
   } // End depositedByCheque                       

   public int numberOfChequeTransactionsIs() { 
       return chequeRegister.numberOfDepositsIs();
   } // End numberOfChequeTransactionsIs.


   public double depositedByDebitIs(){ 
       return debitRegister.takingsIs();
   } // End depositedByDebit                       

   public int numberOfDebitTransactionsIs() { 
       return debitRegister.numberOfDepositsIs();
   } // End numberOfdebitTransactionsIs.


   public double depositedByCreditIs(){ 
       return creditRegister.takingsIs();
   } // End depositedByCredit                       

   public int numberOfCreditTransactionsIs() { 
       return creditRegister.numberOfDepositsIs();
   } // End numberOfCreditTransactionsIs.


   public String toString(){ 
      return super.toString() + "\n" +
                   numberOfCashTransactionsIs()           + 
                   " cash        transactions totalling " + 
                    depositedByCashIs() + ".\n"           +
                   numberOfChequeTransactionsIs()         + 
                   " cheque      transactions totalling " + 
                    depositedByChequeIs() + ".\n"         + 
                   numberOfDebitTransactionsIs()          + 
                   " debit card  transactions totalling " + 
                    depositedByDebitIs() + ".\n"          + 
                   numberOfCreditTransactionsIs()         + 
                   " credit card transactions totalling " + 
                    depositedByCreditIs() + "."; 
   
   } // End toString

} // End MultipleRegister

