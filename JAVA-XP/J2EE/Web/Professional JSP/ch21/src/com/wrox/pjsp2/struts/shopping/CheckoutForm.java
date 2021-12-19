package com.wrox.pjsp2.struts.shopping;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.wrox.pjsp2.struts.common.Constants;

public final class CheckoutForm extends ActionForm {
   private String action = null;
   private int quantity = Constants.UNSELECTED_VALUE;
   private int titleId = Constants.UNSELECTED_VALUE;

   public CheckoutForm() {
      System.out.println("CheckoutForm ctor.");
   }

   public String getAction() {
      return action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public int getTitleId() {
      return titleId;
   }

   public void setTitleId(int titleId) {
      this.titleId = titleId;
   }
   public void reset(ActionMapping mapping, HttpServletRequest request) {
      action = null;
      titleId = Constants.UNSELECTED_VALUE;
      quantity = Constants.UNSELECTED_VALUE;
   }

   public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
      ActionErrors errors = new ActionErrors();
      System.out.println("CheckoutForm.validate()");
      return errors;

   }

}
