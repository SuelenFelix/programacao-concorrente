package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

public class Question42 {
    static Semaphore mutex = new Semaphore(1);
    static Semaphore empty = new Semaphore(1);
    static int contR = 0;

    static public void read() throws InterruptedException {
        mutex.acquire();
        contR++;
        if (contR == 1) {
            empty.acquire();
        }
        mutex.release();
        System.out.println("lendo");

        mutex.release();
        contR--;
        if (contR == 0) {
            empty.release();
        }
        mutex.release();
    }

    static public void write() throws InterruptedException {
        empty.acquire();

        System.out.println("escreve");

        empty.release();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    write();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    read();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
