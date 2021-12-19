package com.oreilly.struts.storefront.catalog.view;

import com.oreilly.struts.storefront.framework.view.BaseView;

public class ItemSummaryView extends BaseView {
  /**
   * The base price of the item.
   */
  private Double unitPrice = null;
  /**
   * A short statement listing a feature of the item.
   */
  private String productFeature;
  /**
   * A path to a small image stored on the web server.
   */
  private String smallImageURL;
  private String modelNumber;


  /**
   * A no arg constructor.
   */
  public ItemSummaryView(){
    super();
  }

  public ItemSummaryView(String id, String name, String description,
    Double price, String feature, String smallImageURL ) {
    super( id, name, description );
    setUnitPrice( price );
    setProductFeature( feature );
    setSmallImageURL( smallImageURL );
  }

  public Double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice( Double price ){
    this.unitPrice = price;
  }

  public void setSmallImageURL(String newSmallImageURL) {
    smallImageURL = newSmallImageURL;
  }

  public String getProductFeature() {
    return productFeature;
  }

  public String getSmallImageURL() {
    return smallImageURL;
  }

  public void setProductFeature(String newProductFeature) {
    productFeature = newProductFeature;
  }
  public void setModelNumber(String modelNumber) {
    this.modelNumber = modelNumber;
  }
  public String getModelNumber() {
    return modelNumber;
  }
}