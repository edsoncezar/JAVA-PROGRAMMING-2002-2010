package org.jboss.docs.cmp2.crimeportal;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;

public interface ReadAheadHome extends EJBLocalHome
{
   ReadAhead create() throws CreateException;
}
