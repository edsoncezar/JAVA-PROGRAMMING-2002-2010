/**
 * Este aspecto acrescenta um "Ola" antes da mensagem ser entregue
 * @author <a href="http://www.paulojeronimo.eti.br">Paulo Jeronimo</a>
 */
public aspect AcrescentaOlaAspect {

    /**
     * JoinPoint - ao chamar um metodo entregar da classe Mensageiro
     */
    pointcut entregarMensagem() : 
        call(* Mensageiro.entregar(..));

    /**
     * Advice - antes de alcancar o JoinPoint entregarMensagem
     */
    before() : entregarMensagem() {
        System.out.print("Ola! ");
    }
}
