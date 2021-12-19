/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.math.*;

public interface Converter extends EJBObject {
 
  public BigDecimal dollarToYen(BigDecimal dollars) throws RemoteException;
  public BigDecimal yenToEuro(BigDecimal yen) throws RemoteException;
}
