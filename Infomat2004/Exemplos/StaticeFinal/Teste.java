public class Teste {
    
    /** Creates a new instance of main */
    public Teste() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        StaticFinal.x=20;
        // Descomente as linhas comentadas uma por vez e compile para verificar os erros
        
        //   StaticFinal.z="Ane";
        //   StaticFinal.c='B';
        System.out.println(StaticFinal.x);
        System.out.println(StaticFinal.z);
        //  System.out.println(StaticFinal.c);
    }
    
}
