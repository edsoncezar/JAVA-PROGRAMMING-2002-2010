package org.jboss.docs.cmp2.crimeportal;

import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface LocationHome extends EJBLocalHome
{
   Location create() throws CreateException;

   Location create(String description, String streep, String city, 
         String state, int zipCode) throws CreateException;

   Location findByPrimaryKey(Integer pk) throws FinderException;

   Collection findAll() throws FinderException;
}
