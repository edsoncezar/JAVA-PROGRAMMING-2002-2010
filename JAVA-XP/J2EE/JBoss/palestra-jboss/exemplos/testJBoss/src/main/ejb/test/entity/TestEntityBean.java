/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package test.entity;

import test.interfaces.InvalidValueException;
import test.interfaces.TestEntity;
import test.interfaces.TestEntityData;
import test.interfaces.TestEntityHome;
import test.interfaces.TestEntityPK;
import test.interfaces.ServiceUnavailableException;
// Only necessary because of a limitation by the EJBDoclet
import test.interfaces.SequenceGenerator;
import test.interfaces.SequenceGeneratorHome;

import java.sql.Date;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * The Entity bean represents a TestEntity
 *
 * @author Andreas Schaefer
 * @version $Revision: 1.1 $
 *
 * @ejb:bean name="test/TestEntity"
 *           display-name="TestEntity working on projects to support clients"
 *           type="CMP"
 *           jndi-name="ejb/test/TestEntity"
 *
 * @ejb:env-entry name="SequenceName"
 *                value="TestEntity"
 *
 * @ejb:ejb-ref ejb-name="test/SequenceGenerator"
 *
 * @ejb:transaction type="Required"
 *
 * @ejb:data-object extends="test.interfaces.AbstractData"
 *                  setdata="false"
 *
 * @ejb:finder signature="java.util.Collection findAll()"
 *
 * @ejb:finder signature="test.interfaces.TestEntity findByName( java.lang.String pSurname, java.lang.String pLastName )"
 *
 * @jboss:finder-query name="findByName"
 *                     query="First_Name = {0} AND Last_Name = {1}"
 *
 * @ejb:finder signature="test.interfaces.TestEntity findAnotherByName( int pId, java.lang.String pSurname, java.lang.String pLastName )"
 *
 * @jboss:finder-query name="findAnotherByName"
 *                     query="Id != {0} AND First_Name = {1} AND Last_Name = {2}"
 *
 * @jboss:table-name table-name="TestEntity"
 *
 * @jboss:create-table create="true"
 *
 * @jboss:remove-table remove="true"
 **/
