package com.wrox.pjsp2.struts.common;


import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

public final class User implements Serializable {
   private String firstName = null;
   private String lastName = null;
   private String title = null;
   private String userName = null;
   private String password = null;
   private String confirmPassword = null;
   private String passwordHint = null;
   private Address userAddress = new Address();
   private Address billingAddress = new Address();
   private String email = null;
   
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
   
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

   public String getUserName() {
      return userName;
   }
   
   public void setUserName(String userName) {
      this.userName = userName;
   }
   
   public String getPassword() {
      return password;
   }
   
   public void setPassword(String password) {
      this.password = password;
   }
   
   public String getConfirmPassword() {
      return confirmPassword;
   }
   
   public void setConfirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
   }
   
   public String getPasswordHint() {
      return passwordHint;
   }
   
   public void setPasswordHint(String passwordHint) {
      this.passwordHint = passwordHint;
   }

   public Address getUserAddress() {
      return userAddress;
   }
   
   public void setUserAddress(Address userAddress) {
      this.userAddress = userAddress;
   }
   
   public Address getBillingAddress() {
      return billingAddress;
   }
   
   public void setBillingAddress(Address billingAddress) {
      this.billingAddress = billingAddress;
   }
   
   public String getEmail() {
      return email;
   }
   
   public void setEmail(String email) {
      this.email = email;
   }
   
   public String getDisplayName() {
      StringBuffer tempName = new StringBuffer(32);
      tempName.append(getTitle());
      tempName.append(" ");
      tempName.append(getFirstName());
      tempName.append(" ");
      tempName.append(getLastName());
      return ((tempName.toString()).trim());
   }
   
   public String toString() {
      StringBuffer sb = new StringBuffer("User[userName=");
      sb.append(userName);
      if (title != null) {
         sb.append(", title=");
         sb.append(title);
      }
      if (firstName != null) {
         sb.append(", firstName=");
         sb.append(firstName);
      }
      if(lastName != null) {
         sb.append(", lastName=");
         sb.append(lastName);
      }
      if(password != null) {
         sb.append(", password=");
         sb.append(password);
      }
      if(confirmPassword != null) {
         sb.append(", confirmPassword =");
         sb.append(confirmPassword);
      }
      if(email != null) {
         sb.append(", email=");
         sb.append(email);
      }
      if(userAddress != null) {
         sb.append(", userAddress=");
         sb.append(userAddress);
      }
      if(billingAddress != null) {
         sb.append(", billingAddress=");
         sb.append(billingAddress);
      }
      sb.append("]");
      return sb.toString();
   }
}
