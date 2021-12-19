package com.titan.reservationprocessor;

import java.util.Date;

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
import com.titan.travelagent.TicketDO;

import javax.jms.Message;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.QueueSender;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueConnection;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;

import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.MessageDrivenContext;
import javax.ejb.MessageDrivenBean;

import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

public class ReservationProcessorBean
   implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener
{
   
   MessageDrivenContext ejbContext;
   Context jndiContext;
   
   public void setMessageDrivenContext (MessageDrivenContext mdc)
   {
      ejbContext = mdc;
      try
      {
         jndiContext = new InitialContext ();
      } catch(NamingException ne)
      {
         throw new EJBException (ne);
      }
   }
   
   public void ejbCreate () {}
   
   public void onMessage (Message message)
   {
      try
      {         
         System.out.println ("ReservationProcessor::onMessage() called..");
         MapMessage reservationMsg = (MapMessage)message;
         
         Integer customerPk = (Integer)
         reservationMsg.getObject ("CustomerID");
         Integer cruisePk =   (Integer)
         reservationMsg.getObject ("CruiseID");
         Integer cabinPk =    (Integer)
         reservationMsg.getObject ("CabinID");
         
         double price = reservationMsg.getDouble ("Price");
         
         String creditCardNum = reservationMsg.getString ("CreditCardNum");
         Date creditCardExpDate = new Date ( reservationMsg.getLong ("CreditCardExpDate") );
         String creditCardType = reservationMsg.getString ("CreditCardType");
         
         CreditCardDO card = new CreditCardDO (creditCardNum,creditCardExpDate,creditCardType);
         
         System.out.println ("Customer ID = "+customerPk+", Cruise ID = "+cruisePk+", Cabin ID = "+cabinPk+", Price = "+price);
         
         CustomerRemote customer = getCustomer (customerPk);
         CruiseLocal cruise = getCruise (cruisePk);
         CabinLocal cabin = getCabin (cabinPk);
         
         ReservationHomeLocal resHome = (ReservationHomeLocal)
         jndiContext.lookup ("java:comp/env/ejb/ReservationHomeLocal");
         
         ReservationLocal reservation =
         resHome.create (customer, cruise, cabin, price, new Date ());
         
         Object ref = jndiContext.lookup ("java:comp/env/ejb/ProcessPaymentHomeRemote");
         
         ProcessPaymentHomeRemote ppHome = (ProcessPaymentHomeRemote)
         PortableRemoteObject.narrow (ref, ProcessPaymentHomeRemote.class);
         
         ProcessPaymentRemote process = ppHome.create ();
         process.byCredit (customer, card, price);
         
         TicketDO ticket = new TicketDO (customer,cruise,cabin,price);
         
         deliverTicket (reservationMsg, ticket);
         
      } 
      catch(Exception e)
      {
         throw new EJBException (e);
      }
   }
   
   public void deliverTicket (MapMessage reservationMsg, TicketDO ticket)
      throws NamingException, JMSException
   {
      
      // create a ticket and send it to the proper destination
      //
      System.out.println ("ReservationProcessor::deliverTicket()..");
      
      Queue queue = (Queue)reservationMsg.getJMSReplyTo ();
      QueueConnectionFactory factory = (QueueConnectionFactory)
      jndiContext.lookup ("java:comp/env/jms/QueueFactory");
      QueueConnection connect = factory.createQueueConnection ();
      QueueSession session = connect.createQueueSession (false,0);
      QueueSender sender = session.createSender (queue);
      ObjectMessage message = session.createObjectMessage ();
      message.setObject (ticket);
      
      System.out.println ("Sending message back to sender..");
      sender.send (message);
      
      connect.close ();
   }
   
   public CustomerRemote getCustomer (Integer key)
      throws NamingException, ObjectNotFoundException, FinderException, RemoteException
   {
      // get a remote reference to the Customer EJB
      //
      Object ref = jndiContext.lookup ("java:comp/env/ejb/CustomerHomeRemote");
      CustomerHomeRemote home = (CustomerHomeRemote)
      PortableRemoteObject.narrow (ref, CustomerHomeRemote.class);
      CustomerRemote customer = (CustomerRemote)home.findByPrimaryKey (key);
      return customer;
   }
   
   public CruiseLocal getCruise (Integer key)
      throws NamingException, ObjectNotFoundException, FinderException
   {
      // get a local reference to the Cruise EJB
      //
      CruiseHomeLocal home = (CruiseHomeLocal)
      jndiContext.lookup ("java:comp/env/ejb/CruiseHomeLocal");
      CruiseLocal cruise = home.findByPrimaryKey (key);
      return cruise;
   }
   
   public CabinLocal getCabin (Integer key)
      throws NamingException, ObjectNotFoundException, FinderException
   {
      // get a local reference to the Cabin EJB
      //
      CabinHomeLocal home = (CabinHomeLocal)
      jndiContext.lookup ("java:comp/env/ejb/CabinHomeLocal");
      CabinLocal cruise = home.findByPrimaryKey (key);
      return cruise;
   }
   
   public void ejbRemove ()
   {
      try
      {
         jndiContext.close ();
         ejbContext = null;
      } catch(NamingException ignored) { }
   }
}

