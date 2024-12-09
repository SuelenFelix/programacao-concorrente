package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class Question35 {
    static int n = 5;
    static int cont = 0;
    static Semaphore sem = new Semaphore(n);

    public static void main(String[] args) {
        Runnable a = (() -> {
            try {
                threadA();
                System.out.println(Thread.currentThread().getName() + " passou aqui");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        for (int i = 0; i < 10; i++) {
            new Thread(a, "Thread " + (i + 1)).start();

        }

        System.out.println(cont);

    }

    public static void threadA() throws InterruptedException {
        sem.acquire();
        cont++;
        sem.release();
    }
}
