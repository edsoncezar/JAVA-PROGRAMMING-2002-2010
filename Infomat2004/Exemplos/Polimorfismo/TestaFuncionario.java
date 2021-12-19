public class TestaFuncionario {
    
    /** Creates a new instance of TestaFuncionario */
    public TestaFuncionario() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Funcionario f = new Gerente();
        Funcionario f1 = new Supervisor();
        
        f.calculaSalario();
        f1.calculaSalario();
    }
    
}
