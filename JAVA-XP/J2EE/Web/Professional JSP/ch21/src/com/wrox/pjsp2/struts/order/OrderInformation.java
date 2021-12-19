package com.wrox.pjsp2.struts.order;

import java.io.Serializable;
import com.wrox.pjsp2.struts.common.Address;

public class OrderInformation implements Serializable {
   private String firstName = null;
   private String lastName = null;
   private Address address = null;
   
   public String getFirstName() {
      return firstName;
   }
   
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }
   
   public String getLastName() {
      return lastName;
   }
   
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }
   
   public Address getAddress() {
      return address;
   }
   
   public void setAddress(Address address) {
      this.address = address;
   }
   
   public String toString() {
      StringBuffer sb = new StringBuffer("OrderInformation[");
      if (firstName != null) {
         sb.append("firstName=");
         sb.append(firstName);
      }
      if(lastName != null) {
         sb.append(", lastName=");
         sb.append(lastName);
      }
      if(address != null) {
         sb.append(", address=");
         sb.append(address);
      }
      sb.append("]");
      return sb.toString();
   }
}
