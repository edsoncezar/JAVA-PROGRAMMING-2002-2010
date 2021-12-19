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
import org.apache.log4j.Category;
import net.sourceforge.junitejb.EJBTestCase;

public class ReadAheadTest extends EJBTestCase {
   public static Test suite() {
      TestSuite testSuite = new TestSuite("ReadAheadTest");
      testSuite.addTestSuite(ReadAheadTest.class);
      return testSuite;
   }   

   public ReadAheadTest(String name) {
      super(name);
   }

   private Category log = Category.getInstance(getClass());
   private ReadAheadHome readAheadHome;

   /**
    * Looks up all of the home interfaces and creates the initial data. 
    * Looking up objects in JNDI is expensive, so it should be done once 
    * and cached.
    * @throws Exception if a problem occures while finding the home interfaces,
    * or if an problem occures while createing the initial data
    */
   public void setUp() throws Exception {
      InitialContext jndi = new InitialContext();

      readAheadHome = 
            (ReadAheadHome) jndi.lookup("crimeportal/ReadAhead"); 
   }

   public void testReadAhead_none() throws Exception {
      ReadAhead readAhead = null;
      try {
         readAhead = readAheadHome.create();
         log.info(
               "\n\n########################################################" +
               "\n### read-ahead none" +
               "\n###");

         readAhead.createGangsterHtmlTable_none();

         log.info(
               "\n###" +
               "\n########################################################" +
               "\n\n" +
               "\n########################################################" +
               "\n### read-ahead on-load" +
               "\n###");
               
         readAhead.createGangsterHtmlTable_onload();

         log.info(
               "\n###" +
               "\n########################################################" +
               "\n\n" +
               "\n########################################################" +
               "\n### read-ahead on-find" +
               "\n###");

         readAhead.createGangsterHtmlTable_onfind();

         log.info(
               "\n###" +
               "\n########################################################" +
               "\n\n" +
               "\n########################################################" +
               "\n### lazy-load relationship" +
               "\n###");

         readAhead.createGangsterHangoutHtmlTable();

         log.info(
               "\n###" +
               "\n########################################################" +
               "\n\n" +
               "\n########################################################" +
               "\n### read-ahead no transaction" +
               "\n###");

         readAhead.createGangsterHtmlTable_no_tx();

         log.info(
               "\n###" +
               "\n########################################################" +
               "\n\n" +
               "\n########################################################" +
               "\n### read-ahead with user transaction" +
               "\n###");

         readAhead.createGangsterHtmlTable_with_tx();

         log.info(
               "\n###" +
               "\n########################################################");
      } finally {
         if(readAhead != null) {
            readAhead.remove();
         }
      }
   }
}
