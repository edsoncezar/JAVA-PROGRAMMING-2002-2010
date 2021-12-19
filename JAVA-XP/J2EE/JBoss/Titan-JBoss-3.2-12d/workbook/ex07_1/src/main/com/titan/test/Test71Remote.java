package com.titan.test;

import java.rmi.RemoteException;

public interface Test71Remote extends javax.ejb.EJBObject 
{
   public String test71a() throws RemoteException;
   public String test71b() throws RemoteException;
   public String test71c() throws RemoteException;
}
