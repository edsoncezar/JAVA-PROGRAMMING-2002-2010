package com.titan.clients;

import com.titan.test.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

public class Client_73
{
   public static void main(String[] args) throws Exception
   {
      try
      {
         // obtain CustomerHome
         Context jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("Test73HomeRemote");
         Test73HomeRemote home = (Test73HomeRemote)obj; 
         Test73Remote tester = home.create();
         String output = tester.test73();
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
