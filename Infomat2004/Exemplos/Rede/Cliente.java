import java.net.*;
import java.io.*;
public class Cliente {
    
    /** Creates a new instance of Cliente */
    public Cliente() {
    }
    
    public static void main(String args[] ) {
        Socket ClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        try {
        /* cria o socket do cliente para conexao com o servidor
           que esta na maquina work1 operando na porta 4444 */
            
            ClientSocket = new Socket("127.0.0.1", 4444);
            
            /* associa um buffer de entrada e outro de saida ao socket */
            
            out = new PrintWriter(ClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
            
            /* associa uma string aa entrada padrao */
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            /* cria duas strings, uma para receber e outra para enviar pelo socket */
            
            String fromServer;
            String fromUser;
            
            while (true) {
                fromServer = in.readLine();   /* receive do socket */
                System.out.println("Server: " + fromServer); /* mostra na saida padrao */
                System.out.println("Enviar (digite Bye para terminar): ");
                fromUser = stdIn.readLine();  /* le da entrada padrao */
                out.println(fromUser);      /* send pelo socket */
                if (fromUser.equals("Bye"))   /* termina se usuario digitou Bye*/
                    break;
            }
            
            /* fecha os buffers e o socket */
            out.close();
            in.close();
            stdIn.close();
            ClientSocket.close();
        }
        catch (UnknownHostException e) {
            System.err.println("Host desconhecido: 127.0.0.1.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Conexao com 127.0.0.1 falhou. ");
            System.exit(1);
        }
    }
}