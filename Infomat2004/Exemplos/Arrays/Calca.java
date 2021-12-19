public class Calca {
    
    /* Declaração dos atributos: modificador de acesso - tipo - nome*/
    private int codigo;
    private String nome;
    
    /** Contrutor: cria uma nova instancia de Calca */
    public Calca() {
        /* Inicializa valores */
        codigo=0;
        nome="";
    }
    
    /* Métodos set para outras classes setarem valor nos atributos private
     * desta classe (encapsulamento) */
    
    public void setNome(String nome){
        this.nome=nome;
    }
    
    public void setCodigo(int codigo){
        this.codigo=codigo;
    }
    
    /* Métodos get para outras classes pegarem valores dos atributos private
     * desta classe (encapsulamento) */
    public String getNome(){
        return this.nome;
    }
    
    public int getCodigo(){
        return this.codigo;
    }
}
