package com.titan.clients;

import com.titan.cabin.CabinHomeRemote;
import com.titan.cabin.CabinRemote;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

public class Client_1 
{
   public static void main(String [] args) 
   {
      try 
      {
         Context jndiContext = getInitialContext();
         Object ref = jndiContext.lookup("CabinHomeRemote");
         CabinHomeRemote home = (CabinHomeRemote)
            PortableRemoteObject.narrow(ref,CabinHomeRemote.class);   
         CabinRemote cabin_1 = home.create(new Integer(1));
         cabin_1.setName("Master Suite");
         cabin_1.setDeckLevel(1);
         cabin_1.setShipId(1);
         cabin_1.setBedCount(3);
             
         Integer pk = new Integer(1);
            
         CabinRemote cabin_2 = home.findByPrimaryKey(pk);
         System.out.println(cabin_2.getName());
         System.out.println(cabin_2.getDeckLevel());
         System.out.println(cabin_2.getShipId());
         System.out.println(cabin_2.getBedCount());

      } 
      catch (java.rmi.RemoteException re){re.printStackTrace();}
      catch (javax.naming.NamingException ne){ne.printStackTrace();}
      catch (javax.ejb.CreateException ce){ce.printStackTrace();}
      catch (javax.ejb.FinderException fe){fe.printStackTrace();}
   }

   public static Context getInitialContext() 
      throws javax.naming.NamingException 
   {
      return new InitialContext();
      /**** context initialized by jndi.properties file
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, "localhost:1099");
            return new javax.naming.InitialContext(p);
      */
      
   }
}
