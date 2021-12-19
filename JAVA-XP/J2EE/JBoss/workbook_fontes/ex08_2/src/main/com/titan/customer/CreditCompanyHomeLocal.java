package com.titan.customer;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

public interface CreditCompanyHomeLocal extends javax.ejb.EJBLocalHome 
{
   public CreditCompanyLocal create(Integer id, String name)
      throws CreateException;
   
   public CreditCompanyLocal findByPrimaryKey(Integer id)
      throws FinderException;

}

