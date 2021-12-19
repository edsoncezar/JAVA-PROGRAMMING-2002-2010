package paulojeronimo.sac.common;

import java.util.Collection;

public interface SacServices {
    Collection getCentrais() throws SacRemoteException;
    Collection getCentrais(String usuario) throws SacRemoteException;
    Collection getClassesServicos() throws SacRemoteException;
    Collection getServicos(int idClasseServico) throws SacRemoteException;
    Collection getComandos(int idVersao, int idServico) throws SacRemoteException;
}
