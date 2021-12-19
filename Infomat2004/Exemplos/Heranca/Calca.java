public class Calca extends Roupa {
    
    public int comprimentoPerna;
    
    /** Creates a new instance of Calca */
    public Calca() {
        setNome("");
        this.tamanho=' ';
        this.cor ="";
        this.codigo=0;
    }
    
    public void setComprimentoPerna(int cp){
        this.comprimentoPerna=cp;
    }
    
    public int getComprimentoPerna(){
        return this.comprimentoPerna;
    }
}
