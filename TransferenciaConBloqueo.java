import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class TransferenciaConBloqueo {
    public static void main(String[] args) {
        Cuenta c1 = new Cuenta("ES388388433883838388484", 20500);
        Cuenta c2 = new Cuenta("ES99090499388484889399", 50000);

        System.out.println("Saldos iniciales:");
        System.out.println(c1);
        System.out.println(c2);

        Hilo h1 = new Hilo("Jose Murciano", c1, c2);
        Hilo h2 = new Hilo("Jurado Javier", c2, c1);

        h1.setDaemon(true);
        h2.setDaemon(true);
        h1.start();
        h2.start();

        try {
            h1.join(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Se interrumpió la espera del hilo " + h1.getName());
        }

        try {
            h2.join(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Se interrumpió la espera del hilo " + h2.getName());
        }

        System.out.println("\nEstado de los hilos:");
        System.out.println(h1.getName() + ": " + h1.getState());
        System.out.println(h2.getName() + ": " + h2.getState());
        mostrarInterbloqueo();
    }

    private static void mostrarInterbloqueo() {
        ThreadMXBean gestorHilos = ManagementFactory.getThreadMXBean();
        long[] idsInterbloqueados = gestorHilos.findDeadlockedThreads();

        if (idsInterbloqueados == null) {
            System.out.println("No se ha detectado ningún interbloqueo.");
            return;
        }

        System.out.println("Interbloqueo detectado. Información de los hilos:");
        ThreadInfo[] informacion = gestorHilos.getThreadInfo(idsInterbloqueados, true, true);
        for (ThreadInfo hilo : informacion) {
            System.out.println(hilo);
        }
    }
}
