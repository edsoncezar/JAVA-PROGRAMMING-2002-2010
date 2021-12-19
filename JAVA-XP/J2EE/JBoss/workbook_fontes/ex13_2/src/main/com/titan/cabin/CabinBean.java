package com.titan.cabin;

import com.titan.ship.ShipLocal;

public abstract class CabinBean implements javax.ejb.EntityBean
{
   
   public Integer ejbCreate (Integer id)
   throws javax.ejb.CreateException
   {
      this.setId (id);
      return null;
   }
   
   public void ejbPostCreate (Integer id) { }
   
   public Integer ejbCreate (Integer id, ShipLocal ship, String name, int count, int level)
   throws javax.ejb.CreateException
   {
      this.setId (id);
      this.setName (name);
      this.setBedCount (count);
      this.setDeckLevel (level);
      return null;
   }
   
   public void ejbPostCreate (Integer id, ShipLocal ship, String name, int count, int level)
   {
      this.setShip (ship);
   }
   
   // Persistent fields
   //
   public abstract void setId (Integer id);
   public abstract Integer getId ();
   
   public abstract void setShip (ShipLocal ship);
   public abstract ShipLocal getShip ( );
   
   public abstract void setName (String name);
   public abstract String getName ( );
   
   public abstract void setBedCount (int count);
   public abstract int getBedCount ( );
   
   public abstract void setDeckLevel (int level);
   public abstract int getDeckLevel ( );
   
   // EntityBean (empty) implementation
   //
   public void setEntityContext (javax.ejb.EntityContext ctx) { }
   public void unsetEntityContext () { }
   
   public void ejbActivate () { }
   public void ejbPassivate () { }
   
   public void ejbLoad () { }
   public void ejbStore () { }
   public void ejbRemove () { }
}
