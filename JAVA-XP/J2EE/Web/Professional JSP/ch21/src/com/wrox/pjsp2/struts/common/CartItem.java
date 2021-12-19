package com.wrox.pjsp2.struts.common;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartItem implements Serializable {
   private int quantity = 0;
   private CD cd = null;
   private double total = (double)0.0;
   private int titleId = 0;
   private HashMap mapping = null;
   private Locale locale = new Locale("EN", "US");  //default
   
   public CartItem() {
      mapping = new HashMap();
   }
   
   public void addCd(CD cd, int quantity) {
      if(cd == null) {
         // Should throw run-time exception.
         return;
      }
      this.cd = cd;
      this.titleId = cd.getTitleId();
      this.quantity = quantity;
      recalculateTotal();
      mapping.put(Constants.TITLE_ID, new Integer(titleId));
   }
   
   private void recalculateTotal() {
      total = cd.getPrice() * quantity;
   }
   
   public CD getCd() {
      return cd;
   }
   
   public int getQuantity() {
      return quantity;
   }
   
   public void setLocale(Locale locale) {
      this.locale = locale;
   }
   
   public double getTotalAsDouble() {
      return total;
   }
   
   public String getTotal() {
      NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
      String totalStr = nf.format(total);
      return totalStr;
   }

   public void decrementQuantity() {
      if(quantity > 0) {
         quantity--;
         recalculateTotal();
      }
   }

   public void incrementQuantity() {
      quantity++;
      recalculateTotal();
   }

   public void changeQuantity(int quantity) {
      if(quantity >= 0) {
         this.quantity = quantity;
         recalculateTotal();
      }
   }
      
   public int getTitleId() {
      return titleId;
   }
}
