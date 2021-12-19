public abstract class Conta {
    private float saldo;
    private int numero;

    public Conta(int numero) {
        this.numero = numero;
    }

    public void creditar(float quantia) {
        setSaldo(getSaldo() + quantia);
    }

    public void debitar(float quantia)
        throws SaldoInsuficienteException {
        float saldo = getSaldo();
        if (saldo < quantia) {
            throw new SaldoInsuficienteException(
                "O saldo e insuficiente");
        } else {
            setSaldo(saldo - quantia);
        }
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
}
