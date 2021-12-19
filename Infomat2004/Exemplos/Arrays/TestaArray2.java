public class TestaArray2 {
    
    /**  Contrutor: Cria uma nova instancia de TestaArray2 */
    public TestaArray2() {
    }
    
    /**
     * M�todo main - o cabe�alho � padr�o. O String [] args � um array de String onde
     * tudo que for passado no momento dam execu��o cair� nas posi��es dele. Ex:
     * executando <java TestaArray a b 4 r> o a cair� na posi��o 0 do array, o b na posi��o 1,
     * o 4 na posi��o 2 e o r na posi��o 3 */
    
    public static void main(String[] args) {
        
          /* Declara um array de Cal�as com 2 posi��es - de 0 at� 1 pois em Java
           * a primeita posi��o de um array � a 0 */
        
        Calca [] c = new Calca[2];
        
        /* Cria uma nova instancia de Cal�a para cada posi��o sen�o
         * ocorre NullPointerException */
        c[0]= new Calca();
        c[1]= new Calca();
        
        /* Usa os m�todos set para alterar valores das cal�as criadas */
        c[0].setNome("Social");
        c[0].setCodigo(01);
        
        c[1].setNome("Jeans");
        c[1].setCodigo(02);
        
        /* Imprime na tela */
        System.out.println("Nome de c[0]: "+ c[0].getNome()+ " C�digo de c[0]: "+ c[0].getCodigo()+
        "\nNome de c[1]: "+ c[1].getNome() + " C�digo de c[1]: "+c[1].getCodigo());
    }
    
}
