public class ExemploIf {
    
    /** Creates a new instance of ExemploIf1 */
    public ExemploIf() {
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
    }
}


