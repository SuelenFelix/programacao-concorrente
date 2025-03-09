package estudos.exercicios.segundoEstagio;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Bakery implements Lock {

    private int[] tickets;
    private boolean[] choosing;
    private int n;

    public Bakery(int n) {
        this.tickets = new int[n];
        this.choosing = new boolean[n];
        this.n = n;

        for (int i = 0; i < n; i++) {
            tickets[i] = 0; // inicializa todos os tickets com valor 0
        }
    }

    @Override
    public void lock() {
        int me = (int) Thread.currentThread().getId();
        choosing[me] = true;
        tickets[me] = findMax(tickets) + 1;
        choosing[me] = false;

        for (int i = 0; i < n; i++) {
            while (choosing[i]) {
                /*
                 * Esse bloco garante que caso uma thread com valor de ticket menor perca a cpu
                 * antes de setar choosing para false, a thread com valor de ticket maior que
                 * conseguiu avançar não possa passar desse ponto, ficando bloqueada
                 */
            }
            while (tickets[i] != 0 && (tickets[i] < tickets[me] || i < me)) {
                /*
                 * Esse bloco garante que tickets com valor menor tenham prioridade e caso haja
                 * empate, considera a thread que tem o indíce menor
                 * 
                 * Exemplo: t5 com id 5 e t1 com id 1 chegaram juntas e ambas pegam o valor de
                 * ticket igual a 1, considerando que t5 chegou ao while antes e me atualmente
                 * se refere à ela. Com isso, dentro do for, ao percorrer todas as threds
                 * veremos que terá um i(1) menor que me(5), fazendo com que t5 fique bloqueada
                 * até que t1 execute e saia da RC
                 */

            }
        }
    }

    @Override
    public void unlock() {
        int me = (int) Thread.currentThread().getId();
        tickets[me] = 0;

    }

    /*
     * Method that finds the max value inside the ticket array.
     */
    private int findMax(int[] ticket) {

        int m = tickets[0];

        for (int i = 1; i < tickets.length; i++) {
            if (tickets[i] > m)
                m = tickets[i];
        }
        return m;
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
