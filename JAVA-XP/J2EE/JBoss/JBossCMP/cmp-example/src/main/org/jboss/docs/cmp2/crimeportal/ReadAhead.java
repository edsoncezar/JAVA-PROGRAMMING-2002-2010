package org.jboss.docs.cmp2.crimeportal;

import javax.ejb.EJBLocalObject;
import javax.ejb.FinderException;

public interface ReadAhead extends EJBLocalObject
{
   String createGangsterHtmlTable_none() throws FinderException;
   String createGangsterHtmlTable_onload() throws FinderException;
   String createGangsterHtmlTable_onfind() throws FinderException;
   String createGangsterHangoutHtmlTable() throws FinderException;
   String createGangsterHtmlTable_no_tx() throws FinderException;
   String createGangsterHtmlTable_with_tx() throws FinderException;
}
