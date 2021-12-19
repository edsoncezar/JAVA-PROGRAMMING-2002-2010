public class Teste1 {
    
    /** Creates a new instance of main2 */
    public Teste1() {
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Descomente as linhas comentadas uma por vez e compile para verificar os erros
        
        //   StaticFinal.z="Ane";
        //    StaticFinal.c='B';
        
        /* Observe que x por ser estático manterá o mesmo valor com que foi inicializado mesmo
        * com a execução de Teste onde altero o valor de x para 20. A execução de Teste imprimirá
        *  20 mas a de Teste1 imprimirá 10. */
        System.out.println(StaticFinal.x);
        System.out.println(StaticFinal.z);
        //  System.out.println(StaticFinal.c);
    }
    
}
