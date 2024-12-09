package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class Question34 {
    static int cont = 0;
    static Semaphore sem = new Semaphore(1);

    public static void main(String[] args) {
        Runnable a = (() -> {
            try {
                threadA();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Runnable b = (() -> {
            try {
                threadB();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadA = new Thread(a);
        Thread threadB = new Thread(b);

        threadB.start();
        threadA.start();

        try {
            threadA.join();
            threadB.join();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(cont);

    }

    public static void threadA() throws InterruptedException {
        sem.acquire();
        cont++;
        sem.release();

    }

    public static void threadB() throws InterruptedException {
        sem.acquire();
        cont++;
        sem.release();
    }
}
