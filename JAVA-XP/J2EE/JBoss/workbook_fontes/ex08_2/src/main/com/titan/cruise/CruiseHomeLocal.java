package com.titan.cruise;

import com.titan.ship.*;
import com.titan.customer.*;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;

// Cruise EJB's local home interface
public interface CruiseHomeLocal extends javax.ejb.EJBLocalHome 
{
   public CruiseLocal create(String name, ShipLocal ship)
      throws javax.ejb.CreateException;
   
   public CruiseLocal findByPrimaryKey(Integer primaryKey)
      throws javax.ejb.FinderException;

   public Collection findByShip(ShipLocal ship)
      throws FinderException;

   public Collection findEmptyReservations()
      throws FinderException;

   public Collection findNotEmptyReservations()
      throws FinderException;

   public Collection findMemberOf(CustomerLocal cust)
      throws FinderException;

   public Collection findNotMemberOf(CustomerLocal cust)
      throws FinderException;
}
