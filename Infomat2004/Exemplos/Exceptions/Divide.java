public class Divide {
    
    /** Creates a new instance of Divide */
    public Divide() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int a=10;
        int b; // Declaração de b
             
        try{
            b=Integer.parseInt(args[0]); // Inicialização de b
            int resultado= a/b;
            System.out.println("Resultado da Divisão: "+ resultado);
        }
        catch(Exception e){
            System.out.println("Não é possivel dividir por 0 "+e);
        }
        finally{
            System.out.println("Esse foi um teste de tratamento de Exceptions");
        }
    }
    
    
    
}
