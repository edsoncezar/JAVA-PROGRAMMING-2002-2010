/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package test.session;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import test.interfaces.InvalidValueException;
import test.interfaces.TestEntity;
import test.interfaces.TestEntityData;
import test.interfaces.TestEntityHome;

/**
 * Session Bean Template
 *
 * ATTENTION: Some of the XDoclet tags are hidden from XDoclet by
 *            adding a "--" between @ and the namespace. Please remove
 *            this "--" to make it active or add a space to make an
 *            active tag inactive.
 *
 * @ejb:bean name="test/TestSession"
 *           display-name="Bug TestSession Bean"
 *           type="Stateful"
 *           transaction-type="Container"
 *           jndi-name="ejb/test/TestSession"
 *
 * @ejb:ejb-ref ejb-name="test/TestEntity"
 *              ref-name="mytest/TestEntity"
 *
 * @ejb:resource-ref res-name="test/Mail"
 *                   res-type="javax.mail.Session"
 *                   res-auth="Container"
 *
 * @jboss:resource-manager res-man-class="javax.mail.Session"
 *                         res-man-name="test/Mail"
 *                         res-man-jndi-name="java:Mail"
 **/
public class TestSessionBean
   implements SessionBean
{

   // -------------------------------------------------------------------------
   // Static
   // -------------------------------------------------------------------------
   
   // -------------------------------------------------------------------------
   // Members 
   // -------------------------------------------------------------------------
   
   private SessionContext mContext;
   
   // -------------------------------------------------------------------------
   // Methods
   // -------------------------------------------------------------------------  
   
   /**
   * @ejb:interface-method view-type="remote"
   **/
   public int getNewEntityId()
      throws
         RemoteException
   {
      try {
         // Get Test Entity Remote Interface
         Context lContext = new InitialContext();
         
         TestEntityHome lHome = (TestEntityHome) PortableRemoteObject.narrow(
            lContext.lookup(
               "java:comp/env/ejb/mytest/TestEntity"
            ),
            TestEntityHome.class
         );
         // Create a new Test Entity and return its Id
         TestEntityData lData = new TestEntityData();
         lData.setFirstName( "Andy" );
         lData.setLastName( "Schaefer" );
         TestEntity lEntity = lHome.create( lData );
         
         javax.mail.Session lSession = (javax.mail.Session) lContext.lookup(
            "java:comp/env/test/Mail"
         );
         
         return lEntity.getValueObject().getId();
      }
      catch ( InvalidValueException ive ) {
         throw new EJBException( "Invalid Value found: " + ive.getMessage() );
      }
      catch ( NamingException ne ) {
         throw new EJBException( "Naming lookup failure: " + ne.getMessage() );
      }
      catch ( CreateException ce ) {
         throw new EJBException( "Failure while creating a generator session bean: " + ce.getMessage() );
      }
      catch ( RemoteException rte ) {
         throw new EJBException( "Remote exception occured while removing generator session bean: " +  rte.getMessage() );
      }
   }
   
   /**
   * Create the Session Bean
   *
   * @throws CreateException 
   *
   * @ejb:create-method view-type="remote"
   **/
   public void ejbCreate()
      throws
         CreateException
   {
      System.out.println( "TestSessionBean.ejbCreate()" );
   }
   
   /**
   * Describes the instance and its content for debugging purpose
   *
   * @return Debugging information about the instance and its content
   **/
   public String toString()
   {
      return "TestSessionBean [ " + " ]";
   }
   
   
   // -------------------------------------------------------------------------
   // Framework Callbacks
   // -------------------------------------------------------------------------  
   
   public void setSessionContext( SessionContext aContext )
      throws
         EJBException
   {
      mContext = aContext;
   }
   
   public void ejbActivate()
      throws
         EJBException
   {
   }
   
   public void ejbPassivate()
      throws
         EJBException
   {
   }
   
   public void ejbRemove()
      throws
         EJBException
   {
   }
}
