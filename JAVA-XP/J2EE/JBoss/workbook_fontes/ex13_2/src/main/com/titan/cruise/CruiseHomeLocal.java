package com.titan.cruise;

import com.titan.ship.ShipLocal;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

// Cruise EJB's local home interface
//
public interface CruiseHomeLocal extends javax.ejb.EJBLocalHome
{
   
   public CruiseLocal create (String name, ShipLocal ship)
   throws javax.ejb.CreateException;
   
   public CruiseLocal findByPrimaryKey (Integer primaryKey)
   throws javax.ejb.FinderException;
   
   // used to remove all beans to prepare example environment
   //
   public java.util.Collection findAll ()
   throws FinderException;   
}
