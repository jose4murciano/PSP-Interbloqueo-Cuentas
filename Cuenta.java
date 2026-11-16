public class Cuenta {
    private final String numeroCuenta;
    private double saldo;

    public Cuenta(String numeroCuenta, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    public synchronized double getSaldo() {
        return saldo;
    }

    public synchronized void ingresaCantidad(double cantidad) {
        saldo += cantidad;
    }

    public synchronized boolean sacarCantidad(double cantidad) {
        if (cantidad <= 0 || saldo < cantidad) {
            return false;
        }

        saldo -= cantidad;
        return true;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    @Override
    public synchronized String toString() {
        return "Cuenta " + numeroCuenta + " - saldo: " + saldo;
    }
}
