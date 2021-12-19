public class ExemploIfElse1 {
    
    /** Creates a new instance of ExemploIfElse1 */
    public ExemploIfElse1() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int qtdVendas;
        int meta=10;
        
        qtdVendas = Integer.parseInt(args[0]);
        
        if (qtdVendas >= meta) {
            System.out.println("Desempenho Satisfatório");
        }
        else {
            System.out.println("Melhorar Desempenho");
        }
    }
}


