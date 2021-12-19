public class TestaRoupa {
    
    /** Creates a new instance of TestaRoupa */
    public TestaRoupa() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Camiseta c = new Camiseta();
        System.out.println(c.getNome()+" "+ c.cor +" "+c.tamanho+" "+c.codigo);
        
        Camiseta c1 = new Camiseta("Polo",'M');
        System.out.println(c1.getNome()+" "+ c1.cor +" "+c1.tamanho+" "+c1.codigo);
        
        Calca cal = new Calca();
        System.out.println(cal.getNome()+" "+ cal.cor +" "+cal.tamanho+" "+cal.codigo);
        
        c.cor="Verde";
        c.tamanho='G';
        c.codigo=1;
        c.setNome("Polo");
        System.out.println(c.getNome()+" "+ c.cor +" "+c.tamanho+" "+c.codigo);
        
        c1.codigo=2;
        c1.cor="Vermelho";
        System.out.println(c1.getNome()+" "+ c1.cor +" "+c1.tamanho+" "+c1.codigo);
        
        c.setComprimentoManga(4);
        c1.setComprimentoManga(3);
        
        int comp= c.getComprimentoManga();
        System.out.println("Comprimento da Manga: "+comp);
        int comp1 = c1.getComprimentoManga();
        System.out.println("Comprimento da Manga: "+comp1);
        
        cal.setNome("Jeans");
        cal.cor="Azul";
        cal.tamanho='M';
        cal.codigo=3;
        System.out.println(cal.getNome()+" "+ cal.cor +" "+cal.tamanho+" "+cal.codigo);
        
        cal.setComprimentoPerna(8);
        int comp2= cal.getComprimentoPerna();
        System.out.println("Comprimento da Perna: "+comp2);
    }
    
}
