package OutroPacote;
public class Calca {
    
    private int codigo;
    private String nome;
    
    /** Creates a new instance of Calca */
    public Calca() {
        codigo=0;
        nome="";
    }
    
    public void setNome(String nome){
        this.nome=nome;
    }
    
    public void setCodigo(int codigo){
        this.codigo=codigo;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int getCodigo(){
        return this.codigo;
    }
}
