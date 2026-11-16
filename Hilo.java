public class Hilo extends Thread {
    private final String nombre;
    private final Cuenta c1;
    private final Cuenta c2;

    public Hilo(String nombre, Cuenta c1, Cuenta c2) {
        super(nombre);
        this.nombre = nombre;
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            boolean realizada = Transferencia.transferencia(c1, c2, 10);
            if (!realizada) {
                System.out.println(nombre + " no pudo realizar la transferencia " + (i + 1));
            }
        }

        System.out.println(nombre + " ha terminado.");
    }
}
