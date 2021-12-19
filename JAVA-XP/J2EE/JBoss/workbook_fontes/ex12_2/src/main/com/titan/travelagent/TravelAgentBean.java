package com.titan.travelagent;

import com.titan.cabin.CabinLocal;
import com.titan.cabin.CabinHomeLocal;
import com.titan.cruise.CruiseLocal;
import com.titan.cruise.CruiseHomeLocal;
import com.titan.customer.CustomerHomeRemote;
import com.titan.customer.CustomerRemote;
import com.titan.customer.Name;
import com.titan.processpayment.CreditCardDO;
import com.titan.processpayment.ProcessPaymentHomeRemote;
import com.titan.processpayment.ProcessPaymentRemote;
import com.titan.reservation.ReservationHomeLocal;
import com.titan.reservation.ReservationLocal;
import com.titan.ship.ShipHomeLocal;
import com.titan.ship.ShipLocal;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.util.ArrayList;

import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.sql.DataSource;

import javax.naming.NamingException;
import javax.ejb.EJBException;
import javax.ejb.CreateException;
import javax.ejb.NoSuchEntityException;

public class TravelAgentBean implements javax.ejb.SessionBean
{
   
   public CustomerRemote customer;
   public CruiseLocal cruise;
   public CabinLocal cabin;
   
   public javax.ejb.SessionContext ejbContext;
   public javax.naming.Context jndiContext;
   
   public void ejbCreate (CustomerRemote cust)
   {
      System.out.println ("TravelAgentBean::ejbCreate");
      customer = cust;
   }
   
   public void setCabinID (Integer cabinID)
      throws javax.ejb.FinderException
   {
      try
      {
         CabinHomeLocal home = (CabinHomeLocal)
         jndiContext.lookup ("java:comp/env/ejb/CabinHomeLocal");
         cabin = home.findByPrimaryKey (cabinID);
      }
      catch (NamingException ne)
      {
         throw new EJBException (ne);
      }
   }
   
   public void setCruiseID (Integer cruiseID)
      throws javax.ejb.FinderException
   {
      
      try
      {
         CruiseHomeLocal home = (CruiseHomeLocal)
         jndiContext.lookup ("java:comp/env/ejb/CruiseHomeLocal");
         cruise = home.findByPrimaryKey (cruiseID);
      }
      catch (NamingException ne)
      {
         throw new EJBException (ne);
      }
      
   }
   
   public TicketDO bookPassage (CreditCardDO card, double price)
      throws IncompleteConversationalState
   {
      
      if (customer == null || cruise == null || cabin == null)
      {
         throw new IncompleteConversationalState ();
      }
      try
      {
         ReservationHomeLocal reshome =
         (ReservationHomeLocal)jndiContext.lookup ("java:comp/env/ejb/ReservationHomeLocal");
         
         ReservationLocal reservation =
         reshome.create (customer, cruise, cabin, price, new java.util.Date ());
         
         Object ref = jndiContext.lookup ("java:comp/env/ejb/ProcessPaymentHomeRemote");
         
         ProcessPaymentHomeRemote ppHome = (ProcessPaymentHomeRemote)
         PortableRemoteObject.narrow (ref, ProcessPaymentHomeRemote.class);
         
         ProcessPaymentRemote process = ppHome.create ();
         process.byCredit (customer, card, price);
         process.remove ();
         
         TicketDO ticket = new TicketDO (customer,cruise,cabin,price);
         return ticket;
      }
      catch (Exception e)
      {
         throw new EJBException (e);
      }
   }
   
