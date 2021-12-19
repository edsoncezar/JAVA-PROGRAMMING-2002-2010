import java.net.*;
import java.io.*;

public class Servidor {
    
    /** Creates a new instance of Servidor */
    public Servidor() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        {
            ServerSocket ServidorSocket = null;
            try {
                /* cria socket na porta 4444 */
                ServidorSocket = new ServerSocket(4444);
            } catch (IOException e) {
                System.err.println("Could not listen on port: 4444.");
                System.exit(1);
            }
            
            Socket ClientSocket = null;
            try {
                System.out.println("Aguardando");
                /* aguarda solicitacao do cliente e aceita conexao */
                ClientSocket = ServidorSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            
            try{
                /* cria dois buffers, uma para enviar e outro para receber da conexao com o cliente*/
                PrintWriter out = new PrintWriter(ClientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
                String inputLine, outputLine;
                
                out.println("Pronto!"); /* send para o cliente */
                
                while (true) {
                    inputLine = in.readLine();  /* receive do cliente */
                    System.out.println("Recebi: " + inputLine); /* mostra na saida padrao*/
                    out.println("Proximo...");  /* send para o cliente */
                    if (inputLine.equals("Bye")) /* termina se Bye */
                        break;
                }
                /* fecha socket */
                out.close();
                in.close();
                ClientSocket.close();
                ServidorSocket.close();
            }
            catch (Exception e){
                System.out.println("Erro: "+e);
            }
        }
    }
}
