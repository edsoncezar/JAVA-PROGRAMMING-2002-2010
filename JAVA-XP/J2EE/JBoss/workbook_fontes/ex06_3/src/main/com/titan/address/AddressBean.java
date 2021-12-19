package com.titan.address;

import javax.ejb.EntityContext;
import javax.ejb.CreateException;

public abstract class AddressBean implements javax.ejb.EntityBean 
{
   private static final int IDGEN_START = (int)System.currentTimeMillis();
   private static int idgen = IDGEN_START;

   public Integer ejbCreateAddress(String street, String city, 
                                   String state,  String zip )
      throws CreateException
   {
      setId(new Integer(idgen++));
      setStreet(street);
      setCity(city);
      setState(state);
      setZip(zip);
      return null;
   }

   public void ejbPostCreateAddress(String street, String city,
                                    String state,  String zip)
   {
   }

   // persistent fields
   public abstract Integer getId();
   public abstract void setId(Integer id);
   public abstract String getStreet();
   public abstract void setStreet(String street);
   public abstract String getCity();
   public abstract void setCity(String city);
   public abstract String getState();
   public abstract void setState(String state);
   public abstract String getZip();
   public abstract void setZip(String zip);

   // standard call back methods
    
   public void setEntityContext(EntityContext ec){}
   public void unsetEntityContext(){}
   public void ejbLoad(){}
   public void ejbStore(){}
   public void ejbActivate(){}
   public void ejbPassivate(){}
   public void ejbRemove(){}

}
