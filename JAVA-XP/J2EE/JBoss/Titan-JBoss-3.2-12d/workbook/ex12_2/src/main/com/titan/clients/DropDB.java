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

public class DropDB
{
   
   public static void main (String [] args)
   {
      try
      {
         Context jndiContext = getInitialContext();
         
         System.out.println("Looking up home interfaces..");
         Object ref = jndiContext.lookup ("ProcessPaymentHomeRemote");
         
         ProcessPaymentHomeRemote procpayhome = (ProcessPaymentHomeRemote)
         PortableRemoteObject.narrow (ref,ProcessPaymentHomeRemote.class);
         
         ProcessPaymentRemote procpay = procpayhome.create();
         System.out.println ("Dropping database table...");
         procpay.dropDbTable ();
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
