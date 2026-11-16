import java.util.concurrent.CountDownLatch;

public class Transferencia {
    private static final CountDownLatch PRIMERAS_TRANSFERENCIAS = new CountDownLatch(2);

    private Transferencia() {
    }

    public static boolean transferencia(Cuenta c1, Cuenta c2, int cantidad) {
        if (c1 == c2 || cantidad <= 0) {
            return false;
        }

        synchronized (c1) {
            if (!c1.sacarCantidad(cantidad)) {
                return false;
            }

            esperarPrimerasTransferencias();

            synchronized (c2) {
                c2.ingresaCantidad(cantidad);
                return true;
            }
        }
    }

    private static void esperarPrimerasTransferencias() {
        PRIMERAS_TRANSFERENCIAS.countDown();
        boolean interrumpido = false;

        while (true) {
            try {
                PRIMERAS_TRANSFERENCIAS.await();
                break;
            } catch (InterruptedException e) {
                interrumpido = true;
            }
        }

        if (interrumpido) {
            Thread.currentThread().interrupt();
        }
    }
}
