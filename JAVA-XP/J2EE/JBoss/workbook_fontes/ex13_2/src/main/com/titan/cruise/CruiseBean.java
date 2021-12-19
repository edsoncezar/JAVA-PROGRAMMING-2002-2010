package com.titan.cruise;

import java.util.Collection;
import com.titan.ship.ShipLocal;

public abstract class CruiseBean implements javax.ejb.EntityBean
{
   
   private static final int IDGEN_START = (int)System.currentTimeMillis();
   private static int idgen = IDGEN_START;
   public Integer ejbCreate (String name, ShipLocal ship)
   throws javax.ejb.CreateException
   {
      System.out.println ("ejbCreate");
      setId(new Integer(idgen++));
      setName (name);
      return null;
   }
   
   public void ejbPostCreate (String name, ShipLocal ship)
   {
      setShip (ship);
   }
   
   // persistent fields
   //
   public abstract void setId (Integer id);
   public abstract Integer getId ();
   
   public abstract void setName (String name);
   public abstract String getName ( );
   
   public abstract void setShip (ShipLocal ship);
   public abstract ShipLocal getShip ( );
   
   // relationship fields
   //
   public abstract void setReservations (Collection res);
   public abstract Collection getReservations ( );
   
   // EntityBean (empty) implementation
   //
   public void setEntityContext (javax.ejb.EntityContext ec) {}
   public void unsetEntityContext () {}
   public void ejbLoad () {}
   public void ejbStore () {}
   public void ejbActivate () {}
   public void ejbPassivate () {}
   public void ejbRemove () {}
   
}
