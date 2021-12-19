/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package test.entity;

import test.interfaces.InvalidValueException;
import test.interfaces.TestBMPEntity;
import test.interfaces.TestBMPEntityData;
import test.interfaces.TestBMPEntityHome;
import test.interfaces.TestBMPEntityPK;
import test.interfaces.ServiceUnavailableException;
// Only necessary because of a limitation by the EJBDoclet
import test.interfaces.SequenceGenerator;
import test.interfaces.SequenceGeneratorHome;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.sql.DataSource;

/**
 * The Entity bean represents a TestEntity with BMP
 *
 * @author Andreas Schaefer
 * @version $Revision: 1.1 $
 *
 * @ejb:bean name="test/TestBMPEntity"
 *           display-name="TestEntity working on projects to support clients (BMP)"
 *           type="BMP"
 *           jndi-name="ejb/test/TestBMPEntity"
 *
 * @ejb:env-entry name="SequenceName"
 *                value="TestEntity"
 *
 * @ejb:env-entry name="DataSourceName"
 *                value="java:/DefaultDS"
 *
 * @ejb:transaction type="Required"
 *
 * @ejb:data-object extends="test.interfaces.AbstractData"
 *                  setdata="false"
 *
 * @ejb:finder signature="java.util.Collection findAll()"
 **/
