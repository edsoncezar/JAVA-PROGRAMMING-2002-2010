package com.titan.test;

import java.rmi.RemoteException;
import javax.ejb.CreateException;

public interface Test72HomeRemote extends javax.ejb.EJBHome 
{
    public Test72Remote create()
        throws RemoteException, CreateException;

}
