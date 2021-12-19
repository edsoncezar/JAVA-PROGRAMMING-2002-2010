package com.oreilly.struts.storefront.businessobjects;

public class LineItemBO extends BaseBusinessObject {
  private OrderBO order;
  private ItemBO item;
  private Double unitPrice;
  private Integer quantity;
  private Integer orderId;
  private Integer itemId;
  private Double extendedPrice;
  private Integer lineItemNumber;

  public LineItemBO() {
    super();
  }

  public void setItem( ItemBO item ){
    this.item = item;
    // Recalculate because the unit price may have changed now.
   calculateUnitPrice();
  }

  public ItemBO getItem(){
    return item;
  }

  /**
   * Set the order instance that is the owner for this line item.
   */
  public void setOrder( OrderBO order ){
    this.order = order;
  }

  public OrderBO getOrder(){
    return order;
  }

  private void setUnitPrice( Double price ){
    unitPrice = price;
  }

  public Double getUnitPrice(){
    return unitPrice;
  }
  /**
   * Set the number of items being ordered. Must always be greater than
   * or equal to zero, or it will default to this value. Maybe this should
   * have been greater than or equal to one, but there may be times where
   * a purchase order might show a zero order of items.
   */
  public void setQuantity( Integer qty ){
    if ( qty != null && qty.intValue() < 0 ){
      this.quantity = null;
    }else{
      this.quantity = qty;
    }
    // Recalculate because the unit price may have changed now.
    calculateUnitPrice();
  }

  public Integer getQuantity(){
    return quantity;
  }

 /**
  * Called when a change has occured either with the item or the quantity.
  */
  private void calculateUnitPrice(){
    // Get the base price of the item
    if ( getItem() != null && getItem().getBasePrice() != null && getQuantity() != null ){
      Double itemBasePrice = getItem().getBasePrice();
      double unitPrice = itemBasePrice.doubleValue() * getQuantity().intValue();
      /* Unit price is base price X quantity. However, you must be very
       * careful when multiplying money values due to rounding errors. You
       * must trim to a certain number of precisions.
       */
      setUnitPrice( BOUtils.getRoundedDouble( unitPrice ) );
    }else{
      /* There was no item found or the price for the item was null. The
       * only thing we can do is to null out the unit price so that it will
       * alert someone to the problem.
       */
       setUnitPrice( null );
    }
  }
  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }
  public Integer getOrderId() {
    return orderId;
  }
  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }
  public Integer getItemId() {
    return itemId;
  }
  public void setExtendedPrice(Double extendedPrice) {
    this.extendedPrice = extendedPrice;
  }
  public Double getExtendedPrice() {
    return extendedPrice;
  }
  public void setLineItemNumber(Integer lineItemNumber) {
    this.lineItemNumber = lineItemNumber;
  }
  public Integer getLineItemNumber() {
    return lineItemNumber;
  }


}