public abstract class TestBMPEntityBean
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
   public void setValueObject( TestBMPEntityData pTestEntity )
      throws
         InvalidValueException
   {
      setId( pTestEntity.getId() );
      setFirstName( pTestEntity.getFirstName() );
      setLastName( pTestEntity.getLastName() );
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
   public TestBMPEntityData getValueObject() {
      TestBMPEntityData lData = new TestBMPEntityData();
      
      lData.setId( getId() );
      lData.setFirstName( getFirstName() );
      lData.setLastName( getLastName() );
      
      return lData;
   }
   
   /**
   * Describes the instance and its content for debugging purpose
   *
   * @return Debugging information about the instance and its content
   **/
   public String toString() {
      return "TestBMPEntityBean [ " + getValueObject() + " ]";
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
   
   /**
    * Mark the Entity as changed that needs to be saved
    **/
   protected abstract void makeDirty();
   /**
    * Mark the Entity as synchronized with the DB and does
    * not need to be saved
    **/
   protected abstract void makeClean();
   
   private DataSource getDataSource()
   {
      try {
         Context lContext = new InitialContext();
         
         String lDataSourceName = (String) lContext.lookup( 
            "java:comp/env/DataSourceName" 
         );
         return (DataSource) lContext.lookup( lDataSourceName );
      }
      catch ( NamingException ne ) {
         throw new EJBException( "Naming lookup failure: " + ne.getMessage() );
      }
   }
   
   private void save( boolean pIsNew ) {
      DataSource lDataSource = getDataSource();
      Connection lConnection = null;
      PreparedStatement lStatement = null;
      try {
         lConnection = lDataSource.getConnection();
         String lSQL = null;
         if( pIsNew ) {
            // Note that the Primary Key "Id" is the last to match the UPDATE statement
            lSQL = "INSERT INTO TestEntity ( First_Name, Last_Name, Id ) VALUES ( ?, ?, ? )";
         } else {
            lSQL = "UDPATE TestEntity SET First_Name = ?, Last_Name = ? WHERE Id = ?";
         }
         lStatement = lConnection.prepareStatement( lSQL );
         lStatement.setString( 1, getFirstName() );
         lStatement.setString( 2, getLastName() );
         lStatement.setInt( 3, getId() );
         lStatement.executeUpdate();
      }
      catch ( SQLException se ) {
         throw new EJBException( "Could not save record to DB: " + se.getMessage() );
      }
      finally {
         if( lStatement != null ) {
            try {
               lStatement.close();
            }
            catch( Exception e ) {}
         }
         if( lConnection != null ) {
            try {
               lConnection.close();
            }
            catch( Exception e ) {}
         }
      }
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
   **/
   public abstract String getLastName();
   
   /**
   * Set the TestEntity's LastName.
   *
   * @param pLastName The LastName of this TestEntity.  Is set at creation time.
   **/
   public abstract void setLastName( String pLastName );
   
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
   public TestBMPEntityPK ejbCreate( TestBMPEntityData pTestEntity )
      throws
         InvalidValueException,
         EJBException,
         CreateException
   {
      // Clone the given Value Object to keep changed private
      TestBMPEntityData lData = (TestBMPEntityData) pTestEntity.clone();
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
      save( true );
      // Return the PK which is mandatory in BMPs
      return new TestBMPEntityPK( getId() );
   }
   
   public void ejbPostCreate( TestBMPEntityData pTestEntity )
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
      DataSource lDataSource = getDataSource();
      Connection lConnection = null;
      PreparedStatement lStatement = null;
      try {
         lConnection = lDataSource.getConnection();
         lStatement = lConnection.prepareStatement(
            "SELECT Id, First_Name, Last_Name FROM TestEntity WHERE id = ?"
         );
         int lId = ( (TestBMPEntityPK) mContext.getPrimaryKey() ).id;
         lStatement.setInt( 1, lId );
         ResultSet lResult = lStatement.executeQuery();
         lResult.next();
         setId( lResult.getInt( 1 ) );
         setFirstName( lResult.getString( 2 ) );
         setLastName( lResult.getString( 3 ) );
         // Because this method used the attribute setter method
         // the bean is automatically marked as dirty. Therefore
         // reverse this here because it is obviosly not true
         makeClean();
      }
      catch ( SQLException se ) {
         throw new EJBException( "Could not read record from DB: " + se.getMessage() );
      }
      finally {
         if( lStatement != null ) {
            try {
               lStatement.close();
            }
            catch( Exception e ) {}
         }
         if( lConnection != null ) {
            try {
               lConnection.close();
            }
            catch( Exception e ) {}
         }
      }
   }
   
   public void ejbStore()
   {
      save( false );
   }
   
   public void ejbRemove()
      throws
         RemoveException
   {
      DataSource lDataSource = getDataSource();
      Connection lConnection = null;
      PreparedStatement lStatement = null;
      try {
         lConnection = lDataSource.getConnection();
         lStatement = lConnection.prepareStatement(
            "DELETE FROM TestEntity WHERE id = ?"
         );
         int lId = ( (TestBMPEntityPK) mContext.getPrimaryKey() ).id;
         lStatement.setInt( 1, lId );
         lStatement.executeUpdate();
      }
      catch ( SQLException se ) {
         throw new RemoveException( "Could not remove record from DB: " + se.getMessage() );
      }
      finally {
         if( lStatement != null ) {
            try {
               lStatement.close();
            }
            catch( Exception e ) {}
         }
         if( lConnection != null ) {
            try {
               lConnection.close();
            }
            catch( Exception e ) {}
         }
      }
   }
   
   public TestBMPEntityPK ejbFindByPrimaryKey( TestBMPEntityPK pKey )
      throws FinderException
   {
      DataSource lDataSource = getDataSource();
      Connection lConnection = null;
      PreparedStatement lStatement = null;
      try {
         lConnection = lDataSource.getConnection();
         lStatement = lConnection.prepareStatement(
            "SELET Id FROM TestEntity WHERE id = ?"
         );
         int lId = pKey.id;
         lStatement.setInt( 1, lId );
         ResultSet lResult = lStatement.executeQuery();
         if( lResult.next() ) {
            return pKey;
         } else {
            throw new FinderException( "Entity not found with key: " + pKey );
         }
      }
      catch ( SQLException se ) {
         throw new FinderException( "Could not find record from DB: " + se.getMessage() );
      }
      finally {
         if( lStatement != null ) {
            try {
               lStatement.close();
            }
            catch( Exception e ) {}
         }
         if( lConnection != null ) {
            try {
               lConnection.close();
            }
            catch( Exception e ) {}
         }
      }
   }
}
