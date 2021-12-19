package paulojeronimo.sac.ejb;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;

import paulojeronimo.sac.interfaces.*;

import paulojeronimo.sac.common.SacServices;
import paulojeronimo.sac.common.SacRemoteException;

/**
 * @ejb.bean 
 *      name="SacServicesSession"
 *      display-name="Servico de Acesso a Centrais"
 *      type="Stateless"
 *      jndi-name="sac.SacServicesSession"
 *      transaction-type="Container"
 * @ejb.util
 *      generate="physical"
 **/
public class SacServicesSessionBean implements SacServices, SessionBean {
    private SessionContext sessionContext;
  
    /**
     * Obtem todas as centrais.
     * 
     * @ejb.interface-method view-type="remote"
     */ 
    public Collection getCentrais() throws SacRemoteException {
        try {
            Iterator i = CentralUtil.getLocalHome().findAll().iterator();
            Vector result = new Vector();
            while (i.hasNext()) {
                CentralLocal centralLocal = (CentralLocal) i.next();
                result.add(centralLocal.getData());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SacRemoteException(e);
        }
    }

    /**
     * Obtem todas as centrais que um usuário tem acesso.
     *
     * @ejb.interface-method view-type="remote"
     */ 
    public Collection getCentrais(String usuario) throws SacRemoteException {
        try {
            UsuarioLocal usuarioLocal = UsuarioUtil.getLocalHome().findByPrimaryKey(usuario);
            Iterator i = usuarioLocal.getUsuarioCentrals().iterator();
            Vector result = new Vector();
            while (i.hasNext()) {
                UsuarioCentralLocal ucl = (UsuarioCentralLocal) i.next();
                result.add(ucl.getCentral().getData());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SacRemoteException(e);
        }
    }

    /**
     * Obtem as classes de serviço.
     * 
     * @ejb.interface-method view-type="remote"
     */ 
    public Collection getClassesServicos() throws SacRemoteException {
        try {
            Iterator i = ClasseServicoUtil.getLocalHome().findAll().iterator();
            Vector result = new Vector();
            while (i.hasNext()) {
                ClasseServicoLocal classeServicoLocal = (ClasseServicoLocal) i.next();
                result.add(classeServicoLocal.getData());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SacRemoteException(e);
        }
    }

    /**
     * Obtem os serviços de uma classe.
     *
     * @ejb.interface-method view-type="remote"
     */ 
    public Collection getServicos(int idClasseServico) throws SacRemoteException {
        try {
            Iterator i = ServicoUtil.getLocalHome().findByIdClasseServico(
                idClasseServico).iterator();
            Vector result = new Vector();
            while (i.hasNext()) {
                ServicoLocal servicoLocal = (ServicoLocal) i.next();
                result.add(servicoLocal.getData());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SacRemoteException(e);
        }
    }

    /**
     * Obtem os comandos de uma versão/serviço.
     *
     * @ejb.interface-method view-type="remote"
     */ 
    public Collection getComandos(int idVersao, int idServico) throws SacRemoteException {
        try {
            Iterator i = ComandoUtil.getLocalHome().findByIdVersaoAndIdServico(
                idVersao, idServico).iterator();
            Vector result = new Vector();
            while (i.hasNext()) {
                ComandoLocal comandoLocal = (ComandoLocal) i.next();
                result.add(comandoLocal.getData());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SacRemoteException(e);
        }
    }

    /**
    * Create the Session Bean
    *
    * @throws CreateException 
    *
    * @ejb:create-method view-type="remote"
    **/
    public void ejbCreate() throws CreateException {
        System.out.println( "SacServicesSessionBean.ejbCreate()" );
    }

    /**
    * Describes the instance and its content for debugging purpose
    *
    * @return Debugging information about the instance and its content
    **/
    public String toString() {
        return "SacServicesSessionBean [ " + " ]";
    }

    // -------------------------------------------------------------------------
    // Framework Callbacks
    // -------------------------------------------------------------------------  
    public void setSessionContext( SessionContext aContext ) throws EJBException {
      this.sessionContext = aContext;
    }
    public void ejbActivate() throws EJBException { }
    public void ejbPassivate() throws EJBException { }
    public void ejbRemove() throws EJBException { }
}
