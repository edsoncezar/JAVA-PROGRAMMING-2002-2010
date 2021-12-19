package com.titan.phone;

public abstract class PhoneBean implements javax.ejb.EntityBean
{   
   //   private static final int IDGEN_START = (int)System.currentTimeMillis();
   private static final int IDGEN_START = 0;
   private static int idgen = IDGEN_START;
   public Object ejbCreate (String number, byte type)
      throws javax.ejb.CreateException
   {
      System.out.println ("ejbCreate");
      setId(new Integer(idgen++));
      setNumber (number);
      setType (type);
      
      return null;
   }
   
   public void ejbPostCreate (String number, byte type)
   {
      System.out.println ("ejbPostCreate");
   }
   
   // persistent fields
   //
   public abstract Integer getId ();
   public abstract void setId (Integer id);
   
   public abstract String getNumber ();
   public abstract void setNumber (String number);
   
   public abstract byte getType ();
   public abstract void setType (byte type);
   
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
