/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package test.client;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject; 

import test.interfaces.TestSession;
import test.interfaces.TestSessionHome;

public class TestClient {

   public static void main(String[] args){
      try {
         InitialContext lContext = new InitialContext();
         
         TestSessionHome lHome = (TestSessionHome) lContext.lookup( "ejb/test/TestSession" );
         TestSession lSession = lHome.create();
         // Get a new Id of the Test Entity
         int lId = lSession.getNewEntityId();
         System.out.println( "New Entity Id is: " + lId );
         
         lSession.remove();
      } catch( Exception e ){
         e.printStackTrace();
      }
   }

}
