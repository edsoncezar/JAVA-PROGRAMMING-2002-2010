public aspect SaldoMinimoAspect {
    private float Conta.saldoMinimo;

    public Conta.new(int numeroConta, float saldoMinimo) {
        this(numeroConta);
        this.saldoMinimo = saldoMinimo;
    }

    public float Conta.getSaldoDisponivel() {
        return getSaldo() - saldoMinimo;
    }

    public float Conta.getSaldoMinimo() {
        return saldoMinimo;
    }

    after(Conta conta) : 
        execution(ContaPoupanca.new(..)) && this(conta) {
        conta.saldoMinimo = 100;
    }

    before(Conta conta, float quantia) throws SaldoInsuficienteException :
        execution(* Conta.debitar(float)) && this(conta) && args(quantia) {
        if (conta.getSaldoDisponivel() < quantia) {
            throw new SaldoInsuficienteException(
                "voce nao pode ter um saldo inferior a " + 
                conta.getSaldoMinimo());
        }
    }

    public static void main(String args[]) {
        int numeroDaConta = 123456;

        Conta c = new ContaPoupanca(numeroDaConta);
        c.creditar(200);

        System.out.println("saldo = " + c.getSaldo());
        System.out.println("saldo disponivel = " + c.getSaldoDisponivel());

        try {
            debitar(c, 70);
            debitar(c, 50);
            debitar(c, 90);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void debitar(Conta c, float quantia) throws SaldoInsuficienteException {
        System.out.println("tentando sacar " + quantia);
        c.debitar(quantia);
        System.out.println("saldo = " + c.getSaldo());
    }
}
