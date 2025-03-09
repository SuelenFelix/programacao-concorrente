package estudos.exercicios.segundoEstagio;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class LockTwo implements Lock {

    private volatile int victim; // thread que vai dá a vez para a outra

    @Override
    public void lock() {
        int i = (int) Thread.currentThread().getId();
        victim = i;
        while (victim == i) { // como victim é volatile, as threads vão ter acesso imediato à qualquer
                              // alteração dela. esse bloco garante que a primeira a chegar vai ceder sua vez
                              // para a próxima

            // Esse algoritmo tem liveLock quando não há concorrência já que uma thread vai
            // pode esperar por outra que talvez nem chegue

        }
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

    @Override
    public void unlock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unlock'");
    }

}
