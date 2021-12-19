package com.wrox.pjsp2.struts.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.wrox.pjsp2.struts.common.Constants;

public class CD implements Serializable {
   private int titleId = 0;
   private String titleName = null;
   private double price = (double)0.0;
   private int categoryId = 0;
   private String artist = null;
   private HashMap mapping = new HashMap();

   public CD() {
   }

   public int getTitleId() {
      return titleId;
   }

   public void setTitleId(int titleId) {
      this.titleId = titleId;
   }

   public String getTitleName() {
      return titleName;
   }

   public void setTitleName(String titleName) {
      this.titleName = titleName;
   }

   public String getArtist() {
      return artist;
   }

   public void setArtist(String artist) {
      this.artist = artist;
   }

   public double getPrice() {
      // change this to use NumberFormat
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public int getCategoryId() {
      return categoryId;
   }

   public void setCategoryId(int categoryId) {
      this.categoryId = categoryId;
   }

   /**
    * The Mapping HashMap that is passed to the LinkTag in the form
    * tag library.  The HashMap is a collection of parameters that will
    * be used to make a query string and add it to the link.
    */
   public void setMapping() {
      mapping.put(Constants.TITLE_ID, new Integer(titleId));
   }
   
   public Map getMapping() {
      return mapping;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer("CD[titleId=");
      sb.append(titleId);
      sb.append(", titleName=");
      sb.append(titleName);
      sb.append(", artist=");
      sb.append(artist);
      sb.append(", price=");
      sb.append(price);
      sb.append(", categoryId=");
      sb.append(categoryId);
      sb.append("]");
      return sb.toString();
   }

}
