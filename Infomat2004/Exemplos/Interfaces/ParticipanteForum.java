public class ParticipanteForum implements Leitor, Programador {
    
    String pensamento;
    
    /** Creates a new instance of ParticipanteForum */
    public ParticipanteForum() {
    }
    
    public String lendo() { // m�todo definido na interface Leitor
        return "Forum";
    }
    public void pensando(char[] ideias) { // m�todo definido na interface Programador
        pensamento = new String(ideias);
    }
    public String digitando() { // m�todo definido na interface Programador
        return pensamento;
    }
    private String aprendendo() { // m�todo exclusivo desta classe
        return "Java";
    }
}
