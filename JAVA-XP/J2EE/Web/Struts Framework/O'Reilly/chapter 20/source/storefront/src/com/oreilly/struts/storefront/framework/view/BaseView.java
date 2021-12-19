package com.oreilly.struts.storefront.framework.view;
import java.sql.Timestamp;

/**
 *
 */
public class BaseView implements java.io.Serializable {
private String id;
  private Timestamp timeCreated = null;
  private String description;
  private String name;

  public BaseView() {
    super();
    setTimeCreated(new Timestamp(System.currentTimeMillis()));
  }

  public BaseView(String id,
                  String name,
                  String desc) {
    this();
    this.id = id;
    this.name = name;
    this.description = desc;
  }

  public void setName(String name) {
    this.name = name;
  }
  public void setTimeCreated(Timestamp now) {
    timeCreated = now;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
  public String getId() {
    return id;
  }
  public Timestamp getTimeCreated() {
    return timeCreated;
  }

}
