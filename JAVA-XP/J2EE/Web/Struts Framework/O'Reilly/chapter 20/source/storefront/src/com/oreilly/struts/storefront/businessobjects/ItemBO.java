package com.oreilly.struts.storefront.businessobjects;

public class ItemBO extends BaseBusinessObject {
  private Double basePrice;
  private String manufacturer;
  private String sku;
  private String upc;
  private String sellingUOM;
  private Integer minumumSellingUnits;
  private Integer onHandQuantity;
  private String feature1;
  private String feature2;
  private String feature3;
  private String smallImageURL;
  private String largeImageURL;
  private String modelNumber;

  public ItemBO() {
    super();
  }

  public ItemBO( Integer newId ){
   setId( newId );
  }

  public void setBasePrice(Double basePrice) {
    this.basePrice = basePrice;
  }

  public Double getBasePrice() {
    return basePrice;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getSku() {
    return sku;
  }
  public void setUpc(String upc) {
    this.upc = upc;
  }
  public String getUpc() {
    return upc;
  }
  public void setSellingUOM(String sellingUOM) {
    this.sellingUOM = sellingUOM;
  }
  public String getSellingUOM() {
    return sellingUOM;
  }
  public void setMinumumSellingUnits(Integer minumumSellingUnits) {
    this.minumumSellingUnits = minumumSellingUnits;
  }
  public Integer getMinumumSellingUnits() {
    return minumumSellingUnits;
  }

  public void setOnHandQuantity(Integer onHandQuantity) {
    this.onHandQuantity = onHandQuantity;
  }
  public Integer getOnHandQuantity() {
    return onHandQuantity;
  }
  public void setFeature1(String feature1) {
    this.feature1 = feature1;
  }
  public String getFeature1() {
    return feature1;
  }
  public void setFeature2(String feature2) {
    this.feature2 = feature2;
  }
  public String getFeature2() {
    return feature2;
  }
  public void setFeature3(String feature3) {
    this.feature3 = feature3;
  }
  public String getFeature3() {
    return feature3;
  }

  public String toString(){
    StringBuffer buf = new StringBuffer();
    buf.append( "id:"+getId() );
    buf.append( " displayLabel:"+getDisplayLabel());
    buf.append( " baseprice:"+getBasePrice());
    return buf.toString();
  }
  public void setSmallImageURL(String smallImageURL) {
    this.smallImageURL = smallImageURL;
  }
  public String getSmallImageURL() {
    return smallImageURL;
  }
  public void setLargeImageURL(String largeImageURL) {
    this.largeImageURL = largeImageURL;
  }
  public String getLargeImageURL() {
    return largeImageURL;
  }
  public void setModelNumber(String modelNumber) {
    this.modelNumber = modelNumber;
  }
  public String getModelNumber() {
    return modelNumber;
  }
}