package com.titan.test;

import java.rmi.RemoteException;
import javax.ejb.CreateException;

public interface Test71HomeRemote extends javax.ejb.EJBHome {

    public Test71Remote create()
        throws RemoteException, CreateException;

}
