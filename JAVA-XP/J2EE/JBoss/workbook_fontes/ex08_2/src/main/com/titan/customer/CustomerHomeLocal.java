package com.titan.customer;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

public interface CustomerHomeLocal extends javax.ejb.EJBLocalHome 
{
   public CustomerLocal create(Integer id)
      throws CreateException;
   
   public CustomerLocal findByPrimaryKey(Integer id)
      throws FinderException;


   public Collection findAllCustomersWithReservations()
      throws FinderException;

   public Collection findDistinctCustomersWithReservations()
      throws FinderException;

   public Collection findByAmericanExpress()
      throws FinderException;

   public Collection findByGoodCredit()
      throws FinderException;

   public Collection findInStates()
      throws FinderException;

   public Collection findNotInStates()
      throws FinderException;

   public Collection findHomeAddressIsNull()
      throws FinderException;

   public Collection findHomeAddressIsNotNull()
      throws FinderException;

   public Collection findHyphenatedLastNames()
      throws FinderException;

   public Collection findByLastNameLength()
      throws FinderException;


   // query wrappers for ejbSelects

   public Collection queryByCity(String city, String state)
      throws FinderException;
   
   
}

