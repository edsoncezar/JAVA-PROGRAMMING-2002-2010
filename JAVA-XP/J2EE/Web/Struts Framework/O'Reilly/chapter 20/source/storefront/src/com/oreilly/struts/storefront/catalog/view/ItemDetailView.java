package com.oreilly.struts.storefront.catalog.view;
/**
 * Represents an Item that can be sold.
 */
public class ItemDetailView extends ItemSummaryView {

  public ItemDetailView(String id, String name, String desc, Double price,
    String largeImageURL, String smallImageURL, String productFeature) {
    super( id, name, desc, price, productFeature, smallImageURL);
    setLargeImageURL( largeImageURL );
  }

  public String getLargeImageURL() {
    return largeImageURL;
  }

  public void setLargeImageURL(String newLargeImageURL) {
    largeImageURL = newLargeImageURL;
  }
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("id: ");
    buf.append(getId());
    buf.append(" name: ");
    buf.append(getName());
    buf.append(" desc: ");
    buf.append(getDescription());
    buf.append(" url: ");
    buf.append( getLargeImageURL());
    buf.append(" price: ");
    buf.append(getUnitPrice());
    return buf.toString();
  }

  /**
   * No Arg Constructor
   */
  public ItemDetailView() {
    super();
  }


  /**
   * A path to a large image stored on the web server.
   */
  private String largeImageURL;
}