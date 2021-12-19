package com.titan.clients;

import com.titan.test.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

public class Client_82g
{
   public static void main(String[] args) throws Exception
   {
      try
      {
         // obtain CustomerHome
         Context jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("Test82HomeRemote");
         Test82HomeRemote home = (Test82HomeRemote)obj; 
         Test82Remote tester = home.create();
         String output = tester.test82g();
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
