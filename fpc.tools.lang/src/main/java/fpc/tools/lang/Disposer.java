package fpc.tools.lang;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Can be used to launch an action when a reference is garbage collected.
 *
 */
@Slf4j
public class Disposer {

    public static final Runnable NOPE = () -> {};

    private final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

    private final Map<Reference<?>, Runnable> actionOnDispose = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Thread thread;

    public Disposer(String name) {
        this.thread = new Thread(this::loop, "Disposer '"+name+"'");
        thread.setDaemon(true);
        thread.start();
    }

    public <S> @NonNull Reference<S> add(@NonNull S value, @NonNull Runnable actionOnDispose) {
        final WeakReference<S> reference = new WeakReference<>(value,referenceQueue);
        saveAction(reference,actionOnDispose);
        return reference;
    }


    public void stop() {
        lock.writeLock().lock();
        try {
            thread.interrupt();
            actionOnDispose.values().forEach(this::executeAction);
            actionOnDispose.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }


    private void loop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                final Reference<?> reference = referenceQueue.remove();
                executeAction(extractAction(reference));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void executeAction(Runnable extractAction) {
        try {
            extractAction.run();
        } catch (Exception e) {
            ThrowableTool.interruptIfCausedByInterruption(e);
            LOG.warn("Error while disposing an object ",e);
        }
    }

    private void saveAction(@NonNull Reference<?> reference, @NonNull Runnable runnable) {
        lock.writeLock().lock();
        try {
            if (thread.isInterrupted()) {
                throw new IllegalStateException("Disposer is interrupted. Cannot add any more objects");
            }
            actionOnDispose.put(reference,runnable);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @NonNull
    private Runnable extractAction(@NonNull Reference<?> reference) {
        lock.readLock().lock();
        try {
            return actionOnDispose.getOrDefault(reference,NOPE);
        } finally {
            lock.readLock().unlock();
        }
    }

}
