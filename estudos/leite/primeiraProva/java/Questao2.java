package estudos.leite.primeiraProva.java;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Questao2 {
    static int capacity;
    private final Queue<Integer> queue;
    static Semaphore SR = new Semaphore(0);
    static Semaphore SA = new Semaphore(capacity);
    static Semaphore mutex = new Semaphore(1);

    public Questao2(int n) {
        capacity = n;
        this.queue = new LinkedList<>();
        SA = new Semaphore(capacity - 1);
    }

    // remove um item do buffer
    Integer dequeue() throws InterruptedException {
        // This pool method returns the first element of this list, or null if this list
        // is
        SR.acquire();
        mutex.acquire();
        Integer item = queue.poll();
        mutex.release();
        SA.release();
        return item;
    }

    // adiciona um novo item no buffer
    void enqueue(Integer item) throws InterruptedException {
        // This add method appends the specified element to the end of this list.
        SA.acquire();
        mutex.acquire();
        queue.add(item);
        mutex.release();
        SR.release();

    }

    public static void main(String[] args) {
        Questao2 buffer = new Questao2(2);

        // Teste com várias threads produtoras e consumidoras
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 10; i++) { // Produz 10 itens
                    buffer.enqueue(i);
                    System.out.println("Produziu: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumer = () -> {
            try {
                for (int i = 0; i < 10; i++) { // Consome 10 itens
                    Integer item = buffer.dequeue();
                    System.out.println("Consumiu: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}