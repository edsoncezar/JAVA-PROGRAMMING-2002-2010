package com.titan.ship;

public abstract class ShipBean implements javax.ejb.EntityBean
{
   
   public Integer ejbCreate (Integer primaryKey, String name, double tonnage)
      throws javax.ejb.CreateException
   {
      System.out.println ("ejbCreate");
      setId (primaryKey);
      setName (name);
      setTonnage (tonnage);
      return null;
   }
   
   public void ejbPostCreate (Integer primaryKey, String name, double tonnage) { }
   
   // persistent fields
   //
   public abstract void setId (Integer id);
   public abstract Integer getId ();
   
   public abstract void setName (String name);
   public abstract String getName ( );
   
   public abstract void setTonnage (double tonnage);
   public abstract double getTonnage ( );
   
   // standard call back methods
   //
   public void setEntityContext (javax.ejb.EntityContext ec) {}
   public void unsetEntityContext () {}
   public void ejbLoad () {}
   public void ejbStore () {}
   public void ejbActivate () {}
   public void ejbPassivate () {}
   public void ejbRemove () {}
   
}
