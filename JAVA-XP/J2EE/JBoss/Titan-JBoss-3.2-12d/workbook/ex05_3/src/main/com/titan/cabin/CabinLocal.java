package com.titan.cabin;

import javax.ejb.EJBException;

public interface CabinLocal extends javax.ejb.EJBLocalObject 
{
   
   public String getName() throws EJBException;
   public void setName(String str) throws EJBException;
   public int getDeckLevel() throws EJBException;
   public void setDeckLevel(int level) throws EJBException;
   public int getShipId() throws EJBException;
   public void setShipId(int sp) throws EJBException;
   public int getBedCount() throws EJBException;
   public void setBedCount(int bc) throws EJBException; 

}
