import threading

n = 3
sem = threading.Semaphore(1)
barrier = threading.Semaphore(0)
resetbarrier = threading.Semaphore(0)

count = 0

def ThreadA():
    global count
    print("statement a1");
    sem.acquire()
    count += 1
    
    if(n == count): 
        barrier.release()
    sem.release()

    barrier.acquire()
    barrier.release()

    sem.acquire()
    count -= 1
    
    if(count == 0):
        resetbarrier.release()
        barrier.acquire()
        
    sem.release()        
    resetbarrier.acquire()
    resetbarrier.release()
    
threads = []
for _ in range(10):
    thread = threading.Thread(target=ThreadA)
    threads.append(thread)
    thread.start()
    print(thread.name)

    
for thread in threads:
    thread.join()
    
print("count final:", count)