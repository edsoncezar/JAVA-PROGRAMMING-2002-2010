/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package test.message;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message Driven Bean Template
 *
 * @ejb:bean name="test/TestMessage"
 *           display-name="Message Driven Test Bean"
 *           transaction-type="Container"
 *           acknowledge-mode="Auto-acknowledge"
 *           destination-type="javax.jms.Queue"
 *           subscription-durability="NonDurable"
 *
 * @jboss:destination-jndi-name name="queue/testQueue"
 **/
public class TestMessageDrivenBean
   implements MessageDrivenBean, MessageListener
{

   // -------------------------------------------------------------------------
   // Static
   // -------------------------------------------------------------------------
   
   // -------------------------------------------------------------------------
   // Members 
   // -------------------------------------------------------------------------
   
   private MessageDrivenContext  mContext;
   
   // -------------------------------------------------------------------------
   // Methods
   // -------------------------------------------------------------------------  
   
   /**
    * This method is called by the JMS implementation when a message is
    * ready to be delivered.
    *
    * @param pMessage Message send by JMS
    **/
   public void onMessage( Message pMessage )
   {
      System.out.println("TestMessageDrivenBean.onMessage() got message " + pMessage );
   }
   
   /**
   * Create the Session Bean
   *
   * @throws CreateException 
   **/
   public void ejbCreate()
   {
   }
   
   /**
   * Describes the instance and its content for debugging purpose
   *
   * @return Debugging information about the instance and its content
   **/
   public String toString()
   {
      return "TestMessageDrivenBean [ " + " ]";
   }
   
   // -------------------------------------------------------------------------
   // Framework Callbacks
   // -------------------------------------------------------------------------  
   
   public void setMessageDrivenContext( MessageDrivenContext aContext )
      throws
         EJBException
   {
      mContext = aContext;
   }
   
   public void ejbRemove()
   {
   }
}
