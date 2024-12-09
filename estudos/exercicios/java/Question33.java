package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class Question33 {
    static Semaphore sem = new Semaphore(0);
    static Semaphore sem2 = new Semaphore(0);

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
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void threadA() throws InterruptedException {
        System.out.println("a1");
        sem.release();

        sem2.acquire();
        System.out.println("a2");
    }

    public static void threadB() throws InterruptedException {
        sem2.release();
        System.out.println("b1");

        sem.acquire();
        System.out.println("b2");
    }
}
