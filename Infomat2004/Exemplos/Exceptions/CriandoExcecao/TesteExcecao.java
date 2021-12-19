public class TesteExcecao {
    
    /** Creates a new instance of TesteExcecao */
    public TesteExcecao() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws MinhaException {
        // TODO code application logic here     
        String s = metodo(args);
        System.out.println(s);
    }
    
    public static String metodo(String [] args) throws MinhaException{
        String s;
        try  {
            int c = Integer.parseInt(args[0]);
            int c1 = Integer.parseInt(args[1]);
            int result = c/c1;
            s="O resultado da divis�o de " + c + " por " + c1 + " �: " + result;
            
        }
        catch(ArrayIndexOutOfBoundsException a) {
            throw new MinhaException("Passe parametros na execu��o", a);
        }
        catch(ArithmeticException ae) {
            throw new MinhaException("N�o � poss�vel dividir por 0", ae);
        }
        catch(NumberFormatException nfe) {
            throw new MinhaException("Passe apenas n�meros como parametros", nfe);
        }
        return s;
    }
}