   public String [] listAvailableCabins (int bedCount)
      throws IncompleteConversationalState
   {
      if (cruise == null)
         throw new IncompleteConversationalState ();
      
      Connection con = null;
      PreparedStatement ps = null;;
      ResultSet result = null;
      
      try
      {
         Integer cruiseID = (Integer)cruise.getPrimaryKey ();
         Integer shipID = (Integer)cruise.getShip ().getPrimaryKey ();
         con = getConnection ();
         ps = con.prepareStatement (
         "select ID, NAME, DECK_LEVEL from CABIN "+
         "where SHIP_ID = ? and BED_COUNT = ? and ID NOT IN "+
         "(SELECT RCL.CABIN_ID FROM RESERVATION_CABIN_LINK AS RCL, RESERVATION AS R "+
         " WHERE RCL.RESERVATION_ID = R.ID AND R.CRUISE_ID = ?)");
         
         ps.setInt (1,shipID.intValue ());
         ps.setInt (2,bedCount);
         ps.setInt (3,cruiseID.intValue ());
         
         result = ps.executeQuery ();
         Vector vect = new Vector ();
         while(result.next ())
         {
            StringBuffer buf = new StringBuffer ();
            buf.append (result.getString (1));
            buf.append (',');
            buf.append (result.getString (2));
            buf.append (',');
            buf.append (result.getString (3));
            vect.addElement (buf.toString ());
         }
         
         String [] returnArray = new String[vect.size ()];
         vect.copyInto (returnArray);
         return returnArray;
      }
      catch (Exception e)
      {
         throw new EJBException (e);
      }
      finally
      {
         try
         { result.close (); } catch (Exception ignored)
         {}
         try
         { ps.close (); } catch (Exception ignored)
         {}
         try
         { con.close (); } catch (Exception ignored)
         {}
      }
   }
   
