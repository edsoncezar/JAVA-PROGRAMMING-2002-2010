package com.titan.travelagent;

import com.titan.cabin.CabinLocal;
import com.titan.cabin.CabinHomeLocal;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.ejb.EJBException;
import java.util.Properties;
import java.util.Vector;

public class TravelAgentBean implements javax.ejb.SessionBean 
{

   public void ejbCreate() 
   {
      // Do nothing.
   }

   public String [] listCabins(int shipID, int bedCount) 
   {

      try 
      {
         javax.naming.Context jndiContext = new InitialContext();
         CabinHomeLocal home = (CabinHomeLocal)
            jndiContext.lookup("java:comp/env/ejb/CabinHomeLocal");

         Vector vect = new Vector();
         for (int i = 1; ; i++) 
         {
            Integer pk = new Integer(i);
            CabinLocal cabin = null;
            try 
            {
               cabin = home.findByPrimaryKey(pk);
            } 
            catch(javax.ejb.FinderException fe) 
            {
               System.out.println("Caught exception: "+fe.getMessage()+" for pk="+i); 
               break;
            }
            // Check to see if the bed count and ship ID match.
            if (cabin != null &&
                cabin.getShipId() == shipID && 
                cabin.getBedCount() == bedCount) 
            {
               String details = 
                  i+","+cabin.getName()+","+cabin.getDeckLevel();
               vect.addElement(details);
            }
         }
        
         String [] list = new String[vect.size()];
         vect.copyInto(list);
         return list;
       
      } 
      catch (NamingException ne) 
      {
         throw new EJBException(ne);
      }    
   }

   public void ejbRemove(){}
   public void ejbActivate(){}
   public void ejbPassivate(){}
   public void setSessionContext(javax.ejb.SessionContext cntx){}
}
