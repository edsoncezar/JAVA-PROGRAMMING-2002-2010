package org.jboss.docs.cmp2.crimeportal;

import java.io.Serializable;

/**
 * PhoneNumber contains a US phone number broken into three seperate number;
 * area code, exchange, and extension.
 */
public class PhoneNumber implements Serializable {
   /** The first three digits of the phone number. */
   private short areaCode;

   /** The middle three digits of the phone number. */
   private short exchange;

   /** The last four digits of the phone number. */
   private short extension;

   /**
    * Creates the phone number (000) 000-0000.
    */
   public PhoneNumber() {
   }

   /**
    * Creates a phone number with the specified areaCode, exchange, and
    * extension.
    * @param areaCode the first three digits of the phone number
    * @param exchange the middle three digits of the phone number
    * @param extension the last four digits of the phone number
    * @throws IllegalArgumentException if any of the numbers is negative, if
    * the area code or excange have more then three digits, or if the extension
    * has more then four digits.
    */
   public PhoneNumber(int areaCode, int exchange, int extension) {
      setAreaCode((short)areaCode);
      setExchange((short)exchange);
      setExtension((short)extension);
   }

   /**
    * Gets the area code, which is the first three digits of the phone number.
    * @return the first three digits of the phone number
    */
   public short getAreaCode() {
      return areaCode;
   }

   /**
    * Sets the area code, which is the first three digits of the phone number.
    * @param areaCode the first three digits of the phone number
    * @throws IllegalArgumentException if the area code is a negative number
    * or if the area code has more then three digits.
    */
   public void setAreaCode(short areaCode) {
      if(areaCode < 0 || areaCode > 999) {
         throw new IllegalArgumentException("area code: " + areaCode);
      }
      this.areaCode = areaCode;
   }

   /**
    * Gets the exchange, which is middle three digits of the phone number.
    * @return the middle three digits of the phone number
    */
   public short getExchange() {
      return exchange;
   }

   /**
    * Sets the exchange, which is middle three digits of the phone number.
    * @param exchange the middle three digits of the phone number
    * @throws IllegalArgumentException if the exchange is a negative number
    * or if the exchange has more then three digits.
    */
   public void setExchange(short exchange) {
      if(exchange < 0 || exchange > 999) {
         throw new IllegalArgumentException("exchange: " + exchange);
      }
      this.exchange = exchange;
   }
   
   /**
    * Gets the extension, which is the last four digits of the phone number.
    * @return the last four digits of the phone number
    */
   public short getExtension() {
      return extension;
   }

   /**
    * Sets the extension, which is the last four digits of the phone number.
    * @param extension the last four digits of the phone number
    * @throws IllegalArgumentException if the extension is a negative number
    * or if the extension has more then four digits.
    */
   public void setExtension(short extension) {
      if(extension < 0 || extension > 9999) {
         throw new IllegalArgumentException("extension: " + extension);
      }
      this.extension = extension;
   }
   
   public boolean equals(Object o) {
      if(o == this) {
         return true;
      }
      if(!(o instanceof PhoneNumber)) {
         return false;
      }
      PhoneNumber phoneNumber = (PhoneNumber)o;
      return phoneNumber.extension == this.extension &&
            phoneNumber.exchange == this.exchange &&
            phoneNumber.areaCode == this.areaCode;
   }
   
   public int hashCode() {
      int result = 17;    
      result = 37 * result + areaCode;
      result = 37 * result + exchange;
      result = 37 * result + extension;
      return result;
   }
     
   /**
    * Returns the string representation of this phone number.
    * The string consists of fourteen characters whose format
    * is "(XXX) YYY-ZZZZ", where XXX is the area code, YYY is
    * the extension, and ZZZZ is the exchange.  (Each of the
    * capital letters represents a single decimal digit.)
    *
    * If any of the three parts of this phone number is too small
    * to fill up its field, the field is padded with leading zeros.
    * For example, if the value of the exchange is 123, the last
    * four characters of the string representation will be "0123".
    *
    * Note that there is a single space separating the closing
    * parenthesis after the area code from the first digit of the
    * exchange.
    */
   public String toString() {
      return "(" + toPaddedString(areaCode, 3) + ") " +
            toPaddedString(exchange, 3) + "-" +
            toPaddedString(extension, 4);
   }

   /**
    * Converts the number to a string padded with zeros on 
    * the lefthand side to be atleas the specified length.
    * Used by toString.
    * @param number the number to convert to a string
    * @param length minimum length of the converted number
    * @return the number as a string of length atleas the length specified. if
    * the number is not long enough, zeros are padded to the left hand side.
    */
   private static String toPaddedString(int number, int length) {
      String s = Integer.toString(number);
      return ZEROS[length - s.length()] + s;
   }

   /**
    * Helper array of strings of zeros. Used by toPaddedString.
    */
   private static String[] ZEROS = 
         {"", "0", "00", "000", "0000", "00000",
         "000000", "0000000", "00000000", "000000000"};
}
