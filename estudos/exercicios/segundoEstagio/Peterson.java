package estudos.exercicios.segundoEstagio;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Peterson implements Lock {

    private volatile boolean[] flag = new boolean[2];
    private volatile int victim;

    @Override
    public void lock() {
        int i = (int) Thread.currentThread().getId();
        int j = 1 - i;
        flag[i] = true;
        victim = i;

        while (flag[j] && victim == i) { // Esse bloco garante que a thread ceda para a outra e que a outra esteja
                                         // interessada em entrar na RC

            // Esse algoritmo resolve os problemas de LockOne e LockTwo

            /*
             * Exemplos de execucao:
             * sem concorrencia:
             * 
             * t0 chega, j = 1, flag = [t, f], vic = 0
             * ela passa para RC já que flag[1] == false
             * 
             * t1 chega, j = 0, flag = [t, t], vic = 0
             * trava no while já que flag[0] == true e vic == i
             * 
             * 
             * com concorrencia
             * 
             * t0 e t1 chegam, para t0 -> j = 0, flag[t, ?] e vic = 0
             * para t1 -> j = 1, flag[?, t] e vic = 1
             * 
             * Se t0 chegar ao while antes e tiver setado vic antes, agora vic será = 1
             * então flag[1] = true e vic != 0, logo vai para RC
             * 
             * Se t0 chegar ao while antes e tiver setado vic depois, agora vic será = 0
             * então flag[1] = true e vic == 0, logo fica bloqueada e t1 irá seguir
             * 
             * Se t1 chegar ao while antes e tiver setado vic antes, agora vic será = 0
             * então flag[0] = true e vic != 1, logo vai para RC
             * 
             * Se t1 chegar ao while antes e tiver setado vic depois, agora vic será = 1
             * então flag[0] = true e vic == 1, logo fica bloqueada e t0 seegue
             */

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
