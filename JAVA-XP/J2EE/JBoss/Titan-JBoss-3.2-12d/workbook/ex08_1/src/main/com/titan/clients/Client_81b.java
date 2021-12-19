package com.titan.clients;

import com.titan.test.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

public class Client_81b
{
   public static void main(String[] args) throws Exception
   {
      try
      {
         // obtain CustomerHome
         Context jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("Test81HomeRemote");
         Test81HomeRemote home = (Test81HomeRemote)obj; 
         Test81Remote tester = home.create();
         String output = tester.test81b();
         System.out.println(output);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
   
   public static Context getInitialContext() 
      throws javax.naming.NamingException 
   {
      return new InitialContext();
   }
}
