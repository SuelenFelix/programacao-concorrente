package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class Question {

    static int contC = 0;
    static int contA = 0;
    static int waiting = 0;
    static int leaving = 0;

    static Semaphore adultos = new Semaphore(0);
    static Semaphore criancas = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);

    static public void adult() throws InterruptedException {
        mutex.acquire();
        contA++;
        System.out.println("Adult entered. Adults: " + contA);

        if (waiting > 0) {
            int n = Math.min(waiting, 3);
            criancas.release(n); // 1
            waiting -= n; // 0
            contC += n;// 2
        }
        mutex.release();
        System.out.println("Adultooo");

        mutex.acquire();
        if (contC <= 3 * (contA - 1)) {
            contA--;
            mutex.release();
        } else {
            leaving++;
            mutex.release();
            adultos.acquire();
        }
    }

    static public void child() throws InterruptedException {
        mutex.acquire();

        if (contC < 3 * contA) {
            contC++;
            mutex.release();
        } else {
            waiting++;
            mutex.release();
            criancas.acquire();
        }
        System.out.println("criancaaa");

        mutex.acquire();
        contC--;
        if (contC < 3 * (contA - 1) && leaving < 3 * (contA - 1)) {
            leaving--;
            contA--;
            adultos.release();
        }
        mutex.release();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    adult();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    child();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

    }
}
