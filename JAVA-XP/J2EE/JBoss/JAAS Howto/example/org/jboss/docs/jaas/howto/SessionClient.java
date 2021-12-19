package org.jboss.docs.jaas.howto;

import java.io.IOException;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;

/** A simple session client that access the two secured EJBs as the user
 passed in on the command line.

 @author Scott.Stark@jboss.org
 @version $Revision: 1.1 $
 */
public class SessionClient
{
   static class AppCallbackHandler implements CallbackHandler
   {
      private String username;
      private char[] password;

      public AppCallbackHandler(String username, char[] password)
      {
         this.username = username;
         this.password = password;
      }

      public void handle(Callback[] callbacks) throws
         java.io.IOException, UnsupportedCallbackException
      {
         for (int i = 0; i < callbacks.length; i++)
         {
            if (callbacks[i] instanceof NameCallback)
            {
               NameCallback nc = (NameCallback) callbacks[i];
               nc.setName(username);
            }
            else if (callbacks[i] instanceof PasswordCallback)
            {
               PasswordCallback pc = (PasswordCallback) callbacks[i];
               pc.setPassword(password);
            }
            else
            {
               throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }
         }
      }
   }

   public static void main(String args[]) throws Exception
   {
      if (args.length != 3)
         throw new IllegalArgumentException("Usage: username password example");

      System.setErr(System.out);
      String name = args[0];
      char[] password = args[1].toCharArray();
      String example = args[2];
      System.out.println("+++ Running SessionClient with username=" + name + ", password=" + args[1] + ", example=" + example);
      try
      {
         AppCallbackHandler handler = new AppCallbackHandler(name, password);
         LoginContext lc = new LoginContext("TestClient", handler);
         System.out.println("Created LoginContext");
         lc.login();
      }
      catch (LoginException le)
      {
         System.out.println("Login failed");
         le.printStackTrace();
      }

      try
      {
         InitialContext iniContext = new InitialContext();
         SessionHome home = (SessionHome) iniContext.lookup(example + "/PublicSession");
         System.out.println("Found PublicSession home");
         Session bean = home.create();
         System.out.println("Created PublicSession");
         System.out.println("Bean.echo('Hello') -> " + bean.echo("Hello"));
         bean.remove();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      try
      {
         InitialContext iniContext = new InitialContext();
         SessionHome home = (SessionHome) iniContext.lookup(example + "/PrivateSession");
         System.out.println("Found PrivateSession home");
         Session bean = home.create();
         throw new IllegalStateException("Was able to create PrivateSession");
      }
      catch (Exception e)
      {
         System.out.println("Failed to create PrivateSession as expected");
      }
   }
}
