// Este é um comentário de uma linha só

/* Este é
 *um cometário de mais de uma linha */

/** Este é um comentário
 * de Javadoc (documentação) que pode ser de mais de uma linha */

/* Declaração do pacote ("diretório") onde a classe se encontra */
// package ClasseseObjetos;

import java.util.Date;
public class Aluno1 {
    
    /* Declaração dos atributos: modificador de acesso - tipo - nome*/
    /** Atributos - características do que queremos representar no sistema */
    private String nome;
    private float nota;
    private String sala;
    
    /* Bloco aberto sem ser método ou constutor.
     * Funciona mas não faça isso! Pois o código solto na classe
     * pode perder o sentido */
    
    {
        int a=10;
        System.out.println("Oi "+a);
    }
    
    /**  Contrutor: cria uma nova instancia de Aluno e inicializa com valores definidos: branco e zero */
    public Aluno1(){
        System.out.println("Valores da Inicialização Padrão do Java "+nome + " "+ nota + " " +sala);
        // Agora vamos inicializar de outro jeito
        nome="";
        nota=0.0f;
        sala="";
        
        /* Outro bloco perdido no código - não faça isso! */
        {
            System.out.println("Oi");
        }
    }
    /* Overloading (Sobrecarga) de contrutores (ter mais que um com mesmo nome - funciona para contrutores e métodos)
     * Neste eu tenho parametros*/
    public Aluno1(String nome, float nota, String s){
        this.nome=nome;
        this.nota=nota;
        this.sala=s;
    }
    
    /* Neste eu mudo a ordem dos parametros */
    public Aluno1(float nota, String s, String nome){
        this.nome=nome;
        this.nota=nota;
        this.sala=s;
    }
    
    /* Neste eu mudo o tipo dos parametros */
    public Aluno1(String exemplo, String s, String nome){
        this.nome=nome;
        this.nota=nota;
        this.sala=s;
    }
    
    /* Neste eu mudo a quantidade dos parametros */
    public Aluno1(String s, String nome){
        this.nome=nome;
        this.nota=nota;
        this.sala=s;
    }
    
    /** Método muda de sala que altera a sala do aluno */
    public void mudaDeSala(String sala){
        this.sala=sala;
        System.out.println("Você esta agora na sala: "+sala);
        
        /* Outro bloco perdido no código - não faça isso! */
        {
            System.out.println("Oi");
        }
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
    
    static public void main(String [] args){
        // Cria um novo objeto do tipo Data chamado d a partir da classe Data (pré-definida na API - Application Programming Interface)
        Date d = new Date();
        
        // Imprime a data
        System.out.println("Data e hora de hoje: "+ d);
        
        // Cria um objeto do tipo Aluno1 chamado a - usa contrutor padrão (sem parametros)
        Aluno1 a = new Aluno1();
        
        // Imprime dados do aluno a
        System.out.println("Valores inicializados pelo construtor: " + a.nome + " "+ a.nota + " " + a.sala);
        
        // Altera o nome de a para Fernando
        a.setNome("Fernando");
        
        // Imprime o nome do aluno a
        System.out.println("O nome agora é: " + a.nome);
        
        // Cria um objeto do tipo Aluno chamado a1 passando parametros - usa contrutor com parametros
        Aluno1 a1 = new Aluno1("Ane",9.5f, "3C");
        
        // Imprime dados do aluno a1
        System.out.println("Usando os métodos get: " + a1.getNome() + " "+ a1.getNota()+ " " + a1.getSala());
        
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
