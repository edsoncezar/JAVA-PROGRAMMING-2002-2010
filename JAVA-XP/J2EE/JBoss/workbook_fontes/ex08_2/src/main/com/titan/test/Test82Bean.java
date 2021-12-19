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

public class Test82Bean implements javax.ejb.SessionBean 
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

         CustomerLocal rmh = customerHome.create(new Integer(1006));
         rmh.setName(new Name("Monson-Haefel", "Richard"));
         rmh.setHasGoodCredit(true);
         out.println("added Richard Monson-Haefel");


         AddressLocal addr = null;
         addr = addressHome.createAddress("123 Boston Road", "Billerica", "MA", "01821");
         bill.setHomeAddress(addr);

         addr = addressHome.createAddress("Etwa Schweitzer Strasse", "Irgendwo", "Switzerland", "07711");
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

         CreditCompanyLocal amex = creditCompanyHome.create(new Integer(2), "American Express");
         addr = addressHome.createAddress("Somewhere", "Atlanta", "GA", "06660");
         amex.setAddress(addr);

         CreditCardLocal cc;
         cc = cardHome.create(new Date(), "5324 9393 1010 2929", "Bill Burke");
         cc.setCreditCompany(capitalOne);
         cc.setOrganization("Master Card");
         bill.setCreditCard(cc);

         cc = cardHome.create(new Date(), "5311 5000 1011 2333", "Sacha Labourey");
         cc.setCreditCompany(capitalOne);
         cc.setOrganization("Master Card");
         sacha.setCreditCard(cc);

         cc = cardHome.create(new Date(), "5310 5131 7711 2663", "March Fleury");
         cc.setCreditCompany(capitalOne);
         cc.setOrganization("Master Card");
         marc.setCreditCard(cc);

         cc = cardHome.create(new Date(), "5820 5881 7788 2688", "Jane Swift");
         cc.setCreditCompany(amex);
         cc.setOrganization("American Express");
         swifty.setCreditCard(cc);

         cc = cardHome.create(new Date(), "5450 5441 7448 2644", "Nomar Garciaparra");
         cc.setCreditCompany(amex);
         cc.setOrganization("American Express");
         nomar.setCreditCard(cc);

         // Create ships
         ShipLocal queen = shipHome.create(new Integer(1), "Queen Mary", 100000.0);
         ShipLocal titanic = shipHome.create(new Integer(2), "Titanic", 200000.0);
         
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
         atlanticCustomers.add(bill);
         atlanticCustomers.add(marc);
         atlanticCustomers.add(swifty);

         ReservationLocal alaskanReservation = reservationHome.create(alaskan, alaskanCustomers);
         HashSet alaskanCabins = new HashSet();
         alaskanCabins.add(cabin1);
         alaskanCabins.add(cabin2);
         alaskanReservation.setCabins(alaskanCabins);
         alaskanReservation.setAmountPaid(40000.0);
         ReservationLocal atlanticReservation = reservationHome.create(atlantic, atlanticCustomers);
         HashSet atlanticCabins = new HashSet();
         atlanticCabins.add(cabin3);
         atlanticCabins.add(cabin4);
         atlanticCabins.add(cabin5);
         atlanticReservation.setCabins(atlanticCabins);
         atlanticReservation.setAmountPaid(10000.0);
         
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }


   public String test82a() throws RemoteException
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
         
         out.println("USING DISTINCT");
         out.println("--------------------------------");
         out.println("Non-distinct: ");
         out.println("SELECT OBJECT( cust)");
         out.println("FROM Reservation res, IN (res.customers) cust");
         Collection customers = customerHome.findAllCustomersWithReservations();
         Iterator it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName() + " has a reservation.");
         }
         out.println("");
         out.println("Distinct: ");
         out.println("SELECT DISTINCT OBJECT( cust)");
         out.println("FROM Reservation res, IN (res.customers) cust");
         customers = customerHome.findDistinctCustomersWithReservations();
         it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName() + " has a reservation.");
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

   public String test82b() throws RemoteException
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
         
         obj = jndiContext.lookup("ShipHomeLocal");
         ShipHomeLocal shipHome = (ShipHomeLocal)obj; 

         out.println("THE WHERE CLAUSE AND LITERALS");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( c ) FROM Customer AS c");
         out.println("WHERE c.creditCard.organization = 'American Express'");
         Collection customers = customerHome.findByAmericanExpress();
         Iterator it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName() + " has an American Express card.");
         }
         out.println("");

         out.println("SELECT OBJECT( s ) FROM Ship AS s");
         out.println("WHERE s.tonnage = 100000.0");
         Collection ships = shipHome.findByTonnage100000();
         it = ships.iterator();
         while (it.hasNext())
         {
            ShipLocal ship = (ShipLocal)it.next();
            out.println("   Ship " + ship.getName() + " as tonnage 100000.0");
         }
         out.println("");

         out.println("SELECT OBJECT( c ) FROM Customer AS c");
         out.println("WHERE c.hasGoodCredit = TRUE");
         customers = customerHome.findByGoodCredit();
         it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName() + " has good credit.");
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

   public String test82c() throws RemoteException
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
         
         obj = jndiContext.lookup("ShipHomeLocal");
         ShipHomeLocal shipHome = (ShipHomeLocal)obj; 

         obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruiseHome = (CruiseHomeLocal)obj; 

         out.println("THE WHERE CLAUSE AND INPUT PARAMETERS");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( c ) FROM Customer AS c");
         out.println("WHERE c.homeAddress.state = ?2");
         out.println("AND c.homeAddress.city = ?1");
         out.println("Get customers from Billerica, MA");
         Collection customers = customerHome.queryByCity("Billerica", "MA");
         Iterator it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName() + " is from Billerica.");
         }
         out.println("");

         out.println("SELECT OBJECT( crs ) FROM Cruise AS crs");
         out.println("WHERE crs.ship = ?1");
         out.println("Get cruises on the Titanic");
         ShipLocal titanic = shipHome.findByPrimaryKey(new Integer(2));

         Collection cruises = cruiseHome.findByShip(titanic);
         it = cruises.iterator();
         while (it.hasNext())
         {
            CruiseLocal cruise = (CruiseLocal)it.next();
            out.println("   " + cruise.getName() + " is a Titanic cruise.");
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

   public String test82d() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();

         Object obj = jndiContext.lookup("ReservationHomeLocal");
         ReservationHomeLocal reservationHome = (ReservationHomeLocal)obj; 

         out.println("THE WHERE CLAUSE AND CDATA Sections");
         out.println("--------------------------------");
         out.println("![CDATA[");
         out.println("SELECT OBJECT( r ) FROM Rservation r");
         out.println("WHERE r.amountPaid > ?1");
         out.println("]]>");

         Collection reservations = reservationHome.findWithPaymentGreaterThan(new Double(20000.0));
         Iterator it = reservations.iterator();
         while (it.hasNext())
         {
            ReservationLocal reservation = (ReservationLocal)it.next();
            out.println("   found reservation with amount paid > 20000.0: " + reservation.getAmountPaid());
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


   public String test82e() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();
         
         Object obj = jndiContext.lookup("ShipHomeLocal");
         ShipHomeLocal shipHome = (ShipHomeLocal)obj; 

         out.println("THE WHERE CLAUSE AND BETWEEN");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( s ) FROM Ship s");
         out.println("WHERE s.tonnage BETWEEN 80000.00 and 130000.00");
         Collection ships = shipHome.findByTonnageBetween();
         Iterator it = ships.iterator();
         while (it.hasNext())
         {
            ShipLocal ship = (ShipLocal)it.next();
            out.println("   " + ship.getName() + " has tonnage " + ship.getTonnage());
         }
         out.println("");
         out.println("SELECT OBJECT( s ) FROM Ship s");
         out.println("WHERE s.tonnage NOT BETWEEN 80000.00 and 130000.00");
         ships = shipHome.findByTonnageNotBetween();
         it = ships.iterator();
         while (it.hasNext())
         {
            ShipLocal ship = (ShipLocal)it.next();
            out.println("   " + ship.getName() + " has tonnage " + ship.getTonnage());
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


   public String test82f() throws RemoteException
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
         
         out.println("THE WHERE CLAUSE AND IN");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( c ) FROM Customer c");
         out.println("WHERE c.homeAddress.state IN ('GA', 'MA')");
         Collection customers = customerHome.findInStates();
         Iterator it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName());
         }
         out.println("");

         out.println("SELECT OBJECT( c ) FROM Customer c");
         out.println("WHERE c.homeAddress.state NOT IN ('GA', 'MA')");
         customers = customerHome.findNotInStates();
         it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName());
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


   public String test82g() throws RemoteException
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
         
         out.println("THE WHERE CLAUSE AND IS NULL");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( c ) FROM Customer c");
         out.println("WHERE c.homeAddress IS NULL");
         Collection customers = customerHome.findHomeAddressIsNull();
         Iterator it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName());
         }
         out.println("");

         out.println("SELECT OBJECT( c ) FROM Customer c");
         out.println("WHERE c.homeAddress IS NOT NULL");
         customers = customerHome.findHomeAddressIsNotNull();
         it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getFirstName());
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


   public String test82h() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruiseHome = (CruiseHomeLocal)obj;
         
         out.println("THE WHERE CLAUSE AND IS EMPTY");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( crs ) FROM Cruise crs");
         out.println("WHERE crs.reservations IS EMPTY");
         Collection cruises = cruiseHome.findEmptyReservations();
         Iterator it = cruises.iterator();
         while (it.hasNext())
         {
            CruiseLocal cruise = (CruiseLocal)it.next();
            out.println("   " + cruise.getName() + " is empty.");
         }
         /*
          * At the writing of this workbook, JBoss 3.0
          * throws a runtime exception when invoking this
          * finder

         out.println("");
         out.println("SELECT OBJECT( crs ) FROM Cruise crs");
         out.println("WHERE crs.reservations IS NOT EMPTY");
         cruises = cruiseHome.findNotEmptyReservations();
         it = cruises.iterator();
         while (it.hasNext())
         {
            CruiseLocal cruise = (CruiseLocal)it.next();
            out.println("   " + cruise.getName() + " is not empty.");
         }
         out.println("");
         */
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }


   public String test82i() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         // obtain Home interfaces
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruiseHome = (CruiseHomeLocal)obj;
         
         obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerHome = (CustomerHomeLocal)obj;
         
         out.println("THE WHERE CLAUSE AND MEMBER OF");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( crs ) FROM Cruise crs,");
         out.println("IN (crs.reservations) res, Customer cust");
         out.println("WHERE cust = ?1 ANT cust MEMBER OF res.customers");
         out.println("Use Bill Burke");
         CustomerLocal bill = customerHome.findByPrimaryKey(new Integer(1001));

         Collection cruises = cruiseHome.findMemberOf(bill);
         Iterator it = cruises.iterator();
         while (it.hasNext())
         {
            CruiseLocal cruise = (CruiseLocal)it.next();
            out.println("   Bill is member of " + cruise.getName());
         }
         out.println("");

         out.println("SELECT OBJECT( crs ) FROM Cruise crs,");
         out.println("IN (crs.reservations) res, Customer cust");
         out.println("WHERE cust = ?1 ANT cust NOT MEMBER OF res.customers");
         out.println("Use Nomar Garciaparra");
         CustomerLocal nomar = customerHome.findByPrimaryKey(new Integer(1005));
         cruises = cruiseHome.findNotMemberOf(nomar);
         it = cruises.iterator();
         while (it.hasNext())
         {
            CruiseLocal cruise = (CruiseLocal)it.next();
            out.println("   Nomar is not member of " + cruise.getName());
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

   public String test82j() throws RemoteException
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
         
         out.println("THE WHERE CLAUSE AND LIKE");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( c ) FROM Customer c");
         out.println("WHERE c.lastName LIKE '%-%'");
         Collection customers = customerHome.findHyphenatedLastNames();
         Iterator it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getLastName());
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

   public String test82k() throws RemoteException
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
         
         out.println("THE WHERE CLAUSE AND FUNCTIONAL EXPRESSIONS");
         out.println("--------------------------------");
         out.println("SELECT OBJECT( c ) FROM Customer c");
         out.println("WHERE LENGTH(c.lastName) > 6 AND");
         out.println("LOCATE(c.lastName, 'Monson') > -1");

         Collection customers = customerHome.findByLastNameLength();
         Iterator it = customers.iterator();
         while (it.hasNext())
         {
            CustomerLocal cust = (CustomerLocal)it.next();
            Name name = cust.getName();
            out.println("   " + name.getLastName());
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
