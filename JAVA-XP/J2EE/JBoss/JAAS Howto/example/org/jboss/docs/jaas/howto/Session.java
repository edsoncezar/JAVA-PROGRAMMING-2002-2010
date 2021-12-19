package org.jboss.docs.jaas.howto;

import javax.ejb.*;
import java.rmi.*;

/** A simple stateless session bean interface used by the example beans.

@author Scott.Stark@jboss.org
@version $Revision: 1.1 $ 
*/
public interface Session extends EJBObject
{
    public String echo(String arg) throws RemoteException;
    public void noop() throws RemoteException;
    public void restricted() throws RemoteException;
}
