package de.fb.adc_monitor.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.collections4.queue.CircularFifoQueue;

/**
 * A simple wrapper for CircularFifoQueue that maintains a single "fat" read/write lock to synchronize access to most
 * of the basic methods except iterators.
 * 
 * @author Ibragim Kuliev
 *
 * @param <E>
 */
public class ConcurrentCircularFifoQueue<E> implements Queue<E> {

    private CircularFifoQueue<E> queue;
    private ReentrantReadWriteLock lock;

    public ConcurrentCircularFifoQueue(final CircularFifoQueue<E> queue) {
        this.queue = queue;
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public int size() {
        try {
            lock.readLock().lock();
            return queue.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            lock.readLock().lock();
            return queue.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean contains(final Object o) {
        try {
            lock.readLock().lock();
            return queue.contains(o);
        } finally {
            lock.readLock().unlock();
        }
    }

    // WARNING: not properly synchronized yet!
    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public Object[] toArray() {
        try {
            lock.readLock().lock();
            return queue.toArray();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        try {
            lock.readLock().lock();
            return queue.toArray(a);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean remove(final Object o) {
        try {
            lock.writeLock().lock();
            return queue.remove(o);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        try {
            lock.readLock().lock();
            return queue.contains(c);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        try {
            lock.writeLock().lock();
            return queue.addAll(c);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        try {
            lock.writeLock().lock();
            return queue.removeAll(c);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        try {
            lock.writeLock().lock();
            return queue.retainAll(c);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void clear() {
        try {
            lock.writeLock().lock();
            queue.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean add(final E e) {
        try {
            lock.writeLock().lock();
            return queue.add(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean offer(final E e) {
        try {
            lock.writeLock().lock();
            return queue.offer(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public E remove() {
        try {
            lock.writeLock().lock();
            return queue.remove();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public E poll() {
        try {
            lock.writeLock().lock();
            return queue.poll();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public E element() {
        try {
            lock.readLock().lock();
            return queue.element();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public E peek() {
        try {
            lock.readLock().lock();
            return queue.peek();
        } finally {
            lock.readLock().unlock();
        }
    }
}
