package estudos.exercicios.segundoEstagio;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class LockOne implements Lock {

    private boolean[] flag = new boolean[2];

    @Override
    public void lock() {
        int i = (int) Thread.currentThread().getId();
        int j = 1 - i;
        flag[i] = true;
        while (flag[j]) { // garante que a outra está false para poder acessar a RC

            // Apesar disso, locKOne não funciona com concorrencia, já que se chegar duas
            // threads, t0 e t1, com t0 executando
            // até a linha 15 e depois perdendo a cpu e com t1 continuando o progresso,
            // teremos no while:
            // flag[0] = true e flag[1] = true, causando livelock

        }
    }

    @Override
    public void unlock() {
        int i = (int) Thread.currentThread().getId();
        flag[i] = false;
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
