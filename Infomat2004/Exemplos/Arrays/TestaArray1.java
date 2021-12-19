/* Declara��o de classe */
public class TestaArray1 {
    
    /** Contrutor: Cria uma nova instancia de TestaArray1 */
    public TestaArray1() {
    }
    
    /**
     * M�todo main - o cabe�alho � padr�o. O String [] args � um array de String onde
     * tudo que for passado no momento dam execu��o cair� nas posi��es dele. Ex:
     * executando <java TestaArray a b 4 r> o a cair� na posi��o 0 do array, o b na posi��o 1,
     * o 4 na posi��o 2 e o r na posi��o 3 */
    
    public static void main(String[] args) {
        /* Abre tratamento de exce��es */
        try {
                /* Converte a posi��o 0 do array (que ser� passada em execu��o) para inteiro
                 * usando um m�todo pronto da classe Integer e joga na vari�vel local do tipo
                 * inteiro aluno */
            int aluno= Integer.parseInt(args[0]);
            /* Verifica se o que foi digitado � o que se espera */
            if(aluno >11){
                System.out.println("O n�mero deve ser entre 0 e 1");
            }
            else {
                int notas []=null;
                
                // Obs. Tente colocar String [] nome=null; para ver se funciona
                String [] nome;
                
                /* Inicializa as posi��es com valores */
                nome[0]="Ane";
                nome[1]="Luiz";
                
                /* Imprime na tela */
                System.out.println("nome[0] "+ nome[0] + " nome[1] "+nome[1]);
                
                /* Inicializa as posi��es com valores */
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
                System.out.println("A nota do aluno " + aluno + " �: " + notas[aluno]);
            }
        }
        /* Pega a exce��o caso ocorra e trata imprimindo uma mensagem na tela */
        catch (Exception e){
            System.out.println("Ocorreu uma exce��o: " + e);
        }
    }
}