public abstract class TestEntityBean
   implements EntityBean
{
   
   // -------------------------------------------------------------------------
   // Members
   // -------------------------------------------------------------------------  
   
   public EntityContext mContext;
   
   // -------------------------------------------------------------------------
   // Methods
   // -------------------------------------------------------------------------  
   
   /**
   * Store the data within the provided data object into this bean.
   *
   * @param pTestEntity The Value Object containing the TestEntity values
   *
   * @ejb:interface-method view-type="remote"
   **/
   public void setValueObject( TestEntityData pTestEntity )
      throws
         InvalidValueException
   {
      // Check for Data Integrity in the Value Object
      if( pTestEntity == null ) {
         throw new InvalidValueException( "object.undefined", "TestEntity" );
      }
      if( pTestEntity.getId() <= 0 ) {
         throw new InvalidValueException( "id.invalid", new String[] { "TestEntity", "Id" } );
      }
      // Check if the TestEntity is not already saved
      try {
         TestEntityHome lHome = (TestEntityHome) mContext.getEJBHome();
         TestEntity lEntity = lHome.findAnotherByName( pTestEntity.getId(), pTestEntity.getFirstName(), pTestEntity.getLastName() );
         // TestEntity with the given email address already exists retrieve instead of create a new one
         throw new InvalidValueException( "user.already.exists", new String[] { pTestEntity.getFirstName() + " " + pTestEntity.getLastName() } );
      }
      catch( FinderException fe ) {
         // That's ok
      }
      catch( RemoteException re ) {
         // Should never happens (are local)
      }
      try {
         TestEntityHome lHome = (TestEntityHome) mContext.getEJBHome();
         TestEntity lTestEntity = lHome.findByName( pTestEntity.getFirstName(), pTestEntity.getLastName() );
         if( lTestEntity.getValueObject().getId() != pTestEntity.getId() ) {
         }
      }
      catch( FinderException fe ) {
         // That's ok
      }
      catch( RemoteException re ) {
         // Should never happens (are local)
      }
      setId( pTestEntity.getId() );
      setFirstName( pTestEntity.getFirstName() );
      setLastName( pTestEntity.getLastName() );
      setPassword( pTestEntity.getPassword() );
      setEmail( pTestEntity.getEmail() );
      setAddress( pTestEntity.getAddress() );
      setCity( pTestEntity.getCity() );
      setZIP( pTestEntity.getZIP() );
      setState( pTestEntity.getState() );
      setCountry( pTestEntity.getCountry() );
      if( getCreationDate() == null ) {
         // Only set it if object is created
         setCreationDate( new Date( new java.util.Date().getTime() ) );
      }
      // After making any chances update the modification date
      setModificationDate( new Date( new java.util.Date().getTime() ) );
   }
   
   /**
   * Create and return a TestEntity data object populated with the data from
   * this bean.
   *
   * @return Returns a TestEntity value object containing the data within this
   *  bean.
   *
   * @ejb:interface-method view-type="remote"
   **/
   public TestEntityData getValueObject() {
      TestEntityData lData = new TestEntityData();
      
      lData.setId( getId() );
      lData.setFirstName( getFirstName() );
      lData.setLastName( getLastName() );
      lData.setPassword( getPassword() );
      lData.setEmail( getEmail() );
      lData.setAddress( getAddress() );
      lData.setCity( getCity() );
      lData.setZIP( getZIP() );
      lData.setState( getState() );
      lData.setCountry( getCountry() );
      lData.setCreationDate( getCreationDate() );
      lData.setModificationDate( getModificationDate() );
      
      return lData;
   }
   
   /**
   * Describes the instance and its content for debugging purpose
   *
   * @return Debugging information about the instance and its content
   **/
   public String toString() {
      return "TestEntityBean [ " + getValueObject() + " ]";
   }
   
   
   /**
   * Retrive a unique creation id to use for this bean.  This will end up
   * demarcating this bean from others when it is stored as a record
   * in the database.
   *
   * @return Returns an integer that can be used as a unique creation id.
   *
   * @throws ServiceUnavailableException Indicating that it was not possible
   *                                     to retrieve a new unqiue ID because
   *                                     the service is not available
   **/
   private int generateUniqueId()
      throws ServiceUnavailableException
   {
      int lUniqueId = -1;
      try {
         Context lContext = new InitialContext();
         
         String lSequenceName = (String) lContext.lookup( 
            "java:comp/env/SequenceName" 
         );
         SequenceGeneratorHome lHome = (SequenceGeneratorHome) PortableRemoteObject.narrow(
            lContext.lookup(
               "java:comp/env/ejb/test/SequenceGenerator"
            ),
            SequenceGeneratorHome.class
         );
         SequenceGenerator lBean = (SequenceGenerator) lHome.create();
         lUniqueId = lBean.getNextNumber( lSequenceName );
         lBean.remove();  
      }
      catch ( NamingException ne ) {
         throw new ServiceUnavailableException( "Naming lookup failure: " + ne.getMessage() );
      }
      catch ( CreateException ce ) {
         throw new ServiceUnavailableException( "Failure while creating a generator session bean: " + ce.getMessage() );
      }
      catch ( RemoveException re ) {
         // When the Bean cannot be removed after a while it will be taken back by the container
         // therefore ignore this exception
      }
      catch ( RemoteException rte ) {
         throw new ServiceUnavailableException( "Remote exception occured while accessing generator session bean: " +  rte.getMessage() );
      }
      
      return lUniqueId;
   }
   
   // -------------------------------------------------------------------------
   // Properties (Getters/Setters)
   // -------------------------------------------------------------------------  
   
   /**
   * Retrieve the TestEntity's id.
   *
   * @return Returns an int representing the id of this TestEntity.
   *
   * @ejb:persistent-field
   * @ejb:pk-field
   *
   * @jboss:column-name name="Id"
   **/
   public abstract int getId();
   
   /**
   * Set the TestEntity's id.
   *
   * @param pId The id of this TestEntity. Is set at creation time.
   **/
   public abstract void setId( int pId );
   
   /**
   * Retrieve the TestEntity's FirstName.
   *
   * @return Returns an int representing the FirstName of this TestEntity.
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="First_Name"
   **/
   public abstract String getFirstName();
   
   /**
   * Set the TestEntity's FirstName.
   *
   * @param pFirstName The FirstName of this TestEntity.  Is set at creation time.
   **/
   public abstract void setFirstName( String pFirstName );
   
   /**
   * Retrieve the TestEntity's LastName.
   *
   * @return Returns an int representing the LastName of this TestEntity.
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="Last_Name"
   **/
   public abstract String getLastName();
   
   /**
   * Set the TestEntity's LastName.
   *
   * @param pLastName The LastName of this TestEntity.  Is set at creation time.
   **/
   public abstract void setLastName( String pLastName );
   
   /**
   * Retrieve the TestEntity's Password.
   *
   * @return Returns an int representing the Password of this TestEntity.
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="Password"
   **/
   public abstract String getPassword();
   
   /**
   * Set the TestEntity's Password.
   *
   * @param pPassword The Password of this TestEntity
   **/
   public abstract void setPassword( String pPassword );
   
   /**
   * Retrieve the TestEntity's Email.
   *
   * @return Returns an int representing the Email of this TestEntity.
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="Email"
   **/
   public abstract String getEmail();
   
   /**
   * Set the TestEntity's Email.
   *
   * @param pEmail The Email of this TestEntity.  Is set at creation time.
   **/
   public abstract void setEmail( String pEmail );
   
   /**
   * @return Returns the Address of this TestEntity
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="Address"
   **/
   public abstract String getAddress();
   
   /**
   * Specify the Address of this TestEntity
   *
   * @param pAddress Address of this TestEntity
   **/
   public abstract void setAddress( String pAddress );
   
   /**
   * @return Returns the City of this TestEntity
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="City"
   **/
   public abstract String getCity();
   
   /**
   * Specify the City of this TestEntity
   *
   * @param pCity City of this TestEntity
   **/
   public abstract void setCity( String pCity );
   
   /**
   * @return Returns the ZIP of this TestEntity
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="ZIP"
   **/
   public abstract String getZIP();
   
   /**
   * Specify the ZIP of this TestEntity
   *
   * @param pZIP ZIP of this TestEntity
   **/
   public abstract void setZIP( String pZIP );
   
   /**
   * @return Returns the State of this TestEntity
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="State"
   **/
   public abstract String getState();
   
   /**
   * Specify the State of this TestEntity
   *
   * @param pState State of this TestEntity
   **/
   public abstract void setState( String pState );
   
   /**
   * @return Returns the Country of this TestEntity
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="Country"
   **/
   public abstract String getCountry();
   
   /**
   * Specify the Country of this TestEntity
   *
   * @param pCountry Country of this TestEntity
   **/
   public abstract void setCountry( String pCountry );
   
   /**
   * @return Returns the creation date of this TestEntity
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="Creation_Date"
   **/
   public abstract Date getCreationDate();
   
   /**
   * Specify the creation date of this TestEntity
   *
   * @param pCreationDate Date of the creation of this TestEntity
   **/
   public abstract void setCreationDate( Date pCreationDate );
   
   /**
   * @return Returns the modification date of this TestEntity
   *
   * @ejb:persistent-field
   *
   * @jboss:column-name name="Modification_Date"
   **/
   public abstract Date getModificationDate();
   
   /**
   * Specify the modification date of this TestEntity
   *
   * @param pModificationDate Date of the modification of this TestEntity
   **/
   public abstract void setModificationDate( Date pModificationDate );
   
   // -------------------------------------------------------------------------
   // Framework Callbacks
   // -------------------------------------------------------------------------  
   
   /**
   * Create a TestEntity based on the supplied TestEntity Value Object.
   *
   * @param pTestEntity The data used to create the TestEntity.
   *
   * @throws InvalidValueException If one of the values are not correct,
   *                               this will not roll back the transaction
   *                               because the caller has the chance to
   *                               fix the problem and try again
   * @throws EJBException If no new unique ID could be retrieved this will
   *                      rollback the transaction because there is no
   *                      hope to try again
   * @throws CreateException Because we have to do so (EJB spec.)
   *
   * @ejb:create-method view-type="remote"
   **/
   public TestEntityPK ejbCreate( TestEntityData pTestEntity )
      throws
         InvalidValueException,
         EJBException,
         CreateException
   {
      // Clone the given Value Object to keep changed private
      TestEntityData lData = (TestEntityData) pTestEntity.clone();
      try {
         // Each title must have a unique id to identify itself within the DB
         lData.setId( generateUniqueId() );
      }
      catch( ServiceUnavailableException se ) {
         // The unique id could not be set therefore terminate the transaction
         // by throwing a system exception
         throw new EJBException( se.getMessage() );
      }
      // Save the new TestEntity
      setValueObject( lData );
      // This is only possible in CMPs. Otherwise return a valid PK.
      return null;
   }
   
   public void ejbPostCreate( TestEntityData pTestEntity )
   {
   }
   
   public void setEntityContext( EntityContext lContext )
   {
      mContext = lContext;
   }
   
   public void unsetEntityContext()
   {
      mContext = null;
   }
   
   public void ejbActivate()
   {
   }
   
   public void ejbPassivate()
   {
   }
   
   public void ejbLoad()
   {
   }
   
   public void ejbStore()
   {
   }
   
   public void ejbRemove()
      throws
         RemoveException
   {
   }
}
