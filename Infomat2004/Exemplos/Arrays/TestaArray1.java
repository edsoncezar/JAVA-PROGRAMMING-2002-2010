/* Declaração de classe */
public class TestaArray1 {
    
    /** Contrutor: Cria uma nova instancia de TestaArray1 */
    public TestaArray1() {
    }
    
    /**
     * Método main - o cabeçalho é padrão. O String [] args é um array de String onde
     * tudo que for passado no momento dam execução cairá nas posições dele. Ex:
     * executando <java TestaArray a b 4 r> o a cairá na posição 0 do array, o b na posição 1,
     * o 4 na posição 2 e o r na posição 3 */
    
    public static void main(String[] args) {
        /* Abre tratamento de exceções */
        try {
                /* Converte a posição 0 do array (que será passada em execução) para inteiro
                 * usando um método pronto da classe Integer e joga na variável local do tipo
                 * inteiro aluno */
            int aluno= Integer.parseInt(args[0]);
            /* Verifica se o que foi digitado é o que se espera */
            if(aluno >11){
                System.out.println("O número deve ser entre 0 e 1");
            }
            else {
                int notas []=null;
                
                // Obs. Tente colocar String [] nome=null; para ver se funciona
                String [] nome;
                
                /* Inicializa as posições com valores */
                nome[0]="Ane";
                nome[1]="Luiz";
                
                /* Imprime na tela */
                System.out.println("nome[0] "+ nome[0] + " nome[1] "+nome[1]);
                
                /* Inicializa as posições com valores */
                notas[0]=8;
                notas[1]=10;
                notas[2]=7;
                notas[3]=10;
                notas[4]=9;
                notas[5]=6;
                notas[6]=10;
                notas[7]=9;
                notas[8]=10;
                notas[9]=8;
                notas[10]=10;
                notas[11]=10;
                
                /* Imprime na tela */
                System.out.println("A nota do aluno " + aluno + " é: " + notas[aluno]);
            }
        }
        /* Pega a exceção caso ocorra e trata imprimindo uma mensagem na tela */
        catch (Exception e){
            System.out.println("Ocorreu uma exceção: " + e);
        }
    }
}

