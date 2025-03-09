package estudos.exercicios.segundoEstagio;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class AtomicBakery implements Lock {

    private int[] tickets;
    private AtomicInteger currentTicket;
    private int n;

    public AtomicBakery(int n) {
        this.tickets = new int[n];
        this.currentTicket = new AtomicInteger(0); // O uso de variaveis atomicas evita condicoes de corrida
        this.n = n;

        for (int i = 0; i < n; i++) {
            tickets[i] = 0; // inicializa todos os tickets com valor 0
        }
    }

    @Override
    public void lock() {
        int me = (int) Thread.currentThread().getId();
        tickets[me] = currentTicket.incrementAndGet();

        for (int i = 0; i < tickets.length; i++) {
            // Não é preciso mais o uso de choosing já que agora a operacao é atômica e
            // incrementa e retorna o valor com uma instrução de máquina
            while (tickets[i] != 0 && tickets[i] < tickets[me]) {

                /*
                 * Como agora não há mais a possibilidade de empate, não há mais a necessidade
                 * de fazer i < me, mantendo apenas o critério de que tickets com valor menor
                 * devem ser executados antes
                 */

            }

        }
    }

    @Override
    public void unlock() {
        int me = (int) Thread.currentThread().getId();
        tickets[me] = 0;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lockInterruptibly'");
    }

    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newCondition'");
    }

    @Override
    public boolean tryLock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tryLock'");
    }

    @Override
    public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tryLock'");
    }

}
