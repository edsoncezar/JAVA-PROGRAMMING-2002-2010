package com.titan.test;

import java.rmi.RemoteException;

public interface Test81Remote extends javax.ejb.EJBObject 
{
   public String initialize() throws RemoteException;
   public String test81a() throws RemoteException;
   public String test81b() throws RemoteException;
   public String test81c() throws RemoteException;
}
