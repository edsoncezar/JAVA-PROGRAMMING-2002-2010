package org.jboss.docs.jaas.howto;

import java.security.Principal;
import javax.ejb.*;
import javax.naming.InitialContext;

import org.jboss.docs.jaas.howto.Session;
import org.jboss.docs.jaas.howto.SessionHome;

/** An implmentation of the Session interface that delegates its
echo method call to the PrivateSession bean to test run-as.

@ejbHome: SessionHome
@ejbRemote: Session

@author Scott.Stark@jboss.org
@version $Revision: 1.1 $ 
*/
public class PublicSessionBean implements SessionBean
{
    private SessionContext sessionContext;

    public void ejbCreate() throws CreateException
    {
        System.out.println("PublicSessionBean.ejbCreate() called");
    }

    public void ejbActivate()
    {
        System.out.println("PublicSessionBean.ejbActivate() called");
    }

    public void ejbPassivate()
    {
        System.out.println("PublicSessionBean.ejbPassivate() called");
    }

    public void ejbRemove()
    {
        System.out.println("PublicSessionBean.ejbRemove() called");
    }

    public void setSessionContext(SessionContext context)
    {
        sessionContext = context;
    }

    public String echo(String arg)
    {
        System.out.println("PublicSessionBean.echo, arg="+arg);
        Principal p = sessionContext.getCallerPrincipal();
        System.out.println("PublicSessionBean.echo, callerPrincipal="+p);
        System.out.println("PublicSessionBean.echo, isCallerInRole('EchoUser')="+sessionContext.isCallerInRole("EchoUser"));
        try
        {
            InitialContext ctx = new InitialContext();
			SessionHome home = (SessionHome) ctx.lookup("java:comp/env/ejb/PrivateSession");
            Session bean = home.create();
            System.out.println("PublicSessionBean.echo, created PrivateSession");
            arg = bean.echo(arg);
        }
        catch(Exception e)
        {
        }
        return arg;
    }
    public void noop()
    {
        System.out.println("PublicSessionBean.noop");
        Principal p = sessionContext.getCallerPrincipal();
        System.out.println("PublicSessionBean.noop, callerPrincipal="+p);
    }
    public void restricted() 
    {
        System.out.println("PublicSessionBean.restricted");
        Principal p = sessionContext.getCallerPrincipal();
        System.out.println("PublicSessionBean.restricted, callerPrincipal="+p);
    }
}
