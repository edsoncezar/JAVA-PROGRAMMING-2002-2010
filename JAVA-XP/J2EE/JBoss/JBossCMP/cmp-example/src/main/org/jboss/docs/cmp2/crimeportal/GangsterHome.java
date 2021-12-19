package org.jboss.docs.cmp2.crimeportal;

import java.util.Collection;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface GangsterHome extends EJBLocalHome
{
   Gangster create(Integer id, String name, String nickName)
         throws CreateException;
   Gangster create(
         Integer id, 
         String name, 
         String nickName, 
         int badness, 
         Organization org) throws CreateException;

   Gangster findByPrimaryKey(Integer id) throws FinderException;
   Collection findByPrimaryKeys(Collection c) throws FinderException;

   Collection findAll() throws FinderException;
   Collection findAll_none() throws FinderException;
   Collection findAll_onfind() throws FinderException;
   Collection findAll_onload() throws FinderException;

   Collection findBadDudes_ejbql(int badness) throws FinderException;
   Collection findBadDudes_jbossql(int badness) throws FinderException;
   Collection findBadDudes_declaredsql(int badness) throws FinderException;

   Set selectBoss_ejbql(String name) throws FinderException;
   Set selectBoss_declaredsql(String name) throws FinderException;

   Set selectInStates(Set states) throws FinderException;
   
   /** Finds just four gangsters. Used in read ahead tests. */
   Collection findFour() throws FinderException;
}
