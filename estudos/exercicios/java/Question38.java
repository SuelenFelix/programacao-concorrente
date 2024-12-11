package estudos.exercicios.java;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Question38 {

    static Semaphore seg = new Semaphore(0);
    static Semaphore lid = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);
    static Semaphore rendevouz = new Semaphore(0);
    static int contL = 0;
    static int contS = 0;

    static public void lideres() throws InterruptedException {
        mutex.acquire();
        if (contS > 0) {
            contS--;
            seg.release();
        } else {
            contL++;
            mutex.release();
            lid.acquire();
        }

        System.out.println("dança");
        mutex.release();
    }

    static public void seguidores() throws InterruptedException {
        mutex.acquire();
        if (contL > 0) {
            contL--;
            lid.release();
        } else {
            contS++;
            mutex.release();
            seg.acquire();
        }

        mutex.release();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    lideres();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    seguidores();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

    }

}
