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

public class Test72Bean implements javax.ejb.SessionBean 
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

   public String test72a() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("ShipHomeLocal");
         ShipHomeLocal shiphome = (ShipHomeLocal)obj; 
         
         out.println("Creating Ships");
         
         // Create some Ship beans - manually set key
         ShipLocal shipA = shiphome.create(new Integer(1001), "Ship A", 30000.0); 
         ShipLocal shipB = shiphome.create(new Integer(1002), "Ship B", 40000.0); 
         
         out.println("PK="+shipA.getId()+" name="+shipA.getName()+" tonnage="+shipA.getTonnage());
         out.println("PK="+shipB.getId()+" name="+shipB.getName()+" tonnage="+shipB.getTonnage());
         
         out.println("Creating Cruises");
         
         // Create some Cruise beans - automatic key generation by CMP engine
         // Link 1-3 to Ship A, 4-6 to Ship B
         obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruisehome = (CruiseHomeLocal)obj; 
         
         CruiseLocal cruises[] = new CruiseLocal[6];
         cruises[0] = cruisehome.create("Cruise 1", shipA);
         cruises[1] = cruisehome.create("Cruise 2", shipA);
         cruises[2] = cruisehome.create("Cruise 3", shipA);
         cruises[3] = cruisehome.create("Cruise 4", shipB);
         cruises[4] = cruisehome.create("Cruise 5", shipB);
         cruises[5] = cruisehome.create("Cruise 6", shipB);
         
         for (int jj=0; jj<6; jj++) 
         {
            CruiseLocal cc = cruises[jj];
            out.println(cc.getName()+" is using "+cc.getShip().getName());
         }
         
         out.println("Changing Cruise 4 to use same ship as Cruise 1");
         
         ShipLocal newship = cruises[0].getShip();
         cruises[3].setShip(newship);
         
         for (int jj=0; jj<6; jj++) 
         {
            CruiseLocal cc = cruises[jj];
            out.println(cc.getName()+" is using "+cc.getShip().getName()); 
         }

         out.println("Removing created beans");
         cruises[0].remove();
         cruises[1].remove();
         cruises[2].remove();
         cruises[3].remove();
         cruises[4].remove();
         cruises[5].remove();
         shipA.remove();
         shipB.remove();
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }


   public String test72b() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruisehome = (CruiseHomeLocal)obj;
         
         out.println("Creating Cruises");
         
         // Create some Cruise beans - leave ship reference empty since we don't care
         CruiseLocal cruiseA = cruisehome.create("Cruise A", null); 
         CruiseLocal cruiseB = cruisehome.create("Cruise B", null); 
         
         out.println("name="+cruiseA.getName());
         out.println("name="+cruiseB.getName());
         
         out.println("Creating Reservations");
         
         // Create some Reservation beans - automatic key generation by CMP engine
         // Link 1-3 to Cruise A, 4-6 to Cruise B
         obj = jndiContext.lookup("ReservationHomeLocal");
         ReservationHomeLocal reservationhome = (ReservationHomeLocal)obj; 
         ReservationLocal reservations[] = new ReservationLocal[6];
         Calendar date = Calendar.getInstance();
         date.set(2002,10,1);
         
         // Leave the Customers collection null in the create() call, we don't care about it right now
         
         for (int i = 0; i < 6; i++)
         {
            CruiseLocal cruise = (i < 3) ? cruiseA : cruiseB; 
            reservations[i] = reservationhome.create(cruise,new ArrayList());
            reservations[i].setDate(date.getTime());
            reservations[i].setAmountPaid((i + 1) * 1000.0);
            date.add(Calendar.DAY_OF_MONTH, 7);
         }
         
         DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
         
         // Report information on the 6 reservations 
         
         for (int jj=0; jj<6; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!"); 
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename);
         }
         
         out.println("Testing CruiseB.setReservations( CruiseA.getReservations() )");
         
         // show the effect of a simple "setReservations" on a cruise
         
         Collection a_reservations = cruiseA.getReservations();
         cruiseB.setReservations( a_reservations );
         
         // Report information on the 6 reservations 
         
         for (int jj=0; jj<6; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename);
         }

         out.println("Removing created beans.");
         reservations[0].remove();
         reservations[1].remove();
         reservations[2].remove();
         reservations[3].remove();
         reservations[4].remove();
         reservations[5].remove();
         cruiseA.remove();
         cruiseB.remove();
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }
   
   public String test72c() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      try
      {
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruisehome = (CruiseHomeLocal)obj;
         
         out.println("Creating Cruises");
         
         // Create some Cruise beans - leave ship reference empty since we don't care
         CruiseLocal cruiseA = cruisehome.create("Cruise A", null); 
         CruiseLocal cruiseB = cruisehome.create("Cruise B", null); 
         
         out.println("name="+cruiseA.getName());
         out.println("name="+cruiseB.getName());
         
         out.println("Creating Reservations");
         
         // Create some Reservation beans - automatic key generation by CMP engine
         // Link 1-3 to Cruise A, 4-6 to Cruise B
         obj = jndiContext.lookup("ReservationHomeLocal");
         ReservationHomeLocal reservationhome = (ReservationHomeLocal)obj;
         ReservationLocal reservations[] = new ReservationLocal[6];
         Calendar date = Calendar.getInstance();
         date.set(2002,10,1);
         
         // Leave the Customers collection null in the create() call, we don't care about it right now

         for (int i = 0; i < 6; i++)
         {
            CruiseLocal cruise = (i < 3) ? cruiseA : cruiseB;
            reservations[i] = reservationhome.create(cruise,new ArrayList());
            reservations[i].setDate(date.getTime());
            reservations[i].setAmountPaid((i + 1) * 1000.0);
            date.add(Calendar.DAY_OF_MONTH, 7);
         }
         
         DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
         
         // Report information on the 6 reservations 
         
         for (int jj=0; jj<6; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename);
         }
         
         out.println("Testing using b_res.addAll(a_res) to combine reservations");
         
         // Show how to combine reservations using Collection methods
         try 
         {
            Collection a_reservations = cruiseA.getReservations();
            Collection b_reservations = cruiseB.getReservations();
            b_reservations.addAll(a_reservations);
         }
         catch (Exception e) 
         {
            e.printStackTrace(out);
         }
         
         // Report information on the 6 reservations 
         
         for (int jj=0; jj<6; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename);
         }
         for (int jj=0; jj<6; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            rr.remove();
         }
         cruiseA.remove();
         cruiseB.remove();
                             
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }
   
   public String test72d() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
         
      try
      {
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("ShipHomeLocal");
         ShipHomeLocal shiphome = (ShipHomeLocal)obj;

         obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruisehome = (CruiseHomeLocal)obj;

         obj = jndiContext.lookup("ReservationHomeLocal");
         ReservationHomeLocal reservationhome = (ReservationHomeLocal)obj;

         obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerhome = (CustomerHomeLocal)obj;

         obj = jndiContext.lookup("CabinHomeLocal");
         CabinHomeLocal cabinhome = (CabinHomeLocal)obj;

         ShipLocal shipA = shiphome.create(new Integer(10771), "Ship A", 30000.0); 
         CruiseLocal cruiseA = cruisehome.create("Cruise A", shipA); 

         out.println("cruise.getName()="+cruiseA.getName());
         out.println("ship.getName()="+shipA.getName());
         out.println("cruise.getShip().getName()="+cruiseA.getShip().getName());

         out.println("Creating Customers 1-6");

         // create two sets of customers, one with customers 1-3 and one with 4-6
         Set lowcustomers = new HashSet();
         Set highcustomers = new HashSet();
         CustomerLocal[] allCustomers = new CustomerLocal[6];
         for (int kk=0; kk<6; kk++) {
            CustomerLocal cust = customerhome.create(new Integer(kk));
            allCustomers[kk] = cust;
            cust.setName(new Name("Customer "+kk,""));
            if (kk<=2) {
               lowcustomers.add(cust);
            } else {
               highcustomers.add(cust);
            }
            out.println(cust.getName().getLastName());
         }
		
         out.println("Creating Reservations 1 and 2, each with 3 customers");

         // Create some Reservation beans - automatic key generation by CMP engine
         // Link 1 to customers 1-3, link 2 to customers 4-6

         ReservationLocal reservations[] = new ReservationLocal[2];
         Calendar date = Calendar.getInstance();
         date.set(2002,10,1);

         reservations[0] = reservationhome.create(cruiseA, lowcustomers);
         reservations[0].setDate(date.getTime());
         reservations[0].setAmountPaid(4000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         reservations[1] = reservationhome.create(cruiseA, highcustomers);
         reservations[1].setDate(date.getTime());
         reservations[1].setAmountPaid(5000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


         // report information on the reservations
         for (int jj=0; jj<2; jj++) {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            String customerinfo = "";
            try {
               Set customerset = rr.getCustomers();
               Iterator iterator = customerset.iterator();
               while(iterator.hasNext()){
                  CustomerLocal cust = (CustomerLocal)iterator.next();
                  customerinfo += cust.getName().getLastName()+" ";
               }
            }
            catch (Exception e) {
               e.printStackTrace();
            }
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename+" with customers "+customerinfo);
         }

         out.println("Performing customers_a.addAll(customers_b) test");

         // Finally we can perform the test shown in Figure 7-19
         try {
            Set customers_a = reservations[0].getCustomers();
            Set customers_b = reservations[1].getCustomers();
            customers_a.addAll(customers_b);
         }
         catch (Exception e) {
            e.printStackTrace(out);
         }

         // report information on the reservations
         for (int jj=0; jj<2; jj++) {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            String customerinfo = "";
            try 
            {
               Set customerset = rr.getCustomers();
               Iterator iterator = customerset.iterator();
               while(iterator.hasNext())
               { 
                  CustomerLocal cust = (CustomerLocal)iterator.next();
                  customerinfo += cust.getName().getLastName()+" ";
               }
            }
            catch (Exception e) 
            {
               e.printStackTrace(out);
            }
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename+" with customers "+customerinfo);

         }
         out.println("Removing created beans");
         shipA.remove();
         cruiseA.remove();
         allCustomers[0].remove();
         allCustomers[1].remove();
         allCustomers[2].remove();
         allCustomers[3].remove();
         allCustomers[4].remove();
         allCustomers[5].remove();
         reservations[0].remove();
         reservations[1].remove();
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }
   
   public String test72e() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
         
      try
      {
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("ShipHomeLocal");
         ShipHomeLocal shiphome = (ShipHomeLocal)obj;

         obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruisehome = (CruiseHomeLocal)obj;

         obj = jndiContext.lookup("ReservationHomeLocal");
         ReservationHomeLocal reservationhome = (ReservationHomeLocal)obj;

         obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerhome = (CustomerHomeLocal)obj;

         obj = jndiContext.lookup("CabinHomeLocal");
         CabinHomeLocal cabinhome = (CabinHomeLocal)obj;

         out.println("Creating a Ship and Cruise");

         ShipLocal shipA = shiphome.create(new Integer(10772), "Ship A", 30000.0); 
         CruiseLocal cruiseA = cruisehome.create("Cruise A", shipA); 

         out.println("cruise.getName()="+cruiseA.getName());
         out.println("ship.getName()="+shipA.getName());
         out.println("cruise.getShip().getName()="+cruiseA.getShip().getName());

         out.println("Creating Customers 1-6");

         // create four sets of customers, 1-3, 2-4, 3-5, 4-6
         Set customers13 = new HashSet();
         Set customers24 = new HashSet();
         Set customers35 = new HashSet();
         Set customers46 = new HashSet();
         CustomerLocal[] allCustomers = new CustomerLocal[6];
         for (int kk=0; kk<6; kk++) {
            CustomerLocal cust = customerhome.create(new Integer(kk));
            allCustomers[kk] = cust;
            cust.setName(new Name("Customer "+kk,""));
            if (kk<=2)          { customers13.add(cust); }
            if (kk>=1 && kk<=3) { customers24.add(cust); }
            if (kk>=2 && kk<=4) { customers35.add(cust); }
            if (kk>=3)          { customers46.add(cust); }
         }
		
         out.println("Creating Reservations 1-4 using three customers each");

         // Create some Reservation beans - automatic key generation by CMP engine
         // Link 1 to customers 1-3, link 2 to customers 2-4, etc..

         ReservationLocal reservations[] = new ReservationLocal[4];
         Calendar date = Calendar.getInstance();
         date.set(2002,10,1);

         reservations[0] = reservationhome.create(cruiseA, customers13);
         reservations[0].setDate(date.getTime());
         reservations[0].setAmountPaid(4000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         reservations[1] = reservationhome.create(cruiseA, customers24);
         reservations[1].setDate(date.getTime());
         reservations[1].setAmountPaid(5000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         reservations[2] = reservationhome.create(cruiseA, customers35);
         reservations[2].setDate(date.getTime());
         reservations[2].setAmountPaid(6000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         reservations[3] = reservationhome.create(cruiseA, customers46);
         reservations[3].setDate(date.getTime());
         reservations[3].setAmountPaid(7000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

         // report information on the reservations
         for (int jj=0; jj<4; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            String customerinfo = "";
            try 
            {
               Set customerset = rr.getCustomers();
               Iterator iterator = customerset.iterator();
               while(iterator.hasNext()){
                  CustomerLocal cust = (CustomerLocal)iterator.next();
                  customerinfo += cust.getName().getLastName()+" ";
               }
            }
            catch (Exception e) 
            {
               e.printStackTrace();
            }
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename+" with customers "+customerinfo);
         }

         out.println("Performing reservationD.setCustomers(customersA) test");

         // Finally we can perform the test shown in Figure 7-20
         try 
         {
            Set customers_a = reservations[0].getCustomers();
            reservations[3].setCustomers(customers_a);
         }
         catch (Exception e) 
         {
            e.printStackTrace();
         }

         // report information on the reservations
         for (int jj=0; jj<4; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            String customerinfo = "";
            try 
            {
               Set customerset = rr.getCustomers();
               Iterator iterator = customerset.iterator();
               while(iterator.hasNext())
               {
                  CustomerLocal cust = (CustomerLocal)iterator.next();
                  customerinfo += cust.getName().getLastName()+" ";
               }
            }
            catch (Exception e) 
            {
               e.printStackTrace();
            }
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename+" with customers "+customerinfo);

         }
         out.println("Removing created beans.");
         shipA.remove();
         cruiseA.remove();
         allCustomers[0].remove();
         allCustomers[1].remove();
         allCustomers[2].remove();
         allCustomers[3].remove();
         allCustomers[4].remove();
         allCustomers[5].remove();
         reservations[0].remove();
         reservations[1].remove();
         reservations[2].remove();
         reservations[3].remove();
      }
      catch (Exception ex)
      {
         ex.printStackTrace(out);
      }
      out.close();
      output = writer.toString();

      return output;
   }
   
   public String test72f() throws RemoteException
   {
      String output = null;
      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
         
      try
      {
         InitialContext jndiContext = getInitialContext();
         Object obj = jndiContext.lookup("ShipHomeLocal");
         ShipHomeLocal shiphome = (ShipHomeLocal)obj;

         obj = jndiContext.lookup("CruiseHomeLocal");
         CruiseHomeLocal cruisehome = (CruiseHomeLocal)obj;

         obj = jndiContext.lookup("ReservationHomeLocal");
         ReservationHomeLocal reservationhome = (ReservationHomeLocal)obj;

         obj = jndiContext.lookup("CustomerHomeLocal");
         CustomerHomeLocal customerhome = (CustomerHomeLocal)obj;

         obj = jndiContext.lookup("CabinHomeLocal");
         CabinHomeLocal cabinhome = (CabinHomeLocal)obj;

         out.println("Creating a Ship and Cruise");

         ShipLocal shipA = shiphome.create(new Integer(10773), "Ship A", 30000.0); 
         CruiseLocal cruiseA = cruisehome.create("Cruise A", shipA); 

         out.println("cruise.getName()="+cruiseA.getName());
         out.println("ship.getName()="+shipA.getName());
         out.println("cruise.getShip().getName()="+cruiseA.getShip().getName());

         out.println("Creating Cabins 1-6");

         // create four sets of cabins, 1-3, 2-4, 3-5, 4-6
         Set cabins13 = new HashSet();
         Set cabins24 = new HashSet();
         Set cabins35 = new HashSet();
         Set cabins46 = new HashSet();
         CabinLocal[] allCabins = new CabinLocal[6];
         for (int kk=0; kk<6; kk++) {
            CabinLocal cabin = cabinhome.create(new Integer(kk));
            allCabins[kk] = cabin;
            cabin.setName("Cabin "+kk);
            if (kk<=2)          { cabins13.add(cabin); }
            if (kk>=1 && kk<=3) { cabins24.add(cabin); }
            if (kk>=2 && kk<=4) { cabins35.add(cabin); }
            if (kk>=3)          { cabins46.add(cabin); }
            out.println(cabin.getName());
         }
		
         out.println("Creating Reservations 1-4 using three cabins each");

         // Create some Reservation beans - automatic key generation by CMP engine
         // Link 1 to cabins 1-3, link 2 to cabins 2-4, etc..

         ReservationLocal reservations[] = new ReservationLocal[4];
         Calendar date = Calendar.getInstance();
         date.set(2002,10,1);

         // leave Customers collection null, we dont care about it for this example

         reservations[0] = reservationhome.create(cruiseA, null);
         reservations[0].setCabins(cabins13);
         reservations[0].setDate(date.getTime());
         reservations[0].setAmountPaid(4000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         reservations[1] = reservationhome.create(cruiseA, null);
         reservations[1].setCabins(cabins24);
         reservations[1].setDate(date.getTime());
         reservations[1].setAmountPaid(5000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         reservations[2] = reservationhome.create(cruiseA, null);
         reservations[2].setCabins(cabins35);
         reservations[2].setDate(date.getTime());
         reservations[2].setAmountPaid(6000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         reservations[3] = reservationhome.create(cruiseA, null);
         reservations[3].setCabins(cabins46);
         reservations[3].setDate(date.getTime());
         reservations[3].setAmountPaid(7000.0);
         date.add(Calendar.DAY_OF_MONTH, 7);

         DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

         // report information on the reservations
         for (int jj=0; jj<4; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            String cabininfo = "";
            try 
            {
               Set cabinset = rr.getCabins();
               Iterator iterator = cabinset.iterator();
               while(iterator.hasNext())
               {
                  CabinLocal cabin = (CabinLocal)iterator.next();
                  cabininfo += cabin.getName()+" ";
               }
            }
            catch (Exception e) 
            {
               e.printStackTrace();
            }
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename+" with cabins "+cabininfo);
         }

         out.println("Performing cabins_a collection iterator.remove() test");

         // Finally we can perform the test shown in Figure 7-22
         try 
         {
            Set cabins_a = reservations[0].getCabins();
            Iterator iterator = cabins_a.iterator();
            while (iterator.hasNext()) 
            {
               CabinLocal cc = (CabinLocal)iterator.next();
               out.println("Removing "+cc.getName()+" from cabins_a");
               iterator.remove();
            }
         }
         catch (Exception e) 
         {
            e.printStackTrace(out);
         }

         // report information on the reservations
         for (int jj=0; jj<4; jj++) 
         {
            ReservationLocal rr = reservations[jj];
            CruiseLocal thiscruise = rr.getCruise();
            String cruisename = (thiscruise!=null ? thiscruise.getName() : "No Cruise!");
            String cabininfo = "";
            try 
            {
               Set cabinset = rr.getCabins();
               Iterator iterator = cabinset.iterator();
               while(iterator.hasNext())
               {
                  CabinLocal cabin = (CabinLocal)iterator.next();
                  cabininfo += cabin.getName()+" ";
               }
            }
            catch (Exception e) {
               e.printStackTrace();
            }
            out.println("Reservation date="+df.format(rr.getDate())
                        +" is for "+cruisename+" with cabins "+cabininfo);
            
         }
         out.println("Removing created beans");
         shipA.remove();
         cruiseA.remove();
         allCabins[0].remove();
         allCabins[1].remove();
         allCabins[2].remove();
         allCabins[3].remove();
         allCabins[4].remove();
         allCabins[5].remove();
         reservations[0].remove();
         reservations[1].remove();
         reservations[2].remove();
         reservations[3].remove();
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
