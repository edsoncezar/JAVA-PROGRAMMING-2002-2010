public class ParticipanteForum implements Leitor, Programador {
    
    String pensamento;
    
    /** Creates a new instance of ParticipanteForum */
    public ParticipanteForum() {
    }
    
    public String lendo() { // método definido na interface Leitor
        return "Forum";
    }
    public void pensando(char[] ideias) { // método definido na interface Programador
        pensamento = new String(ideias);
    }
    public String digitando() { // método definido na interface Programador
        return pensamento;
    }
    private String aprendendo() { // método exclusivo desta classe
        return "Java";
    }
}
