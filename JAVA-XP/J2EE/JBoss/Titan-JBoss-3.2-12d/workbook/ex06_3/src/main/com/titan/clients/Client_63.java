package com.titan.clients;

import com.titan.customer.CustomerHomeRemote;
import com.titan.customer.CustomerRemote;
import com.titan.customer.Name;
import com.titan.customer.AddressDO;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 * Example showing use of relationship entity beans
 * 
 */

public class Client_63 
{

   public static void main(String [] args) throws Exception 
   {

      // obtain CustomerHome
      Context jndiContext = getInitialContext();
      Object obj = jndiContext.lookup("CustomerHomeRemote");
      CustomerHomeRemote home = (CustomerHomeRemote) 
         javax.rmi.PortableRemoteObject.narrow(obj, CustomerHomeRemote.class);

      System.out.println("Creating Customer 1..");
      // create a Customer
      Integer primaryKey = new Integer(1);
      CustomerRemote customer = home.create(primaryKey);
 
      // create an address data object
      System.out.println("Creating AddressDO data object..");
      AddressDO address = new AddressDO("1010 Colorado",
                                        "Austin", "TX", "78701");

      // set address in Customer bean
      System.out.println("Setting Address in Customer 1...");
      customer.setAddress(address);
   
      System.out.println("Acquiring Address data object from Customer 1...");
      address = customer.getAddress();

      System.out.println("Customer 1 Address data: ");
      System.out.println(address.getStreet( ));
      System.out.println(address.getCity( )+","+
                         address.getState()+" "+
                         address.getZip());


      // create a new address
      System.out.println("Creating new AddressDO data object..");
      address = new AddressDO("1600 Pennsylvania Avenue NW",
                              "DC", "WA", "20500");
  
      // change customer's address
      System.out.println("Setting new Address in Customer 1...");
      customer.setAddress(address);

      address = customer.getAddress();
      System.out.println("Customer 1 Address data: ");
      System.out.println(address.getStreet( ));
      System.out.println(address.getCity( )+","+
                         address.getState()+" "+
                         address.getZip());

      // remove Customer to clean up
      System.out.println("Removing Customer 1...");
      customer.remove();
   }
    
   public static Context getInitialContext() 
      throws javax.naming.NamingException 
   {
      return new InitialContext();
   }

}
