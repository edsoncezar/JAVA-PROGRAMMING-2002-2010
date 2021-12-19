package com.wrox.pjsp2.struts.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.text.NumberFormat;

public class ShoppingCart implements Serializable {
   private HashMap cartItems = null;
   private int itemCount = 0;
   private Locale locale = new Locale("EN", "US");  //default
   
   public ShoppingCart() {
      cartItems = new HashMap();
   }
   
   public int getItemCount() {
      return itemCount;
   }
   
   private void setItemCount() {
      itemCount = cartItems.size();
   }
   
   public Set entrySet() {
      return cartItems.entrySet();
   }
   
   public Collection values() {
      return cartItems.values();
   }
   
   public void addCartItem(CartItem cartItem) {
      cartItems.put(new Integer(cartItem.getTitleId()), cartItem);
      setItemCount();
   }

   public void deleteCartItem(CartItem cartItem) {
      Object oldItem = cartItems.remove(new Integer(cartItem.getTitleId()));
      if(oldItem != null) {
         setItemCount();
      }
   }

   public CartItem getCartItem(int titleId) {
      return (CartItem)cartItems.get(new Integer(titleId));
   }
   
   public boolean isCartItemPresent(int titleId) {
      return cartItems.containsKey(new Integer(titleId));
   }
   
   // Needed for the struts-logic:iterate tag
   public CartItem[] getCartItems() {
      Collection collection = cartItems.values();
      CartItem[] cartItemArray = new CartItem[0];
      cartItemArray = (CartItem[])collection.toArray(cartItemArray);
      return cartItemArray;
   }

   public void setLocale(Locale locale) {
      this.locale = locale;
   }
      
   public String getTotal() {
      Iterator it = cartItems.values().iterator();
      double total = (double) 0.0;
      CartItem anItem = null;
      while(it.hasNext()) {
         anItem = (CartItem) it.next();
         total += anItem.getTotalAsDouble();
      }
      NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
      String totalStr = nf.format(total);
      return totalStr;
   }
}
