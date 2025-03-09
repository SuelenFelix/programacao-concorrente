package estudos.exercicios.segundoEstagio;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Generalizacao de Peterson para n threads
public class FilterLock implements Lock {
    private int[] nivel;
    private int[] victim;
    private int n;

    public FilterLock(int n) {
        nivel = new int[n];
        victim = new int[n];
        this.n = n;
        for (int i = 0; i < n; i++) {
            nivel[i] = 0; // garante que todas a threads iniciem no nivel 0
        }
    }

    @Override
    public void lock() {
        int me = (int) Thread.currentThread().getId();
        for (int j = 1; j < n; j++) { // garante que existam n-1 niveis
            nivel[me] = j;
            victim[j] = me;

            for (int k = 0; k < n; k++) {
                /*
                 * verica se existe um k, tal que o nível dele esteja acima ou igual ao nivel de
                 * me, além de garantir que se me for a vitima, ela deve esperar, já que está
                 * cedendo a vez
                 */
                while (k != me && nivel[k] >= nivel[me] && victim[j] == me) {
                    /*
                     * Esse bloco garante que o algortimo respeite os níveis, fazendo com que
                     * threads com nivel mais alto e que não são a vítima executem, caso haja perda
                     * de cpu, o for mais externo garante que haja mudanças de niveis, os aumentando
                     * para threads que já ficaram presas no while por um temp
                     * 
                     */
                }

            }
        }
    }

    @Override
    public void unlock() {
        int me = (int) Thread.currentThread().getId();
        nivel[me] = 0;

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
