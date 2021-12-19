public class TestaFuncionario1 {
    
    /** Creates a new instance of TestaFuncionario1 */
    public TestaFuncionario1() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Funcionario f = new Gerente();
        Funcionario f1 = new Supervisor();
        
        if (f instanceof Gerente) {
            System.out.println("É um gerente");
        }
        else if (f instanceof Supervisor){
            System.out.println("É um supervisor");
        }
        else {
            System.out.println("Não é um gerente nem um supervisor");
        }
        
        if (f1 instanceof Gerente) {
            System.out.println("É um gerente");
        }
        else if (f1 instanceof Supervisor) {
            System.out.println("É um supervisor");
        }
        else {
            System.out.println("Não é um gerente nem um supervisor");
        }
        
    }
    
}
