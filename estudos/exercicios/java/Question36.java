package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class Question36 {
    static int size = 5;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore sem = new Semaphore(0);
    static int cont = 0;

    public static void main(String[] args) {
        Runnable barrier = (() -> {
            try {
                mutex.acquire();
                cont++;
                if (cont == size) {
                    sem.release();
                }
                mutex.release();

                sem.acquire();
                sem.release();
                System.out.println(Thread.currentThread().getName() + " passou pela barreira");

            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        });

        int totalThreads = 100;
        for (int i = 0; i < totalThreads; i++) {
            new Thread(barrier, "Thread-" + (i + 1)).start();
        }

    }

}