   private Connection getConnection () throws SQLException
   {
      try
      {
         DataSource ds =
         (DataSource)jndiContext.lookup ("java:comp/env/jdbc/titanDB");
         return ds.getConnection ();
      }
      catch(NamingException ne)
      {
         throw new EJBException (ne);
      }
   }
   
   
   // mechanism for building local beans for workbook example programs
   //
   public Collection buildSampleData ()
   {
      Collection results = new ArrayList ();
      
      try
      {
         System.out.println ("TravelAgentBean::buildSampleData()");
         
         Object obj = jndiContext.lookup ("java:comp/env/ejb/CustomerHomeRemote");
         CustomerHomeRemote custhome = (CustomerHomeRemote)
         javax.rmi.PortableRemoteObject.narrow (obj, CustomerHomeRemote.class);
         
         CabinHomeLocal cabinhome =
         (CabinHomeLocal)jndiContext.lookup ("java:comp/env/ejb/CabinHomeLocal");
         ShipHomeLocal shiphome =
         (ShipHomeLocal)jndiContext.lookup ("java:comp/env/ejb/ShipHomeLocal");
         CruiseHomeLocal cruisehome =
         (CruiseHomeLocal)jndiContext.lookup ("java:comp/env/ejb/CruiseHomeLocal");
         ReservationHomeLocal reshome =
         (ReservationHomeLocal)jndiContext.lookup ("java:comp/env/ejb/ReservationHomeLocal");
         
         System.out.println ("Creating Customers 1 and 2..");
         
         // we first clean the db by removing any customer, cabin,
         // ship, cruise and reservation beans.
         //         
         removeBeansInCollection (custhome.findAll());
         results.add ("All customers have been removed");
         removeBeansInCollection (cabinhome.findAll());
         results.add ("All cabins have been removed");
         removeBeansInCollection (shiphome.findAll());
         results.add ("All ships have been removed");
         removeBeansInCollection (cruisehome.findAll());
         results.add ("All cruises have been removed");
         removeBeansInCollection (reshome.findAll());
         results.add ("All reservations have been removed");

         // We now set our new basic environment
         //
         System.out.println ("Creating Customers 1 and 2...");
         CustomerRemote customer1 = custhome.create (new Integer (1));
         customer1.setName ( new Name ("Burke","Bill") );
         results.add ("Customer with ID 1 created (Burke Bill)");
         
         CustomerRemote customer2 = custhome.create (new Integer (2));
         customer2.setName ( new Name ("Labourey","Sacha") );
         results.add ("Customer with ID 2 created (Labourey Sacha)");

         System.out.println ("Creating Ships A and B...");
         ShipLocal shipA = shiphome.create (new Integer (101), "Nordic Prince", 50000.0);
			results.add("Created ship with ID 101...");
         ShipLocal shipB = shiphome.create (new Integer (102), "Bohemian Rhapsody", 70000.0);
			results.add("Created ship with ID 102...");

         System.out.println ("Creating Cabins on the Ships...");         
         ArrayList cabinsA = new ArrayList ();
         ArrayList cabinsB = new ArrayList ();
         for (int jj=0; jj<10; jj++)
         {
            CabinLocal cabinA = cabinhome.create (new Integer (100+jj),shipA,"Suite 10"+jj,1,1);
				cabinsA.add(cabinA);
            CabinLocal cabinB = cabinhome.create (new Integer (200+jj),shipB,"Suite 20"+jj,2,1);
				cabinsB.add(cabinB);
         }
			results.add("Created cabins on Ship A with IDs 100-109");
			results.add("Created cabins on Ship B with IDs 200-209");

         
         System.out.println ("Creating three cruises for each ship...");
         
         CruiseLocal cruiseA1 = cruisehome.create ("Alaska Cruise", shipA);
         CruiseLocal cruiseA2 = cruisehome.create ("Norwegian Fjords", shipA);
         CruiseLocal cruiseA3 = cruisehome.create ("Bermuda or Bust", shipA);
			results.add("Created cruises on ShipA with IDs "+cruiseA1.getId()+", "+cruiseA2.getId()+", "+cruiseA3.getId());
         
         CruiseLocal cruiseB1 = cruisehome.create ("Indian Sea Cruise", shipB);
         CruiseLocal cruiseB2 = cruisehome.create ("Australian Highlights", shipB);
         CruiseLocal cruiseB3 = cruisehome.create ("Three-Hour Cruise", shipB);
         results.add ("Created cruises on ShipB with IDs "+cruiseB1.getId ()+", "+cruiseB2.getId ()+", "+cruiseB3.getId ());
         
         System.out.println ("Making a few reservations...");
         
         ReservationLocal res =
         reshome.create (customer1, cruiseA1, (CabinLocal)(cabinsA.get (3)), 1000.0, new Date ());
         res = reshome.create (customer1, cruiseB3, (CabinLocal)(cabinsB.get (8)), 2000.0, new Date ());
         res = reshome.create (customer2, cruiseA2, (CabinLocal)(cabinsA.get (5)), 2000.0, new Date ());
         res = reshome.create (customer2, cruiseB3, (CabinLocal)(cabinsB.get (2)), 2000.0, new Date ());
         
         results.add ("Made reservation for Customer 1 on Cruise "+cruiseA1.getId ()+" for Cabin 103");
         results.add ("Made reservation for Customer 1 on Cruise "+cruiseB3.getId ()+" for Cabin 208");
         results.add ("Made reservation for Customer 2 on Cruise "+cruiseA2.getId ()+" for Cabin 105");
         results.add ("Made reservation for Customer 2 on Cruise "+cruiseB3.getId ()+" for Cabin 202");
         
      }
      catch (Exception e)
      {
         e.printStackTrace ();
         throw new EJBException (e);
      }
      
      return results;
   }
   
   protected void removeBeansInCollection (Collection beans) 
      throws javax.ejb.RemoveException, java.rmi.RemoteException
   {
      if (beans != null && beans.size () > 0)
      {
         java.util.Iterator iter = beans.iterator ();
         while (iter.hasNext ())
         {
            Object bean = iter.next ();
            if (bean instanceof javax.ejb.EJBObject)
               ((javax.ejb.EJBObject)bean).remove ();
            else if (bean instanceof javax.ejb.EJBLocalObject)
               ((javax.ejb.EJBLocalObject)bean).remove ();
            else
               throw new EJBException ("Unknown object: " + bean);
         }
      }
   }
   
   public void ejbRemove ()
   {}
   public void ejbActivate ()
   {}
   public void ejbPassivate ()
   {}
   
   public void setSessionContext (javax.ejb.SessionContext cntx)
   {
      ejbContext = cntx;
      try
      {
         jndiContext = new javax.naming.InitialContext ();
      }
      catch(NamingException ne)
      {
         throw new EJBException (ne);
      }
   }
}
