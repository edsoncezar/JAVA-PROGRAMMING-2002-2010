package com.oreilly.struts.storefront.businessobjects;

import java.util.List;
import java.util.ArrayList;

public class CatalogBO extends BaseBusinessObject {
  String isFeatured = null;
  List assignedItems = null;

  public CatalogBO() {
    super();
    assignedItems = new ArrayList();
  }

  public void setIsFeatured( String featuredCatalog ){
    this.isFeatured = featuredCatalog;
  }

  public String getIsFeatured(){
    if( this.isFeatured == null ){
      this.isFeatured = "N";
    }
    return this.isFeatured;
  }

  public void setAssignedItems( List aList ){
    this.assignedItems = aList;
  }

  public List getAssignedItems(){
    return this.assignedItems;
  }

  public String toString(){
    StringBuffer buf = new StringBuffer();
    buf.append( "id:"+getId() );
    buf.append( " displayLabel:"+getDisplayLabel());
    buf.append( " isFeatured:"+getIsFeatured());
    return buf.toString();
  }
}