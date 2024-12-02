import threading

n = 3
sem = threading.Semaphore(n)

count = 0


def ThreadA():
    global count
    sem.acquire()
    count += 1
    print(count);
    sem.release()
    
    
threads = []
for _ in range(10):
    thread = threading.Thread(target=ThreadA)
    threads.append(thread)
    thread.start()

for thread in threads:
    thread.join()
    
print("Final count:", count)
