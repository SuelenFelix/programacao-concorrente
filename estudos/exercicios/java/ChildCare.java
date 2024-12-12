package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

/*Em uma creche, as normas estaduais exigem que haja sempre um adulto
presente para cada três crianças. */
public class ChildCare {
    static Semaphore criancas = new Semaphore(3);
    static Semaphore adultos = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);
    static int cont = 0;

    static public void createChild() throws InterruptedException {

        criancas.acquire();
        mutex.acquire();
        cont++;
        if (cont == 3) {
            adultos.release();
        }
        System.out.println("Crianca Criada");

        mutex.release();

    }

    static public void createAdult() throws InterruptedException {
        adultos.acquire();
        mutex.acquire();
        System.out.println("Adulto Criado");
        if (cont == 3) {
            criancas.release(3);
            cont = 0;
        }
        mutex.release();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    createChild();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                try {
                    createAdult();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

}
