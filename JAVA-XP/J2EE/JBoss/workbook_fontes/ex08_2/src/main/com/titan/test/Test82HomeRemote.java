package com.titan.test;

import java.rmi.RemoteException;
import javax.ejb.CreateException;

public interface Test82HomeRemote extends javax.ejb.EJBHome 
{
    public Test82Remote create()
        throws RemoteException, CreateException;

}
