package com.wrox.pjsp2.struts.common;

import java.io.Serializable;

/**
 * Taken from struts-example
 * Simple JavaBean to represent label-value pairs for use in collections
 * that are utilized by the <code>&lt;form:options&gt;</code> tag.
 */
public class OptionLabelValue implements Serializable {

   private String label = null; // label displayed to the user
   private String value = null; // value returned to the server
   
   
   /**
   * Construct a new OptionLabelValue with the specified values.
   *
   * @param label The label to be displayed to the user
   * @param value The value to be returned to the server
   */
   public OptionLabelValue(String label, String value) {
      this.label = label;
      this.value = value;
   }
   
   public String getLabel() {
      return label;
   }
   
   public String getValue() {
      return value;
   }
   
   /**
   * Return a string representation of this object.
   */
   public String toString() {
      StringBuffer sb = new StringBuffer("OptionLabelValue[");
      sb.append(this.label);
      sb.append(", ");
      sb.append(this.value);
      sb.append("]");
      return (sb.toString());
   }
   
}
