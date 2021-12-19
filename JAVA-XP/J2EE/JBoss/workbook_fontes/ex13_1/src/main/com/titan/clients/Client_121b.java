package com.titan.clients;

import com.titan.processpayment.*;
import com.titan.customer.*;

import java.util.Calendar;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;

import java.rmi.RemoteException;

/**
 * Example demonstrating use of ProcessPayment EJB directly
 *
 */

public class Client_121b
{
   
   public static void main (String [] args)
   {
      try
      {
         Context jndiContext = getInitialContext ();
         
         System.out.println ("Looking up home interfaces..");
         Object ref = jndiContext.lookup ("ProcessPaymentHomeRemote");
         
         ProcessPaymentHomeRemote procpayhome = (ProcessPaymentHomeRemote)
         PortableRemoteObject.narrow (ref,ProcessPaymentHomeRemote.class);
         
         ProcessPaymentRemote procpay = procpayhome.create ();
            
         // We check if we have to build the database schema...
         //
         if ( (args.length > 0) && args[0].equalsIgnoreCase ("CreateDB") )
         {
            System.out.println ("Creating database table...");
            procpay.makeDbTable ();
         }
         // ... or if we have to drop it...
         //
         else if ( (args.length > 0) && args[0].equalsIgnoreCase ("DropDB") )
         {
            System.out.println ("Dropping database table...");
            procpay.dropDbTable ();
         }
         else
         {
            ref = jndiContext.lookup ("CustomerHomeRemote");
            
            CustomerHomeRemote custhome = (CustomerHomeRemote)
            PortableRemoteObject.narrow (ref,CustomerHomeRemote.class);
            
            CustomerRemote cust = custhome.findByPrimaryKey (new Integer (1));
            
            System.out.println ("Making a payment using byCash()..");
            procpay.byCash (cust,1000.0);
            
            System.out.println ("Making a payment using byCheck()..");
            CheckDO check = new CheckDO ("010010101101010100011", 3001);
            procpay.byCheck (cust,check,2000.0);
            
            System.out.println ("Making a payment using byCredit()..");
            Calendar expdate = Calendar.getInstance ();
            expdate.set (2005,1,28); // month=1 is February
            CreditCardDO credit = new CreditCardDO ("370000000000002",expdate.getTime (),"AMERICAN_EXPRESS");
            procpay.byCredit (cust,credit,3000.0);
            
            System.out.println ("Making a payment using byCheck() with a low check number..");
            CheckDO check2 = new CheckDO ("111000100111010110101", 1001);
            try
            {
               procpay.byCheck (cust,check2,9000.0);
               System.out.println("Problem! The PaymentException has not been raised!");
            }
            catch (PaymentException pe)
            {
               System.out.println ("Caught PaymentException: "+pe.getMessage ());
            }
            
            procpay.remove ();
         }
      } 
      catch(java.rmi.RemoteException re)
      {
         re.printStackTrace ();
      }
      catch(Throwable t)
      {
         t.printStackTrace ();
      }
      
   }
   
   static public Context getInitialContext () throws Exception
   {
      return new InitialContext ();
   }
   
}

