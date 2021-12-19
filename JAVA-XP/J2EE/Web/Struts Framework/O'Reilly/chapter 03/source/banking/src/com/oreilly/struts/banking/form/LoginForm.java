package com.oreilly.struts.banking.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;
/**
 * This ActionForm is used by the online banking appliation to validate
 * that the user has entered an accessNumber and a pinNumber. If one or
 * both of the fields are empty when validate is called by the
 * ActionServlet, error messages are created.
 */
public class LoginForm extends ActionForm {
  // The user's private id number
  private String pinNumber;

  // The user's access number
  private String accessNumber;
  /**
   * Default Constructor
   */
  public LoginForm() {
    super();
    resetFields();
  }
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();

    // Check and see if the access number is missing
    if(accessNumber == null || accessNumber.length() == 0) {
      ActionError newError = new ActionError("global.error.login.requiredfield", "Access Number");
      errors.add(ActionErrors.GLOBAL_ERROR, newError);
    }

    // Check and see if the pin number is missing
    if(pinNumber == null || pinNumber.length() == 0) {
      ActionError newError = new ActionError("global.error.login.requiredfield", "Pin Number");
      errors.add(ActionErrors.GLOBAL_ERROR, newError);
    }

    // Return the ActionErrors
    return errors;
  }

  // Called by the ActionServlet for a new request
  public void reset(ActionMapping mapping,
                    HttpServletRequest request) {

    // Clear out the access number and pin number fields
    resetFields();
  }
  public void setAccessNumber(String nbr) {
    this.accessNumber = nbr;
  }
  public String getAccessNumber() {
    return this.accessNumber;
  }
  protected void resetFields() {
    this.accessNumber = "";
    this.pinNumber = "";
  }
  public String getPinNumber() {
    return this.pinNumber;
  }
  public void setPinNumber(String nbr) {
    this.pinNumber = nbr;
  }
}
