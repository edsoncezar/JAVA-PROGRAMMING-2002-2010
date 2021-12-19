package com.titan.clients;

import com.titan.test.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

public class Client_71a
{
   public static void main(String[] args) throws Exception
   {
      try
      {
         // obtain CustomerHome
         Context jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("Test71HomeRemote");
         Test71HomeRemote home = (Test71HomeRemote)obj; 
         Test71Remote tester = home.create();
         String output = tester.test71a();
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
