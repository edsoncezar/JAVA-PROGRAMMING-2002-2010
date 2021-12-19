package com.titan.address;

import java.util.Collection;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import com.titan.customer.CustomerLocal;

public abstract class AddressBean implements javax.ejb.EntityBean 
{
    
   private static final int IDGEN_START = (int)System.currentTimeMillis();
   private static int idgen = IDGEN_START;

   public Integer ejbCreateAddress(String street, String city, 
				   String state,  String zip)
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

   // ejbSelect methods
   public abstract Collection ejbSelectZipCodes(String state)
      throws FinderException;

   public abstract Collection ejbSelectAll()
      throws FinderException;

   public abstract CustomerLocal ejbSelectCustomer(AddressLocal addr)
      throws FinderException;

   // customer home methods.  These are wrappers of ejbSelect methods

   public Collection ejbHomeQueryZipCodes(String state)
      throws FinderException
   {
      return ejbSelectZipCodes(state);
   }

   public Collection ejbHomeQueryAll()
      throws FinderException
   {
      return ejbSelectAll();
   }

   public CustomerLocal ejbHomeQueryCustomer(AddressLocal addr)
      throws FinderException
   {
      return ejbSelectCustomer(addr);
   }

   // standard call back methods
    
   public void setEntityContext(EntityContext ec){}
   public void unsetEntityContext(){}
   public void ejbLoad(){}
   public void ejbStore(){}
   public void ejbActivate(){}
   public void ejbPassivate(){}
   public void ejbRemove(){}

}
