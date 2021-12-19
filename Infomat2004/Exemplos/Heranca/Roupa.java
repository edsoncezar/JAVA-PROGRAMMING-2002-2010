public class Roupa {
    
    private String nome;
    public char tamanho;
    protected String cor;
    int codigo;
    
    /** Creates a new instance of Roupa */
    public Roupa() {
       
    }
    
    public void setNome(String n){
        this.nome=n;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void lavarRoupa(){
        System.out.println("Roupa Lavada!");
    }
    
    public void passarRoupa(){
        System.out.println("Roupa Passada!");
    }
}

class Camiseta extends Roupa {
    public int comprimentoManga;
    
    public Camiseta(){
        
    }
    
    public Camiseta(String nome, char tamanho){
        setNome(nome);
        this.tamanho=tamanho;
    }  
    
    public void setComprimentoManga(int cm){
        this.comprimentoManga=cm;
    }
    
    public int getComprimentoManga(){
        return this.comprimentoManga;
    }
}


