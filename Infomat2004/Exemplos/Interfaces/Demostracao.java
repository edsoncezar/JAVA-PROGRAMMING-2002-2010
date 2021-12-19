public class Demostracao {
    
    /** Creates a new instance of Demostracao */
    public Demostracao() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ParticipanteForum participante = new ParticipanteForum(); // instanciado o objeto
        Leitor leitor = participante; // upcast para Leitor
        System.out.println("O participante está lendo " + leitor.lendo());
        Programador programador = participante; // upcast para Programador
        String java = "Java";
        programador.pensando(java.toCharArray());
        System.out.println("E programando " + programador.digitando());
    }
    
}
