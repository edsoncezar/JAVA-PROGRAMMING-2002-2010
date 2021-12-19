public class TestaDivide1 {
    
    /** Creates a new instance of TestaDivide1 */
    public TestaDivide1() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        metodo(args);
    }
    
    public static void metodo(String [] args) {
        int a, b;
        try{
            a= Integer.parseInt(args[0]);
            b= Integer.parseInt(args[1]);
            Divide1 d = new Divide1();
            d.retornaResultado(a,b);
        }
        catch (ArithmeticException ae){
            System.out.println("Não é possível dividir por 0.");
        }
        catch (ArrayIndexOutOfBoundsException ai){
            System.out.println("Pase parametros no momento da execução!");
        }
        catch (NumberFormatException nfe){
            System.out.println("Pase apenas números como parametro!");
        }
    }
}
