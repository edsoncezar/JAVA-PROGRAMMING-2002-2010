package org.jboss.docs.jaas.howto;

import java.security.Principal;
import javax.ejb.*;

/** An implmentation of the Session interface that should not
be accessible by external users.

@ejbHome: SessionHome
@ejbRemote: Session

@author Scott.Stark@jboss.org
@version $Revision: 1.1 $ 
*/
public class PrivateSessionBean implements SessionBean
{
    private SessionContext sessionContext;

    public void ejbCreate() throws CreateException
    {
        System.out.println("PrivateSessionBean.ejbCreate() called");
    }

    public void ejbActivate() 
    {
        System.out.println("PrivateSessionBean.ejbActivate() called");
    }

    public void ejbPassivate() 
    {
        System.out.println("PrivateSessionBean.ejbPassivate() called");
    }

    public void ejbRemove() 
    {
        System.out.println("PrivateSessionBean.ejbRemove() called");
    }

    public void setSessionContext(SessionContext context) 
    {
        sessionContext = context;
    }

    public String echo(String arg)
    {
        System.out.println("PrivateSessionBean.echo, arg="+arg);
        Principal p = sessionContext.getCallerPrincipal();
        System.out.println("PrivateSessionBean.echo, callerPrincipal="+p);
        System.out.println("PrivateSessionBean.echo, isCallerInRole('InternalUser')="+sessionContext.isCallerInRole("InternalUser"));
        return arg;
    }
    public void noop() 
    {
        System.out.println("PrivateSessionBean.noop");
        Principal p = sessionContext.getCallerPrincipal();
        System.out.println("PrivateSessionBean.noop, callerPrincipal="+p);
    }
    public void restricted() 
    {
        System.out.println("PrivateSessionBean.restricted");
        Principal p = sessionContext.getCallerPrincipal();
        System.out.println("PrivateSessionBean.restricted, callerPrincipal="+p);
    }
}
