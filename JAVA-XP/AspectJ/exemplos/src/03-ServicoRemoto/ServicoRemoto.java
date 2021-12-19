import java.rmi.RemoteException;

public class ServicoRemoto {
    public static int getResposta() throws RemoteException {
        if (Math.random() > 0.25) {
            throw new RemoteException("Uma falha simulada ocorreu");
        }
        System.out.println("ServicoRemoto.getResposta()");
        return 5;
    }
}
