import threading

n = 3
sem = threading.Semaphore(1)
barrier = threading.Semaphore(0)


count = 0


def ThreadA():
    global count
    print("statement a1")
    sem.acquire()
    count += 1
    print(count)

    if (n == count):
        barrier.release()

    sem.release()
    barrier.acquire()
    barrier.release()


threads = []
for _ in range(10):
    thread = threading.Thread(target=ThreadA)
    threads.append(thread)
    thread.start()

for thread in threads:
    thread.join()

print("count final:", count)
