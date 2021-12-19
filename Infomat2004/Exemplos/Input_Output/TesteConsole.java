public class TesteConsole {
    
    /** Creates a new instance of TesteConsole */
    public TesteConsole() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Forneça um número real|: ");
        double numd = Console.readDouble();
        System.out.println("Número fornecido: "+numd);
        
        System.out.println("Forneça um número inteiro: ");
        int numi = Console.readInteger();
        System.out.println("Número fornecido: "+numi);
        
        System.out.println("Forneça uma string: ");
        String s = Console.readString();
        System.out.println("String fornecida: "+s);
    }    
}
