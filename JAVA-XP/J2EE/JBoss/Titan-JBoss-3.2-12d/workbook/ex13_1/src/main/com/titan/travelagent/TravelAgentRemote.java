package com.titan.travelagent;

import java.rmi.RemoteException;

import javax.ejb.FinderException;
import javax.ejb.CreateException;

import com.titan.processpayment.CreditCardDO;

import java.util.Collection;

public interface TravelAgentRemote extends javax.ejb.EJBObject
{
   public void setCruiseID (Integer cruise)
      throws RemoteException, FinderException;
   
   public void setCabinID (Integer cabin)
      throws RemoteException, FinderException;
   
   public TicketDO bookPassage (CreditCardDO card, double price)
      throws RemoteException, IncompleteConversationalState;
   
   public String [] listAvailableCabins (int bedCount)
      throws RemoteException, IncompleteConversationalState;
   
   // Mechanism for building local beans for example programs.
   //
   public Collection buildSampleData () throws RemoteException;
}
