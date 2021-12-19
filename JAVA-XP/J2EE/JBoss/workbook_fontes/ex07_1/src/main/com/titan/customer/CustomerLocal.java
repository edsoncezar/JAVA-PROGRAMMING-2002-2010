package com.titan.customer;

import com.titan.address.*;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Collection;
import java.util.ArrayList;

public interface CustomerLocal extends javax.ejb.EJBLocalObject 
{
   public Name getName();
   public void setName(Name name);
   
   public boolean getHasGoodCredit();
   public void setHasGoodCredit(boolean flag);
   
   public void addPhoneNumber(String number, byte type)
      throws NamingException, CreateException;
   public void removePhoneNumber(byte typeToRemove);
   public void updatePhoneNumber(String number, byte typeToUpdate);
   
   public AddressLocal getHomeAddress();
   public void setHomeAddress(AddressLocal address);
   
   public AddressLocal getBillingAddress();
   public void setBillingAddress(AddressLocal address);
   
   public CreditCardLocal getCreditCard();
   public void setCreditCard(CreditCardLocal card);
   
   public ArrayList getPhoneList();

   public Collection getPhoneNumbers( );
   public void setPhoneNumbers(Collection phones);
}
