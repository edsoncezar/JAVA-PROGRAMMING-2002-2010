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

public class Test81Bean implements javax.ejb.SessionBean 
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

   public String initialize() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerHome = (CustomerHomeLocal)obj;
         
         obj = jndiContext.lookup("AddressHomeLocal");
         AddressHomeLocal addressHome = (AddressHomeLocal)obj; 
         
         obj = jndiContext.lookup("CreditCardHomeLocal");
         CreditCardHomeLocal cardHome = (CreditCardHomeLocal)obj; 

         obj = jndiContext.lookup("CreditCompanyHomeLocal");
         CreditCompanyHomeLocal creditCompanyHome = (CreditCompanyHomeLocal)obj; 

         obj = jndiContext.lookup("ShipHomeLocal");
         ShipHomeLocal shipHome = (ShipHomeLocal)obj; 

         obj = jndiContext.lookup("CabinHomeLocal");
         CabinHomeLocal cabinHome = (CabinHomeLocal)obj; 

         obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruiseHome = (CruiseHomeLocal)obj; 

         obj = jndiContext.lookup("ReservationHomeLocal");
         ReservationHomeLocal reservationHome = (ReservationHomeLocal)obj; 

         // Create customers

         CustomerLocal bill = customerHome.create(new Integer(1001));
         bill.setName(new Name("Burke", "Bill"));
         bill.setHasGoodCredit(true);
         out.println("added Bill Burke");

         CustomerLocal sacha = customerHome.create(new Integer(1002));
         sacha.setName(new Name("Labourey", "Sacha"));
         sacha.setHasGoodCredit(false); // Sacha get's bad credit ;)
         out.println("added Sacha Labourey");

         CustomerLocal marc = customerHome.create(new Integer(1003));
         marc.setName(new Name("Fleury", "Marc"));
         marc.setHasGoodCredit(true);
         out.println("added Marc Fleury");

         CustomerLocal swifty = customerHome.create(new Integer(1004));
         swifty.setName(new Name("Swift", "Jane"));
         swifty.setHasGoodCredit(true);
         out.println("added Jane Swift");

         CustomerLocal nomar = customerHome.create(new Integer(1005));
         nomar.setName(new Name("Garciaparra", "Nomar"));
         nomar.setHasGoodCredit(true);
         out.println("added Nomar Garciaparra");


         AddressLocal addr = null;
         addr = addressHome.createAddress("123 Boston Road", "Billerica", "MA", "01821");
         bill.setHomeAddress(addr);

         addr = addressHome.createAddress("Etwa Schweitzer Strasse", "Neuchatel", "Switzerland", "07711");
         sacha.setHomeAddress(addr);

         addr = addressHome.createAddress("Sharondale Dr.", "Atlanta", "GA", "06660");
         marc.setHomeAddress(addr);

         addr = addressHome.createAddress("1 Beacon Street", "Boston", "MA", "02115");
         swifty.setHomeAddress(addr);

         addr = addressHome.createAddress("1 Yawkey Way", "Boston", "MA", "02116");
         nomar.setHomeAddress(addr);

         CreditCompanyLocal capitalOne = creditCompanyHome.create(new Integer(1), "Capital One");
         addr = addressHome.createAddress("West Broad Street", "Richmond", "VA", "23233");
         capitalOne.setAddress(addr);

         CreditCompanyLocal mbna = creditCompanyHome.create(new Integer(2), "MBNA");
         addr = addressHome.createAddress("Somewhere", "Atlanta", "GA", "06660");
         mbna.setAddress(addr);

         CreditCardLocal cc;
         cc = cardHome.create(new Date(), "5324 9393 1010 2929", "Bill Burke");
         cc.setCreditCompany(capitalOne);
         bill.setCreditCard(cc);

         cc = cardHome.create(new Date(), "5311 5000 1011 2333", "Sacha Labourey");
         cc.setCreditCompany(capitalOne);
         sacha.setCreditCard(cc);

         cc = cardHome.create(new Date(), "5310 5131 7711 2663", "Marc Fleury");
         cc.setCreditCompany(capitalOne);
         marc.setCreditCard(cc);

         cc = cardHome.create(new Date(), "5810 5881 7788 2688", "Jane Swift");
         cc.setCreditCompany(mbna);
         swifty.setCreditCard(cc);

         cc = cardHome.create(new Date(), "5450 5441 7448 2644", "Nomar Garciaparra");
         cc.setCreditCompany(mbna);
         nomar.setCreditCard(cc);

         // Create ships
         ShipLocal queen = shipHome.create(new Integer(1), "Queen Mary", 40.0);
         ShipLocal titanic = shipHome.create(new Integer(2), "Titanic", 80.0);
         
         // Create cabins
         CabinLocal cabin1 = cabinHome.create(new Integer(1));
         cabin1.setDeckLevel(1);
         cabin1.setShip(queen);
         cabin1.setBedCount(1);
         cabin1.setName("Queen Cabin 1");

         CabinLocal cabin2 = cabinHome.create(new Integer(2));
         cabin2.setDeckLevel(1);
         cabin2.setShip(queen);
         cabin2.setBedCount(1);
         cabin2.setName("Queen Cabin 2");
         
         CabinLocal cabin3 = cabinHome.create(new Integer(3));
         cabin3.setDeckLevel(1);
         cabin3.setShip(titanic);
         cabin3.setBedCount(2);
         cabin3.setName("Titanic Cabin 1");
         
         CabinLocal cabin4 = cabinHome.create(new Integer(4));
         cabin4.setDeckLevel(1);
         cabin4.setShip(titanic);
         cabin4.setBedCount(2);
         cabin4.setName("Titanic Cabin 2");
         
         CabinLocal cabin5 = cabinHome.create(new Integer(5));
         cabin5.setDeckLevel(1);
         cabin5.setShip(titanic);
         cabin5.setBedCount(2);
         cabin5.setName("Titanic Cabin 3");

         // Create cruise
         CruiseLocal alaskan = cruiseHome.create("Alaskan Cruise", queen);
         CruiseLocal atlantic = cruiseHome.create("Atlantic Cruise", titanic);

         // Create Reservations
         ArrayList alaskanCustomers = new ArrayList();
         alaskanCustomers.add(bill);
         alaskanCustomers.add(sacha);
         alaskanCustomers.add(nomar);
         ArrayList atlanticCustomers = new ArrayList();
         atlanticCustomers.add(marc);
         atlanticCustomers.add(swifty);

         ReservationLocal alaskanReservation = reservationHome.create(alaskan, alaskanCustomers);
         HashSet alaskanCabins = new HashSet();
         alaskanCabins.add(cabin1);
         alaskanCabins.add(cabin2);
         alaskanReservation.setCabins(alaskanCabins);
         ReservationLocal atlanticReservation = reservationHome.create(atlantic, atlanticCustomers);
         HashSet atlanticCabins = new HashSet();
         atlanticCabins.add(cabin3);
         atlanticCabins.add(cabin4);
         atlanticCabins.add(cabin5);
         atlanticReservation.setCabins(atlanticCabins);
         
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }


   public String test81a() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerHome = (CustomerHomeLocal)obj;
         
         obj = jndiContext.lookup("AddressHomeLocal");
         AddressHomeLocal addressHome = (AddressHomeLocal)obj; 

         out.println("FIND METHODS");
         out.println("--------------------------------");
         out.println("SELECT OBJECT(c) FROM Customer c");
         out.println("WHERE c.lastName = ?1 AND c.firstName = ?2");
         out.println("Find Bill Burke using findByName");
         CustomerLocal bill = customerHome.findByName("Burke", "Bill");
         Name name = bill.getName();
         out.println("   Found Bill " + name.getLastName());
         out.println("");

         out.println("SELECT OBJECT(c) FROM Customer c");
         out.println("WHERE c.hasGoodCredit = TRUE");
         out.println("Find all with good credit.  Sacha has bad credit!");
         Collection goodCredit = customerHome.findByGoodCredit();

         Iterator it = goodCredit.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            name = cust.getName();
            out.println("   " + name.getFirstName() + " has good credit.");
         }
         out.println("");

         
         out.println("SELECT METHODS");
         out.println("--------------------------------");

         out.println("SELECT a.zip FROM Address AS a");
         out.println("WHERE a.state = ?1");
         out.println("show ejbSelectZipCodes with queryZipCodes");
         Collection zips = addressHome.queryZipCodes("MA");
         it = zips.iterator();
         while (it.hasNext())
         {
            String zip = (String)it.next();
            out.println("   " + zip);
         }
         out.println("");

         out.println("SELECT OBJECT(a) FROM Address AS a");
         out.println("show ejbSelectAll with queryAll");
         Collection all = addressHome.queryAll();
         it = all.iterator();
         while (it.hasNext())
         {
            AddressLocal addr = (AddressLocal)it.next();
            out.println("   " + addr.getStreet());
            out.println("   " +addr.getCity() + ", " + addr.getState() + " " + addr.getZip());
            out.println("");
         }
         out.println("");

         out.println("SELECT OBJECT(C) FROM Customer AS c");
         out.println("WHERE c.homeAddress = ?1");
         out.println("show ejbSelectCustomer using Bill's address.");
         CustomerLocal cust = addressHome.queryCustomer(bill.getHomeAddress());
         name = cust.getName();
         out.println("The customer is: " );
         out.println("   " + name.getFirstName() + " " + name.getLastName());
         out.println("   " + cust.getHomeAddress().getStreet());
         out.println("   " + cust.getHomeAddress().getCity() 
                     + ", " + cust.getHomeAddress().getState() 
                     + " " + cust.getHomeAddress().getZip());
         out.println("");
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }


   public String test81b() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerHome = (CustomerHomeLocal)obj;
         
         out.println("SIMPLE QUERIES with PATHS");
         out.println("--------------------------------");
         out.println("SELECT c.lastName FROM Customer AS c");
         Collection names = customerHome.queryLastNames();
         Iterator it = names.iterator();
         while (it.hasNext())
         {
            String name = (String)it.next();
            out.println("   " + name);
         }
         out.println("");

         out.println("SELECT c.creditCard FROM Customer c");
         Collection ccs = customerHome.queryCreditCards();
         it = ccs.iterator();
         while (it.hasNext())
         {
            CreditCardLocal cc = (CreditCardLocal)it.next();
            out.println("   " + cc.getNumber());
         }
         out.println("");

         out.println("SELECT c.homeAddress.city FROM Customer c");
         Collection cities = customerHome.queryCities();
         it = cities.iterator();
         while (it.hasNext())
         {
            String city = (String)it.next();
            out.println("   " + city);
         }
         out.println("");

         
         out.println("SELECT c.creditCard.creditCompany.address");
         out.println("FROM Customer AS c");
         Collection addrs = customerHome.queryCreditCompanyAddresses();
         it = addrs.iterator();
         while (it.hasNext())
         {
            AddressLocal addr = (AddressLocal)it.next();
            out.println("   " + addr.getStreet());
            out.println("   " + addr.getCity() + ", " + addr.getState() + " " + addr.getZip());
            out.println("");
         }
         out.println("");

         out.println("SELECT c.creditCard.creditCompany.address.city");
         out.println("FROM Customer AS c");
         cities = customerHome.queryCreditCompanyCities();
         it = cities.iterator();
         while (it.hasNext())
         {
            String city = (String)it.next();
            out.println("   " + city);
         }
         out.println("");

      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }

   public String test81c() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerHome = (CustomerHomeLocal)obj;
         
         out.println("THE IN OPERATOR");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( r )");
         out.println("FROM Customer AS c, IN( c.reservations ) AS r");
         Collection reservations = customerHome.queryReservations();
         Iterator it = reservations.iterator();
         while (it.hasNext())
         {
            ReservationLocal reservation = (ReservationLocal)it.next();
            out.println("   Reservation for " + 
                        reservation.getCruise().getName());
         }
         out.println("");

         out.println("SELECT r.cruise");
         out.println("FROM Customer AS c, IN( c.reservations ) AS r");
         Collection cruises = customerHome.queryCruises();
         it = cruises.iterator();
         while (it.hasNext())
         {
            CruiseLocal cruise = (CruiseLocal)it.next();
            
            out.println("   Cruise " + cruise.getName());
         }
         out.println("");

         out.println("SELECT cbn.ship");
         out.println("FROM Customer AS c, IN( c.reservations ) AS r,");
         out.println("IN( r.cabins ) AS cbn");
         Collection ships = customerHome.queryShips();
         it = ships.iterator();
         while (it.hasNext())
         {
            ShipLocal ship = (ShipLocal)it.next();
            
            out.println("   Ship " + ship.getName());
         }
         out.println("");

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
