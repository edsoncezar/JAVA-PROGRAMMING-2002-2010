public class TiposDeDadosPrimitivos {
    
    // Obs. Tentem aumentar em uma unidade o valor de inicializa��o dos atributos e compilar
    
    /* Declara��o de atributos - est�o logo ap�s a declara��o de classe e tem modificador de acesso
     * no caso public */
    public byte b;
    public short s;
    public int i;
    public long l;
    
    public float f;
    public double d;
    
    public char c;
    public boolean bo;
    
    /** Contrutor: cria uma nova instancia de TiposDeDados inicializando seua atributos com alguns valores */
    public TiposDeDadosPrimitivos() {
        // Inicializa��o Padr�o do Java
        metodo();
        // Nossa Inicializa��o
        b=127;
        s=32767;
        i=2147483647;
        l=9223372036854775807l;
        f=3.40282347f; // Ou 0.0f
        d=1.79769313486231579;
        c='A';
        bo=true;
    }
    
    /** M�todo que imprime na tela os valores corrente dos atributos */
    public void metodo(){
        System.out.println("byte: "+b);
        System.out.println("short: "+s);
        System.out.println("int: "+i);
        System.out.println("long: "+l);
        System.out.println("float: "+f);
        System.out.println("double: "+d);
        System.out.println("char: "+c);
        System.out.println("boolean: "+bo+"\n\n");
    }
    
    /**
     * M�todo main - o cabe�alho � padr�o. O String [] args � um array de String onde
     * tudo que for passado no momento dam execu��o cair� nas posi��es dele. Ex:
     * executando <java TestaArray a b 4 r> o a cair� na posi��o 0 do array, o b na posi��o 1,
     * o 4 na posi��o 2 e o r na posi��o 3 */
    
    public static void main(String[] args) {
        // Cria um objeto td do tipo TiposDeDadosPrimitivos e a partir dele chama o m�todo chamado m�todo
        TiposDeDadosPrimitivos td = new TiposDeDadosPrimitivos();
        td.metodo();
        
    }
    
}
