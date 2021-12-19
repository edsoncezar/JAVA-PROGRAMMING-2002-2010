public class Desenha1 {
    
    /** Creates a new instance of Desenha1 */
    public Desenha1() {
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
        
        do {
            contaColuna=0;
            do {
                System.out.print("@");
                contaColuna++;
            }while (contaColuna< largura);
            System.out.println();
            contaLinha++;
        } while (contaLinha < altura);
        
    }
    
}
