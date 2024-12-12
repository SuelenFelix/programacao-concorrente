package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class SushiBar {
    static int eating = 0;
    static int waiting = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore block = new Semaphore(0);
    static boolean must_wait = false;

    static public void chegada() throws InterruptedException {
        System.out.println("alguem entrou");

        mutex.acquire();
        if (must_wait) {
            waiting++;
            mutex.release();
            block.acquire();
            System.out.println("alguem espera");

        } else {
            eating++;
            must_wait = (eating == 5);
            mutex.release();

        }
    }

    static public void saida() throws InterruptedException {
        mutex.acquire();
        eating--;
        if (eating == 0) {
            int n = Math.min(5, waiting);
            waiting -= n;
            eating += n;
            must_wait = (eating == 5);
            block.release(n);
            System.out.println("alguem saiu");

        }
        mutex.release();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    chegada();
                    System.out.println("Chegada: " + Thread.currentThread().getName());
                    Thread.sleep(100);
                    saida();
                    System.out.println("Saída: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
