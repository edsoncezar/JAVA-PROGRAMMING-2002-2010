public class ExemploIfElseIf {
    
    /** Creates a new instance of ExemploIfElseIf */
    public ExemploIfElseIf() {
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
        else if (qtdVendas >= (meta/2)) {
            System.out.println("Desempenho Razoavel");
        }
        else {
            System.out.println("Desempenho Ruim");
        }
    }
}

 
