package org.jboss.docs.cmp2.crimeportal;

import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Category;

public class ReadAheadBean implements SessionBean
{
   private SessionContext ctx;
   private GangsterHome gangsterHome;

   public void ejbCreate() throws CreateException {
      try {
         InitialContext jndi = new InitialContext();
         gangsterHome = (GangsterHome) jndi.lookup("crimeportal/Gangster"); 
      } catch(Exception e) {
         throw new CreateException("Error while looking up GangsterHome");
      }
   }

   public String createGangsterHtmlTable_none() throws FinderException {
      StringBuffer table = new StringBuffer();
      table.append("<table>");
   
      Collection gangsters = gangsterHome.findAll_none();
      for(Iterator iter = gangsters.iterator(); iter.hasNext(); ) {
         Gangster gangster = (Gangster)iter.next();
         table.append("<tr>");
         table.append("<td>").append(gangster.getName()).append("</td>");
         table.append("<td>").append(gangster.getNickName()).append("</td>");
         table.append("<td>").append(gangster.getBadness()).append("</td>");
         table.append("</tr>");
      }
   
      table.append("</table>");
      return table.toString();
   }

   public String createGangsterHtmlTable_onfind() throws FinderException {
      StringBuffer table = new StringBuffer();
      table.append("<table>");
   
      Collection gangsters = gangsterHome.findAll_onfind();
      for(Iterator iter = gangsters.iterator(); iter.hasNext(); ) {
         Gangster gangster = (Gangster)iter.next();
         table.append("<tr>");
         table.append("<td>").append(gangster.getName()).append("</td>");
         table.append("<td>").append(gangster.getNickName()).append("</td>");
         table.append("<td>").append(gangster.getBadness()).append("</td>");
         table.append("</tr>");
      }
   
      table.append("</table>");
      return table.toString();
   }

   public String createGangsterHtmlTable_onload() throws FinderException {
      StringBuffer table = new StringBuffer();
      table.append("<table>");
   
      Collection gangsters = gangsterHome.findAll_onload();
      for(Iterator iter = gangsters.iterator(); iter.hasNext(); ) {
         Gangster gangster = (Gangster)iter.next();
         table.append("<tr>");
         table.append("<td>").append(gangster.getName()).append("</td>");
         table.append("<td>").append(gangster.getNickName()).append("</td>");
         table.append("<td>").append(gangster.getBadness()).append("</td>");
         table.append("</tr>");
      }
   
      table.append("</table>");
      return table.toString();
   }

   public String createGangsterHangoutHtmlTable() throws FinderException {
      StringBuffer table = new StringBuffer();
      table.append("<table>");
   
      Collection gangsters = gangsterHome.findAll_onfind();
      for(Iterator iter = gangsters.iterator(); iter.hasNext(); ) {
         Gangster gangster = (Gangster)iter.next();
         Location hangout = gangster.getHangout();
         table.append("<tr>");
         table.append("<td>").append(gangster.getName()).append("</td>");
         table.append("<td>").append(gangster.getNickName()).append("</td>");
         table.append("<td>").append(gangster.getBadness()).append("</td>");
         table.append("<td>").append(hangout.getCity()).append("</td>");
         table.append("<td>").append(hangout.getState()).append("</td>");
         table.append("<td>").append(hangout.getZipCode()).append("</td>");
         table.append("</tr>");
      }
   
      table.append("</table>");
      return table.toString();
   }

   public String createGangsterHtmlTable_no_tx() throws FinderException {
      StringBuffer table = new StringBuffer();
      table.append("<table>");
   
      Collection gangsters = gangsterHome.findFour();
      for(Iterator iter = gangsters.iterator(); iter.hasNext(); ) {
         Gangster gangster = (Gangster)iter.next();
         table.append("<tr>");
         table.append("<td>").append(gangster.getName()).append("</td>");
         table.append("<td>").append(gangster.getNickName()).append("</td>");
         table.append("<td>").append(gangster.getBadness()).append("</td>");
         table.append("</tr>");
      }
   
      table.append("</table>");
      return table.toString();
   }

   public String createGangsterHtmlTable_with_tx() throws FinderException {
      UserTransaction tx = null;
      try {
         InitialContext ctx = new InitialContext();
         tx = (UserTransaction) ctx.lookup("UserTransaction");
         tx.begin();

         String table = createGangsterHtmlTable_no_tx();

         if(tx.getStatus() == Status.STATUS_ACTIVE) {
            tx.commit();
         }
         return table;
      } catch(Exception e) {
         try {
            if(tx != null) tx.rollback();
         } catch(SystemException unused) {
            // eat the exception we are exceptioning out anyway
         }
         if(e instanceof FinderException) {
            throw (FinderException) e;
         }
         if(e instanceof RuntimeException) {
            throw (RuntimeException) e;
         }
         throw new EJBException(e);
      }
   }

   public void setSessionContext(SessionContext ctx)
   {
      ctx = ctx;
   }

   public void ejbActivate() { }

   public void ejbPassivate() { }

   public void ejbRemove() { }
}
