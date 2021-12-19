package com.titan.customer;

import com.titan.address.*;

import javax.naming.InitialContext;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public abstract class CreditCompanyBean implements javax.ejb.EntityBean 
{

   public Integer ejbCreate(Integer id, String name)
      throws CreateException
   {
      this.setId(id);
      this.setName(name);
      return null;
   }

   public void ejbPostCreate(Integer id, String name)
   {
   }

   // business methods

   public abstract AddressLocal getAddress();
   public abstract void setAddress(AddressLocal address);

   // abstract accessor methods

   public abstract Integer getId();
   public abstract void setId(Integer id);
 
   public abstract String getName();
   public abstract void setName(String lname);

   // ejbSelect methods

   

   // standard call back methods

   public void setEntityContext(EntityContext ec){}
   public void unsetEntityContext(){}
   public void ejbLoad(){}
   public void ejbStore(){}
   public void ejbActivate(){}
   public void ejbPassivate(){}
   public void ejbRemove(){}

}
