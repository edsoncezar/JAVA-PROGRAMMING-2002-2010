package com.titan.processpayment;

import com.titan.customer.CustomerRemote;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.rmi.RemoteException;

import javax.ejb.SessionContext;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProcessPaymentBean implements javax.ejb.SessionBean
{
   // Constants definitions
   //
   final public static String CASH = "CASH";
   final public static String CREDIT = "CREDIT";
   final public static String CHECK = "CHECK";
   
   public SessionContext context; // in this bean, we keep the context
   
   public void ejbCreate () { }
   
   public boolean byCash (CustomerRemote customer, double amount)
      throws PaymentException
   {
      return process (getCustomerID (customer), amount, CASH, null, -1, null, null);
   }
   
   public boolean byCheck (CustomerRemote customer, CheckDO check, double amount)
      throws PaymentException
   {
      int minCheckNumber = getMinCheckNumber ();
      if (check.checkNumber > minCheckNumber)
      {
         return process (getCustomerID (customer), amount, CHECK,
                                        check.checkBarCode, check.checkNumber, 
                                        null, null);
      }
      else
      {
         throw new PaymentException ("Check number is too low. Must be at least " +
                                     minCheckNumber);
      }
   }
   
   public boolean byCredit (CustomerRemote customer, CreditCardDO card, double amount)
      throws PaymentException
   {
      if (card.expiration.before (new java.util.Date ()))
      {
         throw new PaymentException ("Expiration date has passed");
      }
      else
      {
         return process (getCustomerID (customer), amount,
                         CREDIT, null, -1, card.number, 
                         new java.sql.Date (card.expiration.getTime ()));
      }
   }
   
   private boolean process (Integer customerID, double amount,
                            String type, String checkBarCode,
                            int checkNumber, String creditNumber,
                            java.sql.Date creditExpDate)
      throws PaymentException
   {      
      System.out.println ("process() with customerID="+customerID+" amount="+amount);
      Connection con = null;
      PreparedStatement ps = null;
      
      try
      {
         con = getConnection ();
         ps = con.prepareStatement
            ("INSERT INTO payment (customer_id, amount, type, " + 
               "check_bar_code, check_number, credit_number, " + 
               "credit_exp_date)" +
            " VALUES (?,?,?,?,?,?,?)");
         ps.setInt (1,customerID.intValue ());
         ps.setDouble (2,amount);
         ps.setString (3,type);
         ps.setString (4,checkBarCode);
         ps.setInt (5,checkNumber);
         ps.setString (6,creditNumber);
         ps.setDate (7,creditExpDate);

         int retVal = ps.executeUpdate ();
         if (retVal!=1)
         {
            throw new EJBException ("Payment insert failed");
         }
         
         return true;
      } 
      catch(SQLException sql)
      {
         throw new EJBException (sql);
      } 
      finally
      {
         try { ps.close (); } catch (Exception e) {}
         try { con.close (); } catch (Exception e) {}
      }
   }
   
   // SessionBean implementation
   //
   public void ejbActivate () {}
   public void ejbPassivate () {}
   public void ejbRemove () {}
   public void setSessionContext (SessionContext ctx)
   {
      context = ctx;
   }
   
   // Private (helper) methods
   //
   private Integer getCustomerID (CustomerRemote customer)
   {
      try
      {
         return (Integer)customer.getPrimaryKey ();
      } 
      catch(RemoteException re)
      {
         throw new EJBException (re);
      }
   }
   
   private Connection getConnection () throws SQLException
   {
      try
      {
         InitialContext jndiCntx = new InitialContext ();
         DataSource ds = (DataSource)
         jndiCntx.lookup ("java:comp/env/jdbc/titanDB");
         return ds.getConnection ();
      } 
      catch(NamingException ne)
      {
         throw new EJBException (ne);
      }
   }
   
   private int getMinCheckNumber ()
   {
      try
      {
         InitialContext jndiCntx = new InitialContext ( );
         Integer value = (Integer)
         jndiCntx.lookup ("java:comp/env/minCheckNumber");
         return value.intValue ();
      }
      catch(NamingException ne)
      {
         throw new EJBException (ne);
      }
   }   

   // Create DB environmnet
   //
   public void makeDbTable () 
   {
      PreparedStatement ps = null;
      Connection con = null;
      
      try
      {
         con = this.getConnection ();
         
         System.out.println("Creating table PAYMENT...");
         ps = con.prepareStatement ("CREATE TABLE PAYMENT ( " +
                                     "CUSTOMER_ID INT, " +
                                     "AMOUNT DECIMAL (8,2), " +
                                     "TYPE CHAR (10), " +
                                     "CHECK_BAR_CODE CHAR (50), " +
                                     "CHECK_NUMBER INTEGER, " +
                                     "CREDIT_NUMBER CHAR (20), " +
                                     "CREDIT_EXP_DATE DATE" +
                                    ")" );
         ps.execute ();
         System.out.println("...done!");
      }
      catch (SQLException sql)
      {
         throw new EJBException (sql);
      }
      finally
      {
         try { ps.close (); } catch (Exception e) {}
         try { con.close (); } catch (Exception e) {}
      }
   }
   
   public void dropDbTable ()
   {
      PreparedStatement ps = null;
      Connection con = null;
      
      try
      {
         con = this.getConnection ();
         
         System.out.println("Dropping table PAYMENT...");
         ps = con.prepareStatement ("DROP TABLE PAYMENT");
         ps.execute ();
         System.out.println("...done!");
      }
      catch (SQLException sql)
      {
         throw new EJBException (sql);
      }
      finally
      {
         try { ps.close (); } catch (Exception e) {}
         try { con.close (); } catch (Exception e) {}
      }
   }

}
