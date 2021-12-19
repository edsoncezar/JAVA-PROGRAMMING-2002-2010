package com.oreilly.struts.banking.service;

import java.util.*;
import com.oreilly.struts.banking.view.AccountSummaryView;
import com.oreilly.struts.banking.view.AccountDetailView;
/**
 * Used by the example banking application to simulate a datastore.
 */
public class AccountService {
  // A map to hold the users accounts
  private static Map accountMap = new HashMap();
  /**
   * Default Constructor
   */
  public AccountService() {
    super();
  }
  /**
   * Retrieve an AccountDetailView belonging to the user with the acctId.
   */
  public AccountDetailView getAccountDetailView( String acctId ) {
    return (AccountDetailView)accountMap.get( acctId );
  }


  /**
   * Retrieve a collection of AccountSummaryView objects for the user.
   */
  public List getAccountSummaries(String userId) {
    return new ArrayList( accountMap.values() );
  }

  // This data is just dummied up for the example. In a real application,
  // all of this would come from the database.
  static{
    Calendar cal = java.util.GregorianCalendar.getInstance();
    AccountDetailView view1 = new AccountDetailView();
    view1.setAccountId("1211-098");
    view1.setAccountType("CHECKING");
    view1.setAccountBalance(new Double(372.63));
    view1.setAccountName("PERFORMANCE");
    view1.setWithdrawls( new Double(100.00) );
    view1.setDeposits( new Double(472.63) );
    cal.set( 1999, 1, 23 );
    view1.setOpeningDate( cal.getTime() );
    accountMap.put( view1.getAccountId(), view1 );

    AccountDetailView view2 = new AccountDetailView();
    view2.setAccountId("389-341");
    view2.setAccountType("SAVINGS");
    view2.setAccountBalance(new Double(1372.63));
    view2.setAccountName("REGULAR");
    view2.setWithdrawls( new Double(20.00) );
    view2.setDeposits( new Double(1392.63) );
    cal.set( 2000, 5, 3 );
    view2.setOpeningDate( cal.getTime() );
    accountMap.put( view2.getAccountId(), view2 );

  }
}
