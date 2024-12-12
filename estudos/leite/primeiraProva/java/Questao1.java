package estudos.leite.primeiraProva.java;

import java.util.concurrent.Semaphore;

public class Questao1 {

    static Semaphore se = new Semaphore(4);
    static Semaphore si = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);
    static int cont = 0;

    public static void external() throws InterruptedException {
        se.acquire();
        mutex.acquire();
        System.out.println("handleExterno");
        cont++;

        if (cont == 4) {
            si.release();
        }
        mutex.release();
    }

    public static void interno() throws InterruptedException {
        si.acquire();
        System.out.println("handleInterno");

        if (cont == 4) {
            se.release(4);
            cont = 0;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                try {
                    interno();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                try {
                    external();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

    }
}