package com.oreilly.struts.banking.service;

import com.oreilly.struts.banking.view.UserView;
/**
 * Used by the example banking application to simulate a security service.
 */
public class SecurityService implements IAuthentication {

  /**
   * The login method is called when a user wishes to login to
   * the online banking application.
   * @param accessNumber The account access number.
   * @param pinNumber The account private id number.
   * @returns A ValueHolder object representing the user's personal data.
   * @throws InvalidLoginException if the credentials are invalid.
   */
  public UserView login(String accessNbr, String pinNbr)
    throws InvalidLoginException {

    // A real security service would check the login against a security realm
    // This example is hard coded to only let in 123/456
    if(
      (accessNbr != null && accessNbr.equalsIgnoreCase("123")) &&
      (pinNbr != null && pinNbr.equalsIgnoreCase("456")) ){

      /* Dummy a UserView for this example.
       * This data/object would typically come from the business layer
       * after proper authentication/authorization had been done.
       */
      UserView newView = new UserView( "John", "Doe" );
      newView.setId( "39017" );
      return newView;
    }
    else {
      // If the user enters anything other than john/doe, throw this exception
      throw new InvalidLoginException();
    }
  }
}
