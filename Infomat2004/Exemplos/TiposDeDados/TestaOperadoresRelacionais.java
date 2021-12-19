public class TestaOperadoresRelacionais {
    
    /** Contrutor: cria uma nova instancia de TestaOperadoresRelacionais */
    public TestaOperadoresRelacionais() {
    }
    
        /**
     * Método main - o cabeçalho é padrão. O String [] args é um array de String onde
     * tudo que for passado no momento dam execução cairá nas posições dele. Ex:
     * executando <java TestaArray a b 4 r> o a cairá na posição 0 do array, o b na posição 1,
     * o 4 na posição 2 e o r na posição 3 */
    
    public static void main(String[] args) {
        // Variáveis locais pois estão dentro de um método        
        int a=10;
        int b=12;
        
        /* Testa Operadores Relacionais
        * Imprime o resultado das operações na tela */
        
        System.out.println("a==b-> "+(a==b));
        System.out.println("a!=b-> "+(a!=b));
        System.out.println("a<b-> "+(a<b));
        System.out.println("a>b-> "+(a>b));
        System.out.println("a<=b-> "+(a<=b));
        System.out.println("a>=b-> "+(a>=b));
        
        a=b; // Atribuição
        
        System.out.println("a= "+a);
        System.out.println("b= "+b);
        
        /** Um exemplo de Operador Lógico bit a bit: O valor de 15 em binário é 1111
         * com este operador, >> que desloca bits a direita temos 0111 que representa o número 7
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
