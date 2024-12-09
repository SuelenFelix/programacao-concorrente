package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class BarreiraReutilizavel {
    static int size = 5;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore sem = new Semaphore(0);
    static Semaphore reset = new Semaphore(0);

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

                mutex.acquire();
                cont--;

                if (cont == 0) {
                    reset.release();
                    sem.acquire();
                }
                mutex.release();
                reset.acquire();
                reset.release();

            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        });

        for (int i = 0; i < 100; i++) {
            new Thread(barrier, "Thread-" + (i + 1)).start();
        }

    }

}
