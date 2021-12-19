// Filename Counters/MoneyRegister.java.
// Providing an initial CashRegister model.
//
// Written for JFL book Chapter 5 see text.
// Fintan Culwin, v0.1, January 1997
 
package Counters;
 

 
public class MoneyRegister extends WarningCounter { 

// Largest positive int value, 2147483647 in denary.
private static final int MAXINTEGER = 0x7FFFFFFF;

 private double theFloat;
 private double theTakings; 

    public MoneyRegister( double initialCashFloat ) {  
        super( 0, MAXINTEGER);
        theFloat = initialCashFloat;
    }  // End default Constructor.
    
    
    public void deposit( double amount) { 
        this.count();
        theTakings += amount;
    } // End deposit. 
    
    
    public int numberOfDepositsIs() { 
       return this.numberCountedIs();
    } // End numberOfDepositsIs. 
    
    
    public double takingsIs () { 
        return theTakings;
    } // End takingsIs. 
    
    
    public double totalInRegisterIs() { 
        return this.takingsIs() + theFloat;
    } // End totalInRegisterIs. 
    
    public String toString() { 
       return "Initial float      : " + theFloat            + "\n" +
              "Number of deposits : " + numberOfDepositsIs() + "\n" +
              "Total takings      : " + takingsIs()          + "\n" +
              "Total in register  : " + totalInRegisterIs();
    
    } // End toString. 
    

 } // End MoneyRegister
 
 

