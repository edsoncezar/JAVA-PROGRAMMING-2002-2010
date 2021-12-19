package com.wrox.pjsp2.struts.logon;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LogonForm extends ActionForm {
   private String password = null;
   private String userName = null;

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public void reset(ActionMapping mapping, HttpServletRequest request) {
      userName = null;
      password = null;
   }

   public ActionErrors validate(ActionMapping mapping,
                             HttpServletRequest request) {
      ActionErrors errors = new ActionErrors();
      if((userName == null) || (userName.length() < 1)) {
         errors.add("userName",
             new ActionError("error.userName.required"));
      }
      if((password == null) || (password.length() < 1)) {
         errors.add("password",
             new ActionError("error.password.required"));
      }

      return errors;
   }
}
