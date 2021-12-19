package com.wrox.pjsp2.struts.common;

import java.io.Serializable;

public class Address implements Serializable {
   private String address1 = null;
   private String address2 = null;
   private String city = null;
   private String state = null;
   private String zip = null;
   
   public Address() {
   }

   public void setAddress1(String address1) {
      this.address1 = address1;
   }

   public String getAddress1() {
      return address1;
   }

   public void setAddress2(String address2) {
      this.address2 = address2;
   }

   public String getAddress2() {
      return address2;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCity() {
      return city;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getState() {
      return state;
   }

   public void setZip(String zip) {
      this.zip = zip;
   }

   public String getZip() {
      return zip;
   }
  
   public String toString() {
      StringBuffer sb = new StringBuffer("Address[");
      if(address1 != null) {
         sb.append("address1=");
         sb.append(address1);
      }
      if(address2 != null) {
         sb.append(", address2=");
         sb.append(address2);
      }
      if(city != null) {
         sb.append(", city=");
         sb.append(city);
      }
      if(state != null) {
         sb.append(", state=");
         sb.append(state);
      }
      if(zip != null) {
         sb.append(", zip=");
         sb.append(zip);
      }
      sb.append("]");
      return sb.toString();
   }
}
