/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package test.session;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import test.interfaces.ServiceUnavailableException;

/**
 * Encapsulates the retrival of DB data
 *
 * @author Andreas Schaefer
 * @version $Revision: 1.1 $
 *
 * @ejb:bean name="test/SequenceGenerator"
 *           display-name="Generates unique Identifier for an Entity"
 *           type="Stateless"
 *           jndi-name="ejb/test/SequenceGenerator"
 * @ejb:env-entry name="DataSource_Name"
 *                value="DefaultDS"
 * @ejb:resource_ref res-name="jdbc/DefaultDS"
*/
public class SequenceGeneratorBean
   implements SessionBean
{

   // -------------------------------------------------------------------------
   // Static
   // -------------------------------------------------------------------------

   // -------------------------------------------------------------------------
   // Members 
   // -------------------------------------------------------------------------

   private SessionContext mContext;
   // Only for test purposes
   private int mNextNumber = 0;

   // -------------------------------------------------------------------------
   // Methods
   // -------------------------------------------------------------------------  

   /**
    * Delivers the next sequence number from the given Sequence Name
    *
    * @param pSequenceName Name of the Sequence Generator
    *
    * @return Next sequence number
    *
    * @throws RemoteException 
    *
    * @ejb:interface-method view-type="remote"
    * @ejb:transaction type="Mandatory"
    **/
   public int getNextNumber( String pSequenceName )
      throws
         ServiceUnavailableException,
         RemoteException
   {
      Connection aConnection = null;
      Statement aStatement = null;
      try
      {
/* This normally works for DBs like PostgreSQL, Oracle and others
         // Get the Home Interface
         Context aJNDIContext = new InitialContext();
         String lDataSourceName = (String) aJNDIContext.lookup( 
            "java:comp/env/DataSource_Name" 
         );
         // Get the Datasource
         DataSource aSource = (DataSource) aJNDIContext.lookup( "java:/" + lDataSourceName );
         // Get JDBC Connection, create statement and get the result to return
         aConnection = aSource.getConnection();
         aStatement = aConnection.createStatement();
         String aSql = "SELECT Nextval( '" + pSequenceName + "' ) ";
         if( Debug.LEVEL >= Debug.REGULAR ) System.err.println( "Sql Statement: " + aSql );
         ResultSet aResult = aStatement.executeQuery( aSql );
         if( aResult.next() )
         {
            return aResult.getInt( 1 );
         }
         else
         {
            return -1;
         }
*/
// Hypersonic does not provide a feature like Sequences therefore
// we just use the highest ID and add 1 to it. Because this method
// requireds a transaction this method will block any call from other
// Beans until the transaction is finished.
// ATTENTION: this works fine as long as all EJBs get their new unique
// ID from there and nobody adds a new record to the DB other than through
// this application server.
         // Get the Home Interface
         Context aJNDIContext = new InitialContext();
         // Get the Datasource
         DataSource aSource = (DataSource) aJNDIContext.lookup( "java:/DefaultDS" );
         // Get JDBC Connection, create statement and get the result to return
         aConnection = aSource.getConnection();
         aStatement = aConnection.createStatement();
         //AS This is only working for a demo because two threads could get the same
         //AS Sequence Number because of multi threading
         String aSql = "SELECT MAX( id ) FROM " + pSequenceName;
         ResultSet aResult = aStatement.executeQuery( aSql );
         int lResult = -1;
         if( aResult.next() ) {
            lResult = aResult.getInt( 1 );
            if( lResult <= 0 ) {
               lResult = 1;
            }
         }
         return lResult + 1;
      }
      catch( SQLException se ) {
         throw new ServiceUnavailableException ( "Sequence number is broken" );
      }
      catch( NamingException ne ) {
         throw new ServiceUnavailableException ( "JNDI Lookup broken" );
      }
      finally {
         try {
            if( aStatement != null ) {
               aStatement.close();
            }
         }
         catch( Exception e ) {
         }
         try {
            if( aConnection != null ) {
               aConnection.close();
            }
         }
         catch( Exception e ) {
         }
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
   }

   /**
    * Describes the instance and its content for debugging purpose
    *
    * @return Debugging information about the instance and its content
    **/
   public String toString()
   {
      return "SequenceGeneratorBean [ " + " ]";
   }

   // -------------------------------------------------------------------------
   // Framework Callbacks
   // -------------------------------------------------------------------------
   
   public void setSessionContext( SessionContext aContext )
      throws EJBException
   {
      mContext = aContext;
   }
   
   public void ejbActivate()
      throws EJBException
   {
   }
   
   public void ejbPassivate()
      throws EJBException
   {
   }

   public void ejbRemove()
      throws EJBException
   {
   }

}
