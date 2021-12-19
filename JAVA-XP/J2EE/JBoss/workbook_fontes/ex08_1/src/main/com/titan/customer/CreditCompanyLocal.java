package com.titan.customer;

import com.titan.address.*;

public interface CreditCompanyLocal extends javax.ejb.EJBLocalObject 
{

   public String getName();
   public void setName(String name);

   public AddressLocal getAddress();
   public void setAddress(AddressLocal address);

}
