package com.titan.reservation;

import com.titan.cruise.CruiseLocal;
import com.titan.customer.CustomerLocal;
import com.titan.customer.CustomerRemote;
import com.titan.customer.CustomerHomeLocal;
import com.titan.cabin.CabinLocal;

import java.util.Date;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;

public abstract class ReservationBean implements javax.ejb.EntityBean
{   
   private static final int IDGEN_START = (int)System.currentTimeMillis();
   private static int idgen = IDGEN_START;
   public Object ejbCreate (CruiseLocal cruise, Collection customers)
     throws javax.ejb.CreateException
   {
      System.out.println ("ReservationBean::ejbCreate");
      setId(new Integer(idgen++));
      return null;
   }
   
   public void ejbPostCreate (CruiseLocal cruise, Collection customers)
   {
      System.out.println ("ReservationBean::ejbPostCreate");
      setCruise (cruise);
      Collection myCustomers = this.getCustomers ();
      myCustomers.addAll (customers);
   }
   
   public Integer ejbCreate (CustomerRemote customer,
                             CruiseLocal cruise,
                             CabinLocal cabin, double price, Date dateBooked)
      throws javax.ejb.CreateException
   {
      System.out.println ("ReservationBean::ejbCreate");
      setId(new Integer(idgen++));
      setAmountPaid (price);
      setDate (dateBooked);
      return null;
   }
   
   public void ejbPostCreate (CustomerRemote customer,
                              CruiseLocal cruise,
                              CabinLocal cabin, double price, Date dateBooked)
      throws javax.ejb.CreateException
   {
      
      System.out.println ("ReservationBean::ejbPostCreate");
      setCruise (cruise);
      // Our bean has many cabins, use the cmr set method here..
      Set cabins = new HashSet ();
      cabins.add (cabin);
      this.setCabins (cabins);
      
      try
      {
         Integer primKey = (Integer)customer.getPrimaryKey ();
         javax.naming.Context jndiContext = new InitialContext ();
         CustomerHomeLocal home = (CustomerHomeLocal)
         jndiContext.lookup ("java:comp/env/ejb/CustomerHomeLocal");  // get local interface
         
         CustomerLocal custL = home.findByPrimaryKey (primKey);
         // Our bean has many customers, use the cmr set method here..
         Set customers = new HashSet ();
         customers.add (custL);
         this.setCustomers (customers);
      } 
      catch (RemoteException re)
      {
         throw new CreateException ("Invalid Customer - Bad Remote Reference");
      } 
      catch (FinderException fe)
      {
         throw new CreateException ("Invalid Customer - Unable to Find Local Reference");
      } 
      catch (NamingException ne)
      {
         throw new CreateException ("Invalid Customer - Unable to find CustomerHomeLocal Reference");
      }
   }
   
   // relationship fields
   //
   public abstract CruiseLocal getCruise ();
   public abstract void setCruise (CruiseLocal cruise);
   
   public abstract Set getCabins ( );
   public abstract void setCabins (Set cabins);
   
   public abstract Set getCustomers ( );
   public abstract void setCustomers (Set customers);
   
   // persistent fields
   //
   public abstract Integer getId ();
   public abstract void setId (Integer id);
   
   public abstract Date getDate ();
   public abstract void setDate (Date date);
   
   public abstract double getAmountPaid ();
   public abstract void setAmountPaid (double amount);
   
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
