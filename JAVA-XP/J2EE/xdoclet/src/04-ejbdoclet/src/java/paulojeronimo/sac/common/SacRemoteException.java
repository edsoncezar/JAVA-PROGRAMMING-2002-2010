package paulojeronimo.sac.common;

import java.rmi.RemoteException;

public class SacRemoteException extends RemoteException {
    private Throwable rootCause;

    public SacRemoteException() {}

    public SacRemoteException(String message) {
        super(message);
    }

    public SacRemoteException(Throwable rootCause) {
        this.rootCause = rootCause;
    }

    public Throwable getRootCause() {
        return rootCause;
    }
}
