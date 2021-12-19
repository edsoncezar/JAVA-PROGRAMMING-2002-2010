package com.titan.address;

import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.titan.customer.CustomerLocal;

// Address EJB's local home interface
public interface AddressHomeLocal extends javax.ejb.EJBLocalHome 
{  
   public AddressLocal createAddress(String street, String city, 
                                     String state,  String zip)
      throws javax.ejb.CreateException;
   
   public AddressLocal findByPrimaryKey(Integer primaryKey)
      throws javax.ejb.FinderException;

}
