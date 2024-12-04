import threading

sem = threading.Semaphore(0)
sem2 = threading.Semaphore(0)


def ThreadA():
    print("statement a1");
    sem.release()
    
    sem2.acquire()
    print("statement a2");
    
    
def ThreadB():
    sem.acquire()
    print("statement b1");
    
    sem2.release()
    print("statement b2");
    
threadA = threading.Thread(target=ThreadA)
threadB = threading.Thread(target=ThreadB)

threadA.start()
threadB.start()

threadA.join()
threadB.join()