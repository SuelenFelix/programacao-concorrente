package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class Question41 {
    static int bufferSize = 3;
    static Semaphore produtor = new Semaphore(bufferSize);
    static Semaphore consumidor = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);

    static public void produz() throws InterruptedException {
        produtor.acquire();
        mutex.acquire();
        System.out.println("produzi");
        mutex.release();
        consumidor.release();

    }

    static public void consome() throws InterruptedException {
        consumidor.acquire();
        mutex.acquire();
        System.out.println("comi");
        mutex.release();
        produtor.release();

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    produz();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    consome();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
