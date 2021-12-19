import java.rmi.RemoteException;

public aspect GerenciadorDeFalhasAspect {
    final int MAX_TENTATIVAS = 3;

    Object around() throws RemoteException : 
        call(* ServicoRemoto.get*(..) throws RemoteException) {
        int retry = 0;
        while(true) {
            try {
                return proceed();
            } catch (RemoteException ex){
                System.out.println("Excecao: " + ex);
                if (++retry > MAX_TENTATIVAS) {
                    throw ex;
                }
                System.out.println("\tTentando novamente(" + retry + ")");
            }
        }
    }
}
