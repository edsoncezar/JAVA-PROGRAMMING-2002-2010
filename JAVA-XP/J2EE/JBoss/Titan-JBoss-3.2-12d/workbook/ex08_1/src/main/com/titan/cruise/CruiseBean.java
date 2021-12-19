package com.titan.cruise;

import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import com.titan.ship.*;
import java.util.Collection;

public abstract class CruiseBean implements javax.ejb.EntityBean 
{
    
   private static final int IDGEN_START = (int)System.currentTimeMillis();
   private static int idgen = IDGEN_START;
   public Integer ejbCreate(String name, ShipLocal ship) 
      throws CreateException
   {
      System.out.println("ejbCreate");
      setId(new Integer(idgen++));
      setName(name);
      return null;
   }

   public void ejbPostCreate(String name, ShipLocal ship) 
   {
      setShip(ship);
   }

   // persistent fields

   public abstract void setId(Integer id);
   public abstract Integer getId();
   public abstract void setName(String name);
   public abstract String getName( );

   public abstract void setShip(ShipLocal ship);
   public abstract ShipLocal getShip( );

   // relationship fields

   public abstract void setReservations(Collection res);
   public abstract Collection getReservations( );

   // standard call back methods
    
   public void setEntityContext(EntityContext ec){}
   public void unsetEntityContext(){}
   public void ejbLoad(){}
   public void ejbStore(){}
   public void ejbActivate(){}
   public void ejbPassivate(){}
   public void ejbRemove(){}

}
