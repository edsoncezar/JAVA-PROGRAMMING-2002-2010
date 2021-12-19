package com.titan.clients;

import com.titan.travelagent.TravelAgentHomeRemote;
import com.titan.travelagent.TravelAgentRemote;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;

import java.rmi.RemoteException;

public class Client_3 
{
   public static int SHIP_ID = 1;
   public static int BED_COUNT = 3;

   public static void main(String [] args) 
   {
      try 
      {
         Context jndiContext = getInitialContext();
           
         Object ref = jndiContext.lookup("TravelAgentHomeRemote");
               
         TravelAgentHomeRemote home = (TravelAgentHomeRemote)
            PortableRemoteObject.narrow(ref,TravelAgentHomeRemote.class);
        
         TravelAgentRemote travelAgent = home.create();
        
         // Get a list of all cabins on ship 1 with a bed count of 3.
         String list [] = travelAgent.listCabins(SHIP_ID,BED_COUNT);
        
         for(int i = 0; i < list.length; i++)
         {
            System.out.println(list[i]);
         }
      } 
      catch(java.rmi.RemoteException re)
      {
         re.printStackTrace();
      }
      catch(Throwable t)
      {
         t.printStackTrace();
      }
   }

   static public Context getInitialContext() throws Exception 
   {
      return new InitialContext();
      /**** context initialized by jndi.properties file
        java.util.Properties p = new java.util.Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, "localhost:1099");
        return new javax.naming.InitialContext(p);
      */
   }

}

