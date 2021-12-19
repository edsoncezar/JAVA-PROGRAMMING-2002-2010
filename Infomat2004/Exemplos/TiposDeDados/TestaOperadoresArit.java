public class TestaOperadoresArit {
    
    /** Contrutor: cria uma nova instancia de TestaOperadores */
    public TestaOperadoresArit() {
    }
    
       /**
     * M�todo main - o cabe�alho � padr�o. O String [] args � um array de String onde
     * tudo que for passado no momento dam execu��o cair� nas posi��es dele. Ex:
     * executando <java TestaArray a b 4 r> o a cair� na posi��o 0 do array, o b na posi��o 1,
     * o 4 na posi��o 2 e o r na posi��o 3 */
    
    public static void main(String[] args) {          
        // Vari�veis locais pois est�o dentro de um m�todo
        int a=5;
        int b=2;
        float f=0f;
          
       /* Testa Operadores Aritim�ticos         
        * Imprime o resultado das opera��es na tela */
        System.out.println("a= "+a);
        System.out.println("b= "+b);
        System.out.println("-b= " + (-b));
        System.out.println("a+b= "+(a+b));
        System.out.println("a-b= "+(a-b));
        System.out.println("a*b= "+(a*b));
        System.out.println("a/b= "+(a/b));
        System.out.println("a%b= "+(a%b)); // Pega resto inteiro da divis�o
        /* Usa o m�todo pow da classe Math para fazer potencia (o primeiro elevado ao segundo parametro).
        * O valor de retorno cai em uma vari�vel do tipo double chamada p */
        double p=Math.pow(a,b); 
        System.out.println("a elevado a b= "+p);
        f= (float)a/b; // Isso � um cast (convers�o) expl�cito de int para float;        
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
