package ch.vfl.jtris.util;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// A channel can 'transport' objects in a thread-safe manner by sending and receiving them.
public class Channel<T> {

    private ArrayList<T> query;
    private ReentrantLock lock;
    private Condition hasElements;

    public Channel() {
        query = new ArrayList<>();
        lock = new ReentrantLock();
        hasElements = lock.newCondition();
    }

    // send sends an object to the queue.
    public void send(T object) {
        lock.lock();
        query.add(object);

        // if it was the first element to get added, signal that
        if (query.size() == 1) {
            hasElements.signal();
        }

        lock.unlock();
    }

    // awaitReceive receives the oldest element in the queue, and awaits
    // an element if there is none.
    public T awaitReceive() throws InterruptedException {
        lock.lock();

        if (query.size() == 0) {
            hasElements.await();
        }
        T result;
        result = query.remove(0);

        lock.unlock();
        return result;
    }
}
