package com.wrox.pjsp2.struts.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Category implements Serializable {
   private int categoryId = 0;
   private String categoryName = null;
   private HashMap mapping = new HashMap();  //mappings for link parameters

   public Category() {
   }

   public int getCategoryId() {
      return categoryId;
   }

   public void setCategoryId(int categoryId) {
      this.categoryId = categoryId;
   }

   public String getCategoryName() {
      return categoryName;
   }

   public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
   }

   /**
    * The Mapping HashMap that is passed to the LinkTag in the form
    * tag library.  The HashMap is a collection of parameters that will
    * be used to make a query string and add it to the link.
    */
   public void setMapping() {
      mapping.put(Constants.CATEGORY_ID, new Integer(categoryId));
      mapping.put(Constants.CATEGORY_NAME, categoryName);
   }
   
   public Map getMapping() {
      return mapping;
   }
   
   public String toString() {
      StringBuffer sb = new StringBuffer("Category[categoryId=");
      sb.append(categoryId);
      sb.append(", categoryName=");
      sb.append(categoryName);
      sb.append("]");
      return sb.toString();
   }

}
