/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.util.*;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Cart extends EJBObject {
 
  public void addBook(String title) throws RemoteException;
  public void removeBook(String title) throws BookException, RemoteException;
  public Vector getContents() throws RemoteException;
}
