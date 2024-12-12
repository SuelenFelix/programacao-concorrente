package estudos.leite.primeiraProva.java;

import java.util.concurrent.Semaphore;

/* Questao 1 do periodo 23.2 */
public class Questao4 {

    static int n = 2;
    static int cont = 0;
    static Semaphore s = new Semaphore(1);
    static Semaphore mult = new Semaphore(n);
    static Semaphore mutex = new Semaphore(n);

    static public void lock() throws InterruptedException {
        mutex.acquire();
        cont++;
        if (cont == 1) {
            s.acquire();
        }
        mult.acquire();
        mutex.release();
    }

    static public void unlock() throws InterruptedException {
        mutex.acquire();
        cont--;
        if (cont == 0) {
            s.release();
        }
        mult.release();
        mutex.release();
    }

    static public void handleGet() throws InterruptedException {
        System.out.println("read chegou");

        lock();
        System.out.println("LENDOOOO");
        Thread.sleep(2000);

        unlock();

    }

    static public void handlePost() throws InterruptedException {
        System.out.println("POSTYT chegou");

        lock();
        mult.acquire();
        System.out.println("POSTYT");
        unlock();
        mult.release();
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                handleGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                handlePost();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                handleGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

    }

}
