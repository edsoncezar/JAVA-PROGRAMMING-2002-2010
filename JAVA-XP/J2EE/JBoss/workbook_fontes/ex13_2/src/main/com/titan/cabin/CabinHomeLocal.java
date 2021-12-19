package com.titan.cabin;

import com.titan.ship.ShipLocal;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

public interface CabinHomeLocal extends javax.ejb.EJBLocalHome
{
   
   public CabinLocal create (Integer id)
   throws CreateException;
   
   public CabinLocal create (Integer id, ShipLocal ship, String name, int count, int level)
   throws CreateException;
   
   public CabinLocal findByPrimaryKey (Integer pk)
   throws FinderException;

   // used to remove all beans to prepare example environment
   //
   public java.util.Collection findAll ()
   throws FinderException;      
}
