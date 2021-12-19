package com.titan.test;

import com.titan.customer.*;
import com.titan.cruise.*;
import com.titan.phone.*;
import com.titan.address.*;
import com.titan.ship.*;
import com.titan.reservation.*;
import com.titan.phone.*;
import com.titan.cabin.*;
import java.rmi.RemoteException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.text.*;

public class Test73Bean implements javax.ejb.SessionBean 
{
   public SessionContext context;

   public void ejbCreate() {}
   public void ejbActivate() {}
   public void ejbPassivate() {}
   public void ejbRemove() {}
   public void setSessionContext(SessionContext ctx) 
   {
      context = ctx;
   }

   public String test73() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerhome = (CustomerHomeLocal)obj;
         
         obj = jndiContext.lookup("AddressHomeLocal");
         AddressHomeLocal addresshome = (AddressHomeLocal)obj; 
         
         obj = jndiContext.lookup("CreditCardHomeLocal");
         CreditCardHomeLocal cardhome = (CreditCardHomeLocal)obj; 
         
         out.println("Creating Customer 10078, Addresses, Credit Card, Phones");
         
         CustomerLocal customer = customerhome.create(new Integer(10078)); 
         customer.setName( new Name("Star","Ringo") );
         
         out.println("Creating CreditCard");
         
         // set Credit Card info
         Calendar now = Calendar.getInstance();
         CreditCardLocal card = cardhome.create(now.getTime(), "370000000000001", "Ringo Star", "Beatles");
         
         customer.setCreditCard(card);
         
         out.println("customer.getCreditCard().getName()="+customer.getCreditCard().getNameOnCard());
         
         out.println("Creating Address");
         
         AddressLocal addr = addresshome.createAddress("780 Main Street","Beverly Hills","CA","90210");
         
         customer.setHomeAddress(addr);
         
         out.println("Address Info: "+addr.getStreet()+" "+addr.getCity()+", "+addr.getState()+" "+addr.getZip());
         
         out.println("Creating Phones");
         
         
         out.println("Adding a new type 1 phone number..");
         customer.addPhoneNumber("612-555-1212",(byte)1);
         out.println("Adding a new type 2 phone number.");
         customer.addPhoneNumber("888-555-1212",(byte)2);
         
         out.println("New contents of phone list:");
         Vector vv = customer.getPhoneList();
         for (int jj=0; jj<vv.size(); jj++) 
         {
            String ss = (String)(vv.get(jj));
            out.println(ss);
         }
         
         out.println("Removing Customer EJB only");
         customer.remove();

      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }


   public InitialContext getInitialContext() 
      throws javax.naming.NamingException 
   {
      return new InitialContext();
   }
   
}
