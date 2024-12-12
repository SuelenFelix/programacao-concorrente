package estudos.exercicios.java;
/* Três tipos de threads compartilham acesso a uma lista vinculada individualmente: searchers,
inserters e deleters. Os searchers apenas examinam a lista; portanto, eles podem
executar simultaneamente uns com os outros. Os inserters adicionam novos itens ao final
da lista; as inserções devem ser mutuamente exclusivas para impedir que dois
inserters insiram novos itens quase ao mesmo tempo. No entanto, uma
inserção pode prosseguir em paralelo com qualquer número de pesquisas. Finalmente,
deleters removem itens de qualquer lugar da lista. No máximo um processo de
exclusão pode acessar a lista por vez, e a exclusão também deve ser mutuamente
exclusiva com pesquisas e inserções.
 */

import java.util.concurrent.Semaphore;

public class SearchInsertDeleteProblem {
    static Semaphore leituras = new Semaphore(1);
    static Semaphore escritas = new Semaphore(1);
    static Semaphore block = new Semaphore(1);
    static int cont = 0;

    static public void seach() throws InterruptedException {
        RLLock();
        System.out.println("LEndo");
        RLUnlock();
    }

    static private void RLLock() throws InterruptedException {
        leituras.acquire();
        cont++;
        if (cont == 1) {
            block.acquire();
        }
        leituras.release();
    }

    static private void RLUnlock() throws InterruptedException {
        leituras.acquire();
        cont--;
        if (cont == 0) {
            block.release();
        }
        leituras.release();
    }

    static public void insert() throws InterruptedException {
        System.out.println("inss entrou");
        block.acquire();
        block.release();
        escritas.acquire();
        System.out.println("escreve");
        escritas.release();
    }

    static public void delete() throws InterruptedException {
        System.out.println("del entrou");

        block.acquire();
        System.out.println("deleta");
        block.release();
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                seach();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                delete();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                seach();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                seach();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                delete();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                insert();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                insert();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

    }

}
