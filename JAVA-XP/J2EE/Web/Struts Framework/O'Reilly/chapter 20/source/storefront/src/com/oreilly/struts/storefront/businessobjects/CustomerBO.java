package com.oreilly.struts.storefront.businessobjects;

import java.util.List;
import java.util.ArrayList;

public class CustomerBO extends BaseBusinessObject {
  private String email;
  private String password;
  private String creditStatus;
  private String accountStatus;
  private String firstName;
  private String lastName;

  private List submittedOrders = null;

  public CustomerBO() {
    super();
    submittedOrders = new ArrayList();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setCreditStatus(String creditStatus) {
    this.creditStatus = creditStatus;
  }

  public String getCreditStatus() {
    return creditStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }

  public String getAccountStatus() {
    return accountStatus;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getLastName() {
    return lastName;
  }

  public void setSubmittedOrders( List aList ){
    this.submittedOrders = aList;
  }

  public List getSubmittedOrders(){
    return submittedOrders;
  }

  public String toString(){
    StringBuffer buf = new StringBuffer();
    buf.append( "First: " + getFirstName() );
    buf.append( " Last: " + getLastName() );
    return buf.toString();
  }
}