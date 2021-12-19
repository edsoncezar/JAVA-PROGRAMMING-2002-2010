public class ExemploIfElse2 {
    
    /** Creates a new instance of ExemploIfElse2 */
    public ExemploIfElse2() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int qtdVendas;
        int meta=10;
        String mensagem=null;
        
        qtdVendas = Integer.parseInt(args[0]);
        
        mensagem=((qtdVendas >= meta)?"Desempenho Satisfatório":"Melhorar Desempenho");
        System.out.println(mensagem);
    }
    
}
