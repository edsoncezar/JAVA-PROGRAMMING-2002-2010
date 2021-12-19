public class Desenha {
    
    /** Creates a new instance of Desenha */
    public Desenha() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int altura=3;
        int largura=10;
        int contaLinha =0;
        int contaColuna=0;
        
        while (contaLinha < altura) {
            contaColuna=0;
            while (contaColuna< largura) {
                System.out.print("@");
                contaColuna++;
            }
            System.out.println();
            contaLinha++;
        }
    }
    
}
