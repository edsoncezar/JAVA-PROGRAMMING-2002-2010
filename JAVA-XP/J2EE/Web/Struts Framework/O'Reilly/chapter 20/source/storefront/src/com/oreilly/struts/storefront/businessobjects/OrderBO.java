package com.oreilly.struts.storefront.businessobjects;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * The Order Business Object, which represents a purchase order that a customer
 * has or is about to place.
 */
public class OrderBO extends BaseBusinessObject {
  // A list of line items for the order
  private List lineItems;
  // The customer who placed the order
  private CustomerBO customer;
  // The current price of the order
  private double totalPrice;
  // The id of the customer
  private Integer customerId;
  // Whether the order is inprocess, shipped, canceled, etc...
  private String orderStatus;
  // The date and time that the order was received
  private Timestamp submittedDate;

  /**
   * Default NoArg Constructor
   */
  public OrderBO() {
    super();
    // Initialize the line items as a linked list to keep them in order
    lineItems = new LinkedList();
  }
  /**
   * Additional constructor that takes the neccessary arguments to initialize
   */
  public OrderBO( Integer id, Integer custId, String orderStatus,
                  Timestamp submittedDate, double price ){
    this.setId(id);
    this.setCustomerId(custId);
    this.setOrderStatus(orderStatus);
    this.setSubmittedDate(submittedDate);
    this.setTotalPrice(price);
  }

  public void setCustomer( CustomerBO owner ){
    customer = owner;
  }

  public CustomerBO getCustomer(){
    return customer;
  }

  public double getTotalPrice(){
    return this.totalPrice;
  }

  private void setTotalPrice( double price ){
    this.totalPrice = price;
  }

  public void setLineItems( List lineItems ){
    this.lineItems = lineItems;
  }

  public List getLineItems(){
    return lineItems;
  }

  public void addLineItem( LineItemBO lineItem ){
    lineItems.add( lineItem );
  }

  public void removeLineItem( LineItemBO lineItem ){
    lineItems.remove( lineItem );
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setSubmittedDate(Timestamp submittedDate) {
    this.submittedDate = submittedDate;
  }

  public Timestamp getSubmittedDate() {
    return submittedDate;
  }

  /**
   * Recalculate the price of the entire order. If one or more of the line
   * items doesn't have a price for some reason, set the totalPrice of the
   * order to null, since it can't be truely calculated. By setting it to null,
   * even if a few of the line items have a price, this will alert someone
   * that there's a problem. This should also prevent orders from being
   * accepted by the system when there was a problem with one or more line
   * items. If this wasn't done in this manner, a customer might receive
   * goods for free.
   */
  private void recalculatePrice(){
    double totalPrice = 0.0;

    if ( getLineItems() != null ){
      Iterator iter = getLineItems().iterator();
      while( iter.hasNext() ){
        // Get the price for the next line item and make sure it's not null
        Double lineItemPrice = ((LineItemBO)iter.next()).getUnitPrice();
        // Check for an invalid lineItem. If found, return null right here
        if (lineItemPrice == null){
          return;
        }
        // It's not null, so add it to the total
        totalPrice += lineItemPrice.doubleValue();
      }
      // Set the price for the order from the calcualted value
      setTotalPrice( totalPrice );
    }
  }
}