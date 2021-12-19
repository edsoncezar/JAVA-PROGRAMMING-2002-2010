public class Teste {
    public static void main(String[] args) 
        throws SaldoInsuficienteException {
        Conta conta = new ContaPoupanca(12456);
        conta.creditar(100);
        conta.debitar(50);
    }
}
