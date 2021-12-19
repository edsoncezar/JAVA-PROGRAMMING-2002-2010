public class Aluno {
    
    /* Declaração dos atributos: modificador de acesso - tipo - nome*/
    /** Atributos - características do que queremos representar no sistema */
    private String nome;
    private float nota;
    private String sala;
    
    
    /**  Contrutor: cria uma nova instancia de Aluno e inicializa com valores definidos: branco e zero */
    public Aluno(){
        /* Imprime na tela */
        System.out.println("Valores da Inicialização Padrão do Java "+nome + " "+ nota + " " +sala);
        // Agora vamos inicializar de outro jeito
        nome="";
        nota=0f; // Ou nota = 0.0f
        sala="";
    }
    
    /* Obs. A palavra this referencia o atributo da classe, a própria classe ou objeto corrente */
    
    /**  Contrutor: cria uma nova instancia de Aluno e inicializa com os parametros recebidos */
    public Aluno(String nome, float nota, String s){
        this.nome=nome;
        this.nota=nota;
        this.sala=s;
    }
    
    /** Método muda de sala que altera a sala do aluno */
    public void mudaDeSala(String sala){
        this.sala=sala;
        System.out.println("Você esta agora na sala: "+sala);
    }
    
     /* Métodos set para outras classes setarem valor nos atributos private
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
    
       /* Métodos get para outras classes pegarem valores dos atributos private
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
     * Método main - o cabeçalho é padrão. O String [] args é um array de String onde
     * tudo que for passado no momento dam execução cairá nas posições dele. Ex:
     * executando <java TestaArray a b 4 r> o a cairá na posição 0 do array, o b na posição 1,
     * o 4 na posição 2 e o r na posição 3 */
    
    public static void main(String [] args){
        
        // Cria um objeto do tipo Aluno chamado a - usa contrutor padrão (sem parametros)
        Aluno a = new Aluno();
        
        // Imprime dados do aluno a
        System.out.println("Valores inicializados pelo construtor: "+a.nome + " "+ a.nota + " " + a.sala);
        
        // Altera o nome de a para Fernando
        a.setNome("Fernando");
        
        // Imprime o nome do aluno a
        System.out.println("O nome agora é: "+a.nome);
        
        // Cria um objeto do tipo Aluno chamado a1 passando parametros - usa contrutor com parametros
        Aluno a1 = new Aluno("Ane",9.5f, "3C");
        
        // Imprime dados do aluno a1
        System.out.println("Usando os métodos get: "+ a1.getNome() + " "+ a1.getNota()+ " " + a1.getSala());
        
        // Altera a nota de a1 para 10
        a1.setNota(10);
        
        // Imprime a nota do aluno a1
        System.out.println("Nota: "+a1.nota);
        
        // Altera a sala do aluno a1
        a1.mudaDeSala("3B");
        
        // Usa o método getSala() para pegar o valor da sala do aluno a1 e joga o retorno na variável do tipo String sl
        String sl = a1.getSala();
        
        // Imprime a sala do aluno a1
        System.out.println("Valor da Sala: " + sl);         
    }
}

