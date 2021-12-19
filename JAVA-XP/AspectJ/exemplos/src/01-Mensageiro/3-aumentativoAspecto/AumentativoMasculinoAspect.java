/**
 * Este aspecto modifica o nome de uma pessoa do sexo masculino colocando-o no aumentativo.
 * @author <a href="http://www.paulojeronimo.eti.br">Paulo Jeronimo</a>
 */
public aspect AumentativoMasculinoAspect {
    pointcut modifiquePessoa(String pessoa): 
        call(* Mensageiro.entregar(String, String)) && 
        args(pessoa, String);

    /**
     * Modifica o parametro pessoa de acordo com os seguintes Exemplos:
     * alemar - alemarzao
     * pedro - pedrao
     */
    void around(String pessoa) : modifiquePessoa(pessoa) {
        if (pessoa.endsWith("r")) {
            proceed(pessoa + "zao");
        } else if (pessoa.endsWith("o")) {
            pessoa = pessoa.substring(0, pessoa.length() - 1);
            proceed(pessoa + "ao");
        } else {
            proceed(pessoa);
        }
    }
}
