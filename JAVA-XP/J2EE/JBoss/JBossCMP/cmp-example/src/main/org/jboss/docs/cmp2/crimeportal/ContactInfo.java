package org.jboss.docs.cmp2.crimeportal;

import java.io.Serializable;

/**
 * ContactInfo contains a cell number, a pager number, and email address.
 */
public class ContactInfo implements Serializable {
   /** The cell phone number. */
   private PhoneNumber cell;

   /** The pager number. */
   private PhoneNumber pager;

   /** The email address */
   private String email;

   /**
    * Creates empty contact info.
    */
   public ContactInfo() {
   }

   /**
    * Gets the cell phone number.
    * @return the cell phone number
    */
   public PhoneNumber getCell() {
      return cell;
   }

   /**
    * Sets the cell phone number.
    * @param cell the new cell phone number.
    */
   public void setCell(PhoneNumber cell) {
      this.cell = cell;
   }

   /**
    * Gets the pager number.
    * @return the pager number
    */
   public PhoneNumber getPager() {
      return pager;
   }

   /**
    * Sets the pager number.
    * @param pager the new pager number.
    */
   public void setPager(PhoneNumber pager) {
      this.pager = pager;
   }

   /**
    * Gets the email address.
    * @return the email address
    */
   public String getEmail() {
      return email;
   }

   /**
    * Sets the email address.
    * @param email the new email address
    */
   public void setEmail(String email) {
      this.email = email.toLowerCase();
   }
   
   public boolean equals(Object o) {
      if(o == this) {
         return true;
      }
      if(!(o instanceof ContactInfo)) {
         return false;
      }
      ContactInfo contactInfo = (ContactInfo)o;
      return isEqual(contactInfo.cell, cell) &&
            isEqual(contactInfo.pager, pager) &&
            isEqual(contactInfo.email, email);
   }
   
   /**
    * Equals helper method that handles null values.
    * @param o1 first object to compare
    * @param o2 second object to compare
    * @return true if both objects are null or they are equivalent; false 
    * otherwise
    */
   private static boolean isEqual(Object o1, Object o2) {
      if(o1 == null) {
         return o2 == null;
      }
      return o1 == o2 || o1.equals(o2);
   }
   
   public int hashCode() {
      int result = 17;    
      result = 37 * result + (cell==null ? 0 : cell.hashCode());
      result = 37 * result + (pager==null ? 0: pager.hashCode());
      result = 37 * result + (email==null ? 0 : email.hashCode());
      return result;
   }
     
   /**
    * Returns the string representation of this contact info.
    * The exact details of the representation are unspecified and subject to
    * change, but the following may be regarded as typical:
    *
    * "[ContactInfo: cell=(123) 456-7890, pager=(123) 456-7890,
    *    email=someone@somewhere.com]"
    */
   public String toString() {
      return "[ContactInfo: " + 
            "cell=" + cell + ", " +
            "pager=" + pager + ", " +
            "email=" + email + "]";
   }
}
