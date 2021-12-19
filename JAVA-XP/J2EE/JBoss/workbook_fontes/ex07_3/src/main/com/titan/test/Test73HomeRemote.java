package com.titan.test;

import java.rmi.RemoteException;
import javax.ejb.CreateException;

public interface Test73HomeRemote extends javax.ejb.EJBHome 
{
    public Test73Remote create()
        throws RemoteException, CreateException;

}
