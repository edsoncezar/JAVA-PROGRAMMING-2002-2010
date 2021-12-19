public class Media {
    
    /** Creates a new instance of Media */
    public Media() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws java.io.IOException {
        // TODO code application logic here
        float notaMedia=0;
        int contagem=0;
        int valor;
        System.out.println("Forneça como notas conceitos de A até E.");
        System.out.println("Um traço - encerra a entrada de notas.");
        System.out.println("Digite um valor: ");
        while('-'!= (valor=System.in.read())){
            notaMedia+='F'-valor;
            contagem++;
            System.in.skip(2);
            System.out.println("Digite um valor: ");
        }
        notaMedia/=contagem;
        System.out.println("Foram fornecidos "+ contagem + " valores.");
        System.out.println("Nota média = "+notaMedia);
        System.out.println("Conceito médio = "+(char) (70 - Math.round(notaMedia)));
    }
}


