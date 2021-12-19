package org.jboss.docs.jaas.howto;

import javax.ejb.*;
import java.rmi.*;

/** The home interface for the example stateless session beans
*/
public interface SessionHome extends EJBHome
{
    public Session create() throws RemoteException, CreateException;
}
