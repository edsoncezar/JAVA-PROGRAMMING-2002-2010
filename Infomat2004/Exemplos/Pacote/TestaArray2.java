import OutroPacote.*;
public class TestaArray2 {
    
    /** Creates a new instance of TestaArray2 */
    public TestaArray2() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Calca [] c = new Calca[2];
           
        c[0]= new Calca();
        c[1]= new Calca();
        
        c[0].setNome("Social");
        c[0].setCodigo(01);
          
        c[1].setNome("Jeans");
        c[1].setCodigo(02);

        System.out.println("Nome de c[0]: "+ c[0].getNome()+ " Código de c[0]: "+ c[0].getCodigo()+
        "\nNome de c[1]: "+ c[1].getNome() + " Código de c[1]: "+c[1].getCodigo());
    }
    
}
