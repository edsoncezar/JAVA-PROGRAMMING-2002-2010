public class TestaOperadoresArit {
    
    /** Contrutor: cria uma nova instancia de TestaOperadores */
    public TestaOperadoresArit() {
    }
    
       /**
     * Método main - o cabeçalho é padrão. O String [] args é um array de String onde
     * tudo que for passado no momento dam execução cairá nas posições dele. Ex:
     * executando <java TestaArray a b 4 r> o a cairá na posição 0 do array, o b na posição 1,
     * o 4 na posição 2 e o r na posição 3 */
    
    public static void main(String[] args) {          
        // Variáveis locais pois estão dentro de um método
        int a=5;
        int b=2;
        float f=0f;
          
       /* Testa Operadores Aritiméticos         
        * Imprime o resultado das operações na tela */
        System.out.println("a= "+a);
        System.out.println("b= "+b);
        System.out.println("-b= " + (-b));
        System.out.println("a+b= "+(a+b));
        System.out.println("a-b= "+(a-b));
        System.out.println("a*b= "+(a*b));
        System.out.println("a/b= "+(a/b));
        System.out.println("a%b= "+(a%b)); // Pega resto inteiro da divisão
        /* Usa o método pow da classe Math para fazer potencia (o primeiro elevado ao segundo parametro).
        * O valor de retorno cai em uma variável do tipo double chamada p */
        double p=Math.pow(a,b); 
        System.out.println("a elevado a b= "+p);
        f= (float)a/b; // Isso é um cast (conversão) explícito de int para float;        
        System.out.println("f= "+f);  
        // Decrementos (--) e Incremento (++)
        System.out.println("a--= "+(a--));
        System.out.println("a= "+a);
        System.out.println("--a= "+(--a));
        System.out.println("b++= "+(b++));
        System.out.println("b= "+b);
        System.out.println("++b= "+(++b));
        System.out.println("a= "+a);
        System.out.println("b= "+b);                 
    }
    
}
