package com.titan.test;

import java.rmi.RemoteException;
import javax.ejb.CreateException;

public interface Test81HomeRemote extends javax.ejb.EJBHome 
{
    public Test81Remote create()
        throws RemoteException, CreateException;

}
