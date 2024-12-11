package estudos.leite.primeiraProva.java;

import java.util.concurrent.Semaphore;

public class Questao3 {

    static Semaphore lock = new Semaphore(1);
    static Semaphore mutex = new Semaphore(1);
    static Semaphore blockRead = new Semaphore(1);
    static int cont = 0;

    static public void read() throws InterruptedException {
        mutex.acquire();
        cont++;
        if (cont == 1) {
            lock.acquire();
        }
        System.out.println("lendo");
        mutex.release();

        mutex.acquire();
        cont--;
        if (cont == 0) {
            lock.release();
        }
        mutex.release();
    }

    static public void write() {
        System.out.println("escrevendo");
    }

    static public void wrap_read() throws InterruptedException {
        blockRead.acquire();
        blockRead.release();
        read();
    }

    static public void wrap_write() throws InterruptedException {
        blockRead.acquire();
        lock.acquire();
        write();
        lock.release();
        blockRead.release();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    wrap_read();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    wrap_write();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    wrap_read();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

    }

}
