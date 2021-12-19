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

public class CrimePortalTest extends EJBTestCase
      implements CrimePortalTestConstants {

   public static Test suite() {
      TestSuite testSuite = new TestSuite("CrimePortalTest");
      testSuite.addTestSuite(CrimePortalTest.class);
      return testSuite;
   }   

   public CrimePortalTest(String name) {
      super(name);
   }

   private OrganizationHome organizationHome;
   private GangsterHome gangsterHome;
   private JobHome jobHome;
   private LocationHome locationHome;

   /**
    * Looks up all of the home interfaces and creates the initial data. 
    * Looking up objects in JNDI is expensive, so it should be done once 
    * and cached.
    * @throws Exception if a problem occures while finding the home interfaces,
    * or if an problem occures while createing the initial data
    */
   public void setUp() throws Exception {
      InitialContext jndi = new InitialContext();

      organizationHome = 
            (OrganizationHome) jndi.lookup("crimeportal/Organization"); 

      gangsterHome = (GangsterHome) jndi.lookup("crimeportal/Gangster"); 

      jobHome = (JobHome) jndi.lookup("crimeportal/Job"); 

      locationHome = (LocationHome) jndi.lookup("crimeportal/Location");
   }

   /** Test Organization-Gangster relationship */
   public void testOrganization() throws Exception {
      Organization yakuza = organizationHome.findByPrimaryKey("Yakuza");
      Collection gangsters = yakuza.getMemberGangsters();
      assertEquals(3, gangsters.size());
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(YOJIMBO)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(TAKESHI)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(YURIKO)));
   }

   /** Test find bad dudes query */
   public void testFindBadDudes_ejbql() throws Exception {
      Collection gangsters = gangsterHome.findBadDudes_ejbql(5);
      assertEquals(5, gangsters.size());

      assertTrue(gangsters.contains(
            gangsterHome.findByPrimaryKey(TAKESHI)));
      assertTrue(gangsters.contains(
            gangsterHome.findByPrimaryKey(CHOW)));
      assertTrue(gangsters.contains(
            gangsterHome.findByPrimaryKey(SHOGI)));
      assertTrue(gangsters.contains(
            gangsterHome.findByPrimaryKey(YOJIMBO)));
      assertTrue(gangsters.contains(
            gangsterHome.findByPrimaryKey(CORLEONE)));
   }

   /** Test find bad dudes query */
   public void testFindBadDudes_jbossql() throws Exception {
      Collection gangsters = gangsterHome.findBadDudes_jbossql(5);
      assertEquals(5, gangsters.size());

      // gangsters should be in the following order
      Iterator iter = gangsters.iterator();
      assertEquals(gangsterHome.findByPrimaryKey(TAKESHI), iter.next());
      assertEquals(gangsterHome.findByPrimaryKey(CHOW), iter.next());
      assertEquals(gangsterHome.findByPrimaryKey(SHOGI), iter.next());
      assertEquals(gangsterHome.findByPrimaryKey(YOJIMBO), iter.next());
      assertEquals(gangsterHome.findByPrimaryKey(CORLEONE), iter.next());
   }

   /** Test find bad dudes query */
   public void testFindBadDudes_declaredsql() throws Exception {
      Collection gangsters = gangsterHome.findBadDudes_declaredsql(5);
      assertEquals(5, gangsters.size());

      // gangsters should be in the following order
      Iterator iter = gangsters.iterator();
      assertEquals(gangsterHome.findByPrimaryKey(TAKESHI), iter.next());
      assertEquals(gangsterHome.findByPrimaryKey(CHOW), iter.next());
      assertEquals(gangsterHome.findByPrimaryKey(SHOGI), iter.next());
      assertEquals(gangsterHome.findByPrimaryKey(YOJIMBO), iter.next());
      assertEquals(gangsterHome.findByPrimaryKey(CORLEONE), iter.next());
   }

   /** Test select boss query */
   public void testSelectBoss_ejbql() throws Exception {
      Set gangsters = gangsterHome.selectBoss_ejbql(" Yojimbo ");
      assertEquals(1, gangsters.size());
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(TAKESHI)));

      gangsters = gangsterHome.selectBoss_ejbql(" Takeshi ");
      assertEquals(1, gangsters.size());
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(TAKESHI)));

      gangsters = gangsterHome.selectBoss_ejbql("non-existant");
      assertEquals(0, gangsters.size());
   }

   /** Test select boss query */
   public void testSelectBoss_declaredsql() throws Exception {
      Set gangsters = gangsterHome.selectBoss_declaredsql(" YoJIMbO ");
      assertEquals(1, gangsters.size());
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(TAKESHI)));

      gangsters = gangsterHome.selectBoss_declaredsql(" TaKeShI ");
      assertEquals(1, gangsters.size());
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(TAKESHI)));

      gangsters = gangsterHome.selectBoss_declaredsql("non-existant");
      assertEquals(0, gangsters.size());
   }

   /** Test select gangsters in states query */
   public void testSelectInStates() throws Exception {
      Set states = new HashSet();
      states.add("CA");
      states.add("NV");
      states.add("OR");
      states.add("WA");

      Collection gangsters = gangsterHome.selectInStates(states);
      assertEquals(6, gangsters.size());
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(TAKESHI)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(YURIKO)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(CHOW)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(SHOGI)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(YOJIMBO)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(CORLEONE)));
   }

   /** Test find by primary keys query */
   public void testFindByPrimayKeys() throws Exception {
      Set primaryKeys = new HashSet();
      primaryKeys.add(CHOW);
      primaryKeys.add(TONI);
      primaryKeys.add(TAKESHI);
      primaryKeys.add(YOJIMBO);

      Collection gangsters = gangsterHome.findByPrimaryKeys(primaryKeys);
      assertEquals(4, gangsters.size());
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(TAKESHI)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(CHOW)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(TONI)));
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(YOJIMBO)));
   }


   /** Test select operating zip codes query */
   public void testSelectOperatingZipCodes_declaredsql() throws Exception {
      Collection zipCodes = 
            organizationHome.selectOperatingZipCodes_declaredsql(" YaKuZa");
      assertEquals(2, zipCodes.size());

      // zip codes should be in the following order
      Iterator iter = zipCodes.iterator();
      assertEquals(new Integer(94108), iter.next());
      assertEquals(new Integer(94133), iter.next());
   }

   /** Test select gangsters in states query */
   public void testFindByPrimaryKeys() throws Exception {
      Set states = new HashSet();
      states.add("CA");
      states.add("NV");
      states.add("OR");
      states.add("WA");

      Set gangsters = gangsterHome.selectInStates(states);
      assertEquals(6, gangsters.size());
      assertTrue(gangsters.contains(gangsterHome.findByPrimaryKey(CORLEONE)));
   }

   /** Test loading of contact info */
   public void testContactInfo() throws Exception {
      Gangster yojimbo = gangsterHome.findByPrimaryKey(YOJIMBO);
      ContactInfo contactInfo = yojimbo.getContactInfo();
      assertNotNull(contactInfo);
      assertEquals("yojimbo439@yakuza.jp", contactInfo.getEmail());
      assertEquals(new PhoneNumber(123, 456, 7890), contactInfo.getCell());
      assertEquals(new PhoneNumber(111, 222, 3333), contactInfo.getPager());
   }
}
