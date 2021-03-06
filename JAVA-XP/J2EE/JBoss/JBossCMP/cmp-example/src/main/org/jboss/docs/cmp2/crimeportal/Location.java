package org.jboss.docs.cmp2.crimeportal;

import java.util.Set;
import javax.ejb.EJBLocalObject;

public interface Location extends EJBLocalObject
{
   /**
    * Gets the id, which is an autogenerated primary key.
    * @return the id of this address
    */
   public abstract Integer getLocationId();

    /**
    * Gets the address description.
    * @return description of the address
    */
   public abstract String getDescription();

   /**
    * Sets the address description.
    * @param description new address description
    */
   public abstract void setDescription(String description);

   /**
    * Gets the address street.
    * @return street of the address
    */
   public abstract String getStreet();

   /**
    * Sets the address street.
    * @param street new address street
    */
   public abstract void setStreet(String street);
 
   /**
    * Gets the city of the address.
    * @return city of the address
    */
   String getCity();

   /**
    * Sets the city for the address.
    * @param city the new city for the address
    */
   void setCity(String city);

   /**
    * Gets the two letter state code for the address.
    * @return the two letter state code
    */
   String getState();

   /**
    * Sets the two letter state code for the address.
    * @param state the new two letter state code for the address
    * @throws IllegalArgumentException if the state does not contain 
    * exactally two letters
    */
   void setState(String state);
   
   /**
    * Gets the zip code of the address.
    * @return the address zip code
    */
   int getZipCode();

   /**
    * Sets the zip code of the address.
    * @param zipCode the new zip code for the address
    * @throws IllegalArgumentException if the zip code is a negative number
    * or if the zipCode has more then five digits.
    */
   void setZipCode(int zipCode);
}
