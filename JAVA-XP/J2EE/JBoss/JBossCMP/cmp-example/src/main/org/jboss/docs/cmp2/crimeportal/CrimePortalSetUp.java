package org.jboss.docs.cmp2.crimeportal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import javax.naming.InitialContext;
import javax.ejb.EJBLocalObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sourceforge.junitejb.EJBTestCase;

public class CrimePortalSetUp extends EJBTestCase 
      implements CrimePortalTestConstants {

   public static Test suite() {
      TestSuite testSuite = new TestSuite("CrimePortalSetUp");
      testSuite.addTestSuite(CrimePortalSetUp.class);
      return testSuite;
   }   

   public CrimePortalSetUp(String name) {
      super(name);
   }


   /**
    * Looks up all of the home interfaces and creates the initial data. 
    * @throws Exception if a problem occures while finding the home interfaces,
    * or if an problem occures while createing the initial data
    */
   public void testSetUp() throws Exception {
      InitialContext jndi = new InitialContext();

      OrganizationHome organizationHome =
            (OrganizationHome) jndi.lookup("crimeportal/Organization"); 

      GangsterHome gangsterHome = 
            (GangsterHome) jndi.lookup("crimeportal/Gangster"); 

      JobHome jobHome = (JobHome) jndi.lookup("crimeportal/Job"); 

      LocationHome locationHome = 
            (LocationHome) jndi.lookup("crimeportal/Location");

      // Create some organizations
      Organization yakuza = 
            organizationHome.create("Yakuza", "Japanese Gangsters");
      Organization mafia = 
            organizationHome.create("Mafia", "Italian Bad Guys");
      Organization triads = 
            organizationHome.create("Triads", "Kung Fu Movie Extras");

      // Create some gangsters
      Gangster yojimbo = 
            gangsterHome.create(YOJIMBO, "Yojimbo", "Bodyguard", 7, yakuza);
      ContactInfo contactInfo = new ContactInfo();
      contactInfo.setEmail("yojimbo439@yakuza.jp");
      contactInfo.setCell(new PhoneNumber(123, 456, 7890));
      contactInfo.setPager(new PhoneNumber(111, 222, 3333));
      yojimbo.setContactInfo(contactInfo);
      yojimbo.setHangout(locationHome.create("Red Dragon Basement",
               "1452 Stockton Street", "San Fran", "CA", 94108));

      Gangster takeshi = 
            gangsterHome.create(TAKESHI, "Takeshi", "Master", 10, yakuza);
      takeshi.setHangout(locationHome.create("Flaming Fist Dojo",
               "598 Jackson Street", "San Fran", "CA", 94133));
      Gangster yuriko = 
            gangsterHome.create(YURIKO, "Yuriko", "Four finger", 4, yakuza);
      yuriko.setHangout(locationHome.create("Sister's House",
               "1411 Powell Street", "San Fran", "CA", 94133));

      Gangster chow = 
            gangsterHome.create(CHOW, "Chow", "Killer", 9, triads);
      chow.setHangout(locationHome.create("Golden Gate Fortune Cookie Factory",
               "56 Ross Alley", "San Fran", "CA", 94133));
      Gangster shogi = 
            gangsterHome.create(SHOGI, "Shogi", "Lightning", 8, triads);
      shogi.setHangout(locationHome.create("The Wok Shop",
               "718 Grant Avenue", "San Fran", "CA", 94133));
      
      Gangster valentino = 
            gangsterHome.create(VALENTINO, "Valentino", "Pizza-Face", 4, mafia);
      valentino.setHangout(locationHome.create("Luca's",
            "299 Madison Avenue", "New York", "NY", 10017));
      Gangster toni = 
            gangsterHome.create(TONI, "Toni", "Toohless", 2, mafia);
      toni.setHangout(locationHome.create("Hotel Workers Union Shop",
               "225 S. Canal Street", "Chicago", "IL", 60661));
      Gangster corleone = 
            gangsterHome.create(CORLEONE, "Corleone", "Godfather", 6, mafia);
      corleone.setHangout(locationHome.create("Black Diamond Casino",
               "9555 Las Vegas Blvd South", "Las Vegas", "NV", 89109));

      // Assign the bosses
      yakuza.setTheBoss(takeshi);
      triads.setTheBoss(chow);
      mafia.setTheBoss(corleone);

      // Create some jobs
      Job jewler = jobHome.create("10th Street Jeweler Heist");
      jewler.setScore(5000);
      jewler.setSetupCost(50);
      
      Job train = jobHome.create("The Greate Train Robbery");
      train.setScore(2000000);
      train.setSetupCost(500000);

      Job liquorStore = jobHome.create("Cheap Liquor Snatch and Grab");
      liquorStore.setScore(50);
      liquorStore.setSetupCost(0);

      // assign some gangsters to the jobs
      jewler.getGangsters().add(valentino);
      jewler.getGangsters().add(corleone);
      
      train.getGangsters().add(yojimbo);
      train.getGangsters().add(chow);

      liquorStore.getGangsters().add(chow);
   }
}
