public class TesteConsole {
    
    /** Creates a new instance of TesteConsole */
    public TesteConsole() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Forne�a um n�mero real|: ");
        double numd = Console.readDouble();
        System.out.println("N�mero fornecido: "+numd);
        
        System.out.println("Forne�a um n�mero inteiro: ");
        int numi = Console.readInteger();
        System.out.println("N�mero fornecido: "+numi);
        
        System.out.println("Forne�a uma string: ");
        String s = Console.readString();
        System.out.println("String fornecida: "+s);
    }    
}
