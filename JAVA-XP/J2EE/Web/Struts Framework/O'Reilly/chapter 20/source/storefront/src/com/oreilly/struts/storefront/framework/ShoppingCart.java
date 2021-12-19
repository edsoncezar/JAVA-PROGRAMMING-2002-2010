package com.oreilly.struts.storefront.framework;
import java.util.List;
import java.util.LinkedList;
public class ShoppingCart {
  public void addItem(ShoppingCartItem newItem) {
    
    // Check to see if this item is already present, if so, inc the qty
    int size = getSize();
    ShoppingCartItem cartItem = findItem(newItem.getId());
    if(cartItem != null) {
      cartItem.setQuantity(cartItem.getQuantity() + newItem.getQuantity());
    }
    else {
      
      // Must have been a different item, so add it to the cart
      items.add(newItem);
    }
  }
  public void setItems(List otherItems) {
    items.addAll(otherItems);
  }
  public ShoppingCart() {
    items = new LinkedList();
  }
  public void setSize(int size) {
    
  
  // The size is really determined by the list.
  
  // This is needed so that it can act as a JavaBean with a get/set.
  }
  public void empty() {
    items.clear();
  }
  public double getTotalPrice() {
    double total = 0.0;
    int size = items.size();
    for(int i = 0;i < size;i++) {
      total += ((ShoppingCartItem)items.get(i)).getExtendedPrice();
    }
    return total;
  }
  public void removeItem(String itemId) {
    int size = getSize();
    ShoppingCartItem item = findItem(itemId);
    if(item != null) {
      items.remove(item);
    }
  }
  public void removeItems(List itemIds) {
    if(itemIds != null) {
      int size = itemIds.size();
      for(int i = 0;i < size;i++) {
        removeItem((String)itemIds.get(i));
      }
    }
  }
  public void updateQuantity(String itemId, 
                             int newQty) {
    ShoppingCartItem item = findItem(itemId);
    if(item != null) {
      item.setQuantity(newQty);
    }
  }
  public int getSize() {
    return items.size();
  }
  public List getItems() {
    return items;
  }
  private ShoppingCartItem findItem(String itemId) {
    ShoppingCartItem item = null;
    int size = getSize();
    for(int i = 0;i < size;i++) {
      ShoppingCartItem cartItem = (ShoppingCartItem)items.get(i);
      if(itemId.equals(cartItem.getId())) {
        item = cartItem;
        break;
      }
    }
    return item;
  }
  private List items = null;
}
