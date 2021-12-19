package com.oreilly.struts.storefront.customer.view;

import com.oreilly.struts.storefront.framework.view.BaseView;
/**
 * Mutable data representing a user of the system.
 */
public class UserView extends BaseView {
  private String lastName;
  private String firstName;
  private String emailAddress;
  private String creditStatus;

  public UserView(){
    super();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setCreditStatus(String creditStatus) {
    this.creditStatus = creditStatus;
  }
  public String getCreditStatus() {
    return creditStatus;
  }
}