package com.titan.ship;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;

// Ship EJB's local home interface
public interface ShipHomeLocal extends javax.ejb.EJBLocalHome 
{
   public ShipLocal create(Integer primaryKey, String name, double tonnage)
      throws javax.ejb.CreateException;
   
   public ShipLocal findByPrimaryKey(Integer primaryKey)
      throws javax.ejb.FinderException;

   public Collection findByTonnage100000()
      throws javax.ejb.FinderException;

   public Collection findByTonnageBetween()
      throws javax.ejb.FinderException;

   public Collection findByTonnageNotBetween()
      throws javax.ejb.FinderException;
}
