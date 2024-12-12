package estudos.exercicios.java;

import java.util.concurrent.Semaphore;

/*There cannot be men and women in the bathroom at the same
time.
There should never be more than three employees squandering
company
time in the bathroom */

public class UnisexBathroom {
    static Semaphore capacity = new Semaphore(3);
    static int contM = 0;
    static int contW = 0;

    static Semaphore mutex = new Semaphore(1);
    static Semaphore empty = new Semaphore(1);

    static public void femNoBanheiro() throws InterruptedException {
        RLLockW();
        System.out.println("Há mulher(es) no banheiro");
        RLUnlockW();

    }

    static public void masNoBanheiro() throws InterruptedException {
        RLLockM();
        System.out.println("Há rapaz(es) no banheiro");
        RLUnlockM();

    }

    static private void RLLockW() throws InterruptedException {
        capacity.acquire();

        mutex.acquire();
        contW++;
        if (contW == 1) {
            empty.acquire();
        }
        mutex.release();

    }

    static private void RLUnlockW() throws InterruptedException {
        capacity.release();

        mutex.acquire();
        contW--;
        if (contW == 0) {
            empty.release();
        }
        mutex.release();
    }

    static private void RLLockM() throws InterruptedException {
        capacity.acquire();

        mutex.acquire();
        contM++;
        if (contM == 1) {
            empty.acquire();
        }
        mutex.release();

    }

    static private void RLUnlockM() throws InterruptedException {
        capacity.release();

        mutex.acquire();
        contM--;
        if (contM == 0) {
            empty.release();
        }
        mutex.release();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            new Thread(() -> {
                try {
                    masNoBanheiro();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        for (int i = 0; i < 9; i++) {
            new Thread(() -> {
                try {
                    femNoBanheiro();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
