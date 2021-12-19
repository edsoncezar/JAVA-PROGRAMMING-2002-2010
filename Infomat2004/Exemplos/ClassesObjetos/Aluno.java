public class Aluno {
    
    /* Declara��o dos atributos: modificador de acesso - tipo - nome*/
    /** Atributos - caracter�sticas do que queremos representar no sistema */
    private String nome;
    private float nota;
    private String sala;
    
    
    /**  Contrutor: cria uma nova instancia de Aluno e inicializa com valores definidos: branco e zero */
    public Aluno(){
        /* Imprime na tela */
        System.out.println("Valores da Inicializa��o Padr�o do Java "+nome + " "+ nota + " " +sala);
        // Agora vamos inicializar de outro jeito
        nome="";
        nota=0f; // Ou nota = 0.0f
        sala="";
    }
    
    /* Obs. A palavra this referencia o atributo da classe, a pr�pria classe ou objeto corrente */
    
    /**  Contrutor: cria uma nova instancia de Aluno e inicializa com os parametros recebidos */
    public Aluno(String nome, float nota, String s){
        this.nome=nome;
        this.nota=nota;
        this.sala=s;
    }
    
    /** M�todo muda de sala que altera a sala do aluno */
    public void mudaDeSala(String sala){
        this.sala=sala;
        System.out.println("Voc� esta agora na sala: "+sala);
    }
    
     /* M�todos set para outras classes setarem valor nos atributos private
      * desta classe (encapsulamento) */
    public void setNome(String nome){
        this.nome=nome;
    }
    
    public void setNota(float nota){
        this.nota = nota;
    }
    
    public void setSala(String sal){
        this.sala=sala;
    }
    
       /* M�todos get para outras classes pegarem valores dos atributos private
        * desta classe (encapsulamento) */
    public String getNome(){
        return this.nome;
    }
    
    public float getNota(){
        return this.nota;
    }
    
    public String getSala(){
        return this.sala;
    }
    
   
    /**
     * M�todo main - o cabe�alho � padr�o. O String [] args � um array de String onde
     * tudo que for passado no momento dam execu��o cair� nas posi��es dele. Ex:
     * executando <java TestaArray a b 4 r> o a cair� na posi��o 0 do array, o b na posi��o 1,
     * o 4 na posi��o 2 e o r na posi��o 3 */
    
    public static void main(String [] args){
        
        // Cria um objeto do tipo Aluno chamado a - usa contrutor padr�o (sem parametros)
        Aluno a = new Aluno();
        
        // Imprime dados do aluno a
        System.out.println("Valores inicializados pelo construtor: "+a.nome + " "+ a.nota + " " + a.sala);
        
        // Altera o nome de a para Fernando
        a.setNome("Fernando");
        
        // Imprime o nome do aluno a
        System.out.println("O nome agora �: "+a.nome);
        
        // Cria um objeto do tipo Aluno chamado a1 passando parametros - usa contrutor com parametros
        Aluno a1 = new Aluno("Ane",9.5f, "3C");
        
        // Imprime dados do aluno a1
        System.out.println("Usando os m�todos get: "+ a1.getNome() + " "+ a1.getNota()+ " " + a1.getSala());
        
        // Altera a nota de a1 para 10
        a1.setNota(10);
        
        // Imprime a nota do aluno a1
        System.out.println("Nota: "+a1.nota);
        
        // Altera a sala do aluno a1
        a1.mudaDeSala("3B");
        
        // Usa o m�todo getSala() para pegar o valor da sala do aluno a1 e joga o retorno na vari�vel do tipo String sl
        String sl = a1.getSala();
        
        // Imprime a sala do aluno a1
        System.out.println("Valor da Sala: " + sl);         
    }
}

