public class TestaOperadoresRelacionais {
    
    /** Contrutor: cria uma nova instancia de TestaOperadoresRelacionais */
    public TestaOperadoresRelacionais() {
    }
    
        /**
     * M�todo main - o cabe�alho � padr�o. O String [] args � um array de String onde
     * tudo que for passado no momento dam execu��o cair� nas posi��es dele. Ex:
     * executando <java TestaArray a b 4 r> o a cair� na posi��o 0 do array, o b na posi��o 1,
     * o 4 na posi��o 2 e o r na posi��o 3 */
    
    public static void main(String[] args) {
        // Vari�veis locais pois est�o dentro de um m�todo        
        int a=10;
        int b=12;
        
        /* Testa Operadores Relacionais
        * Imprime o resultado das opera��es na tela */
        
        System.out.println("a==b-> "+(a==b));
        System.out.println("a!=b-> "+(a!=b));
        System.out.println("a<b-> "+(a<b));
        System.out.println("a>b-> "+(a>b));
        System.out.println("a<=b-> "+(a<=b));
        System.out.println("a>=b-> "+(a>=b));
        
        a=b; // Atribui��o
        
        System.out.println("a= "+a);
        System.out.println("b= "+b);
        
        /** Um exemplo de Operador L�gico bit a bit: O valor de 15 em bin�rio � 1111
         * com este operador, >> que desloca bits a direita temos 0111 que representa o n�mero 7
         */
        
        int nroOriginal = 15;
        int meuShift = nroOriginal >> 1;
        
        System.out.println("nroOriginal "+nroOriginal);
        System.out.println("meuShift= "+meuShift);
        
        // Agora deslocando um bit para esquerda
        
        meuShift = nroOriginal << 1;
        
        /* Imprime o resultado */
        System.out.println("\nnroOriginal "+nroOriginal);
        System.out.println("meuShift= "+meuShift);
    }
    
}
