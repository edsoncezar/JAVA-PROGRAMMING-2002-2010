package com.titan.processpayment;

public interface ProcessPaymentHomeRemote extends javax.ejb.EJBHome
{   
   public ProcessPaymentRemote create ()
   throws java.rmi.RemoteException, javax.ejb.CreateException;   
}
