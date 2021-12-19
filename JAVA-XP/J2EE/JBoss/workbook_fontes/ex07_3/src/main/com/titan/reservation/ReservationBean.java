package com.titan.reservation;

import com.titan.cruise.*;

import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import java.util.Date;
import java.util.Set;
import java.util.Collection;

public abstract class ReservationBean implements javax.ejb.EntityBean 
{
   private static final int IDGEN_START = (int)System.currentTimeMillis();
   private static int idgen = IDGEN_START;
    
   public Object ejbCreate(CruiseLocal cruise, Collection customers)
      throws CreateException
   {
      System.out.println("ejbCreate");
      setId(new Integer(idgen++));
      return null;
   }

   public void ejbPostCreate(CruiseLocal cruise, Collection customers) 
   {
      System.out.println("ejbPostCreate");
      setCruise(cruise);
      Collection myCustomers = this.getCustomers();
      myCustomers.addAll(customers);
   }

   // relationship fields

   public abstract CruiseLocal getCruise();
   public abstract void setCruise(CruiseLocal cruise);

   public abstract Set getCabins( );
   public abstract void setCabins(Set cabins);

   public abstract Set getCustomers( );
   public abstract void setCustomers(Set customers);

   // persistent fields

   public abstract Integer getId();
   public abstract void setId(Integer id);
   public abstract Date getDate();
   public abstract void setDate(Date date);
   public abstract double getAmountPaid();
   public abstract void setAmountPaid(double amount);

   // standard call back methods
    
   public void setEntityContext(EntityContext ec){}
   public void unsetEntityContext(){}
   public void ejbLoad(){}
   public void ejbStore(){}
   public void ejbActivate(){}
   public void ejbPassivate(){}
   public void ejbRemove(){}

}
