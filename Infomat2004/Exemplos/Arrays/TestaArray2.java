public class TestaArray2 {
    
    /**  Contrutor: Cria uma nova instancia de TestaArray2 */
    public TestaArray2() {
    }
    
    /**
     * Método main - o cabeçalho é padrão. O String [] args é um array de String onde
     * tudo que for passado no momento dam execução cairá nas posições dele. Ex:
     * executando <java TestaArray a b 4 r> o a cairá na posição 0 do array, o b na posição 1,
     * o 4 na posição 2 e o r na posição 3 */
    
    public static void main(String[] args) {
        
          /* Declara um array de Calças com 2 posições - de 0 até 1 pois em Java
           * a primeita posição de um array é a 0 */
        
        Calca [] c = new Calca[2];
        
        /* Cria uma nova instancia de Calça para cada posição senão
         * ocorre NullPointerException */
        c[0]= new Calca();
        c[1]= new Calca();
        
        /* Usa os métodos set para alterar valores das calças criadas */
        c[0].setNome("Social");
        c[0].setCodigo(01);
        
        c[1].setNome("Jeans");
        c[1].setCodigo(02);
        
        /* Imprime na tela */
        System.out.println("Nome de c[0]: "+ c[0].getNome()+ " Código de c[0]: "+ c[0].getCodigo()+
        "\nNome de c[1]: "+ c[1].getNome() + " Código de c[1]: "+c[1].getCodigo());
    }
    
}
