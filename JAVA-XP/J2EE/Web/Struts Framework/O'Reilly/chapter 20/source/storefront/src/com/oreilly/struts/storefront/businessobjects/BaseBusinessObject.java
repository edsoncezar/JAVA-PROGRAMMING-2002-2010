package com.oreilly.struts.storefront.businessobjects;

/**
 * An abstract super class that many business objects will extend.
 */
abstract public class BaseBusinessObject implements java.io.Serializable {
  private Integer id;
  private String displayLabel;
  private String description;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDisplayLabel(String label) {
    this.displayLabel = label;
  }

  public String getDisplayLabel() {
    return displayLabel;
  }
}