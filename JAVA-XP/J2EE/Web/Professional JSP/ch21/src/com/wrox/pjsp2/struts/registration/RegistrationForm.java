package com.wrox.pjsp2.struts.registration;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.wrox.pjsp2.struts.common.Address;
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.User;

public final class RegistrationForm extends ActionForm {

   private String action = "Create";
   private User user = null;

   public String getAction() {
      return action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public void reset(ActionMapping mapping, HttpServletRequest request) {
      action = "Create";
      this.user = new User();
   }

   public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {

      ActionErrors errors = new ActionErrors();
      // Check for errors in each billingAddress element
      Address shippingAddress = user.getUserAddress();
      Address billingAddress = user.getBillingAddress();
      String address1 = shippingAddress.getAddress1();
      String city = shippingAddress.getCity();
      String state = shippingAddress.getState();
      String zip = shippingAddress.getZip();
      
      if((address1 == null) || (address1.length() < 1)) {
         errors.add("userAddress.address1", 
                     new ActionError("error.address1.required"));
      }
      if((city == null) || (city.length() < 1)) {
         errors.add("userAddress.city", 
                     new ActionError("error.city.required"));
      }
      if((state == null) || (state.length() < 1)) {
         errors.add("userAddress.state", 
                     new ActionError("error.state.required"));
      }
      if((zip == null) || (zip.length() < 1)) {
         errors.add("userAddress.zip", 
                     new ActionError("error.zip.required"));
      }
      String firstName = this.user.getFirstName();
      String lastName = this.user.getLastName();
      if((firstName == null) || (firstName.length() < 1)) {
         errors.add("firstName",
                     new ActionError("error.firstName.required"));
      }
      if((lastName == null) || (lastName.length() < 1)) {
         errors.add("lastName",
                     new ActionError("error.lastName.required"));
      }

      String email = this.user.getEmail();
      
      if((email != null) && (email.length() > 0)) {
         int atSign = email.indexOf("@");
         if ((atSign < 1) || (atSign >= (email.length() - 1)))
            errors.add("email", 
                        new ActionError("error.email.format", email));
      }
      if ((email == null) || (email.length() < 1)) {
         errors.add("email", 
                     new ActionError("error.email.required"));
      }


      if("Create".equals(action)) {      

         String userName = this.user.getUserName();
         if((userName == null) || (userName.length() < 1)) {
            errors.add("userName", 
                        new ActionError("error.userName.required"));
         }

         String password = this.user.getPassword();
         String confirmPassword = this.user.getConfirmPassword();
      
         if((password == null) || (password.length() < 1)) {
            errors.add("password", 
                        new ActionError("error.password.required"));
         }
         
         if((confirmPassword == null) || (confirmPassword.length() < 1)) {
            errors.add("confirmPassword", 
                        new ActionError("error.confirmPassword.required"));
         }
         
         if((password != null) && (confirmPassword != null)) {
            if(password.equals(confirmPassword) == false) {
               errors.add("confirmPassword", 
                           new ActionError("error.confirmPassword.match"));
            }
         }
      }
      return errors;
   }

}
