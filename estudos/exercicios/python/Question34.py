import threading

sem = threading.Semaphore(1)

count = 0


def ThreadA():
    global count
    sem.acquire()
    count = count + 1
    print(count)
    sem.release()


threads = []
for _ in range(10):
    thread = threading.Thread(target=ThreadA)
    threads.append(thread)
    thread.start()

for thread in threads:
    thread.join()

print("Final count:", count)
