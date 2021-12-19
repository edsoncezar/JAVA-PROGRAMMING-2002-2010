package com.oreilly.struts.storefront.framework;

import com.oreilly.struts.storefront.catalog.view.ItemDetailView;

/**
 * This wraps the actual item class to help decouple the shopping cart from
 * the actual items it stores.
 */
public class ShoppingCartItem {
  public void setItem(ItemDetailView newItem) {
    item = newItem;
    calculateExtendedPrice();
  }
  public void setExtendedPrice(double newPrice) {
    extendedPrice = newPrice;
  }
  public String getDescription() {
    return item.getDescription();
  }
  public String getId() {
    return item.getId();
  }
  public String getName() {
    return item.getName();
  }
  public ShoppingCartItem(ItemDetailView item, int qty) {
    this.item = item;
    this.quantity = qty;
    calculateExtendedPrice();
  }
  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int newQuantity) {
    quantity = newQuantity;
    calculateExtendedPrice();
  }
  public ItemDetailView getItem() {
    return item;
  }
  public Double getUnitPrice() {
    return item.getUnitPrice();
  }
  public double getExtendedPrice() {
    return extendedPrice;
  }
  private void calculateExtendedPrice() {
    if ( item.getUnitPrice() != null ){
      extendedPrice = item.getUnitPrice().doubleValue() * getQuantity();
    }
  }

  // Extended price is unit price * qty
  private double extendedPrice = 0.0;
  private ItemDetailView item = null;

  // Default qty to 1
  private int quantity = 1;
}
