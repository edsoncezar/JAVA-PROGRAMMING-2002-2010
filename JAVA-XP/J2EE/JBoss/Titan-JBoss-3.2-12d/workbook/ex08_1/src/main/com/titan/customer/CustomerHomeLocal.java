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

   public CustomerLocal findByName(String lastName, String firstName)
      throws FinderException;

   public Collection findByGoodCredit()
      throws FinderException;


   // query wrappers for ejbSelects
   
   public Collection queryLastNames()
      throws FinderException;

   public Collection queryCreditCards()
      throws FinderException;

   public Collection queryCities()
      throws FinderException;

   public Collection queryCreditCompanyAddresses()
      throws FinderException;

   public Collection queryCreditCompanyCities()
      throws FinderException;

   public Collection queryReservations()
      throws FinderException;
   
   public Collection queryCruises()
      throws FinderException;
   
   public Collection queryShips()
      throws FinderException;
   
}

