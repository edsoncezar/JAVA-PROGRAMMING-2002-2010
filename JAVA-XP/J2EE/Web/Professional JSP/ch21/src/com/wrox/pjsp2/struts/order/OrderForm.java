package com.wrox.pjsp2.struts.order;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public final class OrderForm extends ActionForm {
   private String action = null;
   private String creditCardType = null;
   private String creditCardNumber = null;
   private String expireMonth = null;
   private String expireYear = null;
   private OrderInformation shippingInformation = null;
   private OrderInformation billingInformation = null;
   private String orderNumber = null;

   public OrderForm() {
      System.out.println("Order ctor.");
   }

   public String getAction() {
      return action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public String getCreditCardType() {
      return creditCardType;
   }
   
   public void setCreditCardType(String creditCardType) {
      this.creditCardType = creditCardType;
   }
   
   public String getCreditCardNumber() {
      return creditCardNumber;
   }
   
   public void setCreditCardNumber(String creditCardNumber) {
      this.creditCardNumber = creditCardNumber;
   }
   
   public String getExpireMonth() {
      return expireMonth;
   }
   
   public void setExpireMonth(String expireMonth) {
      this.expireMonth = expireMonth;
   }
   
   public String getExpireYear() {
      return expireYear;
   }
   
   public void setExpireYear(String expireYear) {
      this.expireYear = expireYear;
   }
   
   public OrderInformation getShippingInformation() {
      return shippingInformation;
   }
   
   public void setShippingInformation(OrderInformation shippingInformation) {
      this.shippingInformation = shippingInformation;
   }
   
   public OrderInformation getBillingInformation() {
      return billingInformation;
   }
   
   public void setBillingInformation(OrderInformation billingInformation) {
      this.billingInformation = billingInformation;
   }
   
   public String getOrderNumber() {
      return orderNumber;
   }
   
   public void setOrderNumber(String orderNumber) {
      this.orderNumber = orderNumber;
   }
   
   public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
      ActionErrors errors = new ActionErrors();
      return errors;
   }

}
