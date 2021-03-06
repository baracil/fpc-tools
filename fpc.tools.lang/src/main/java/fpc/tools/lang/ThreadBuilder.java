package fpc.tools.lang;

import lombok.NonNull;

import java.util.Optional;

/**
 * @author Bastien Aracil
 */
public class ThreadBuilder {

    public static ThreadBuilder builder(Runnable runnable) {
        return new ThreadBuilder(runnable);
    }

    public static ThreadBuilder builder() {
        return new ThreadBuilder();
    }

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private Runnable runnable = null;
    private String name = null;
    private Boolean daemon = null;
    private Integer priority = null;

    public ThreadBuilder(@NonNull Runnable runnable) {
        this.runnable = runnable;
    }

    public ThreadBuilder() {
    }

    public Thread build() {
        final Thread thread = Optional.ofNullable(runnable).map(Thread::new).orElseGet(Thread::new);
        Optional.ofNullable(name).ifPresent(thread::setName);
        Optional.ofNullable(daemon).ifPresent(thread::setDaemon);
        Optional.ofNullable(priority).ifPresent(thread::setPriority);
        Optional.ofNullable(uncaughtExceptionHandler).ifPresent(thread::setUncaughtExceptionHandler);
        return thread;
    }

    public ThreadBuilder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    public ThreadBuilder runnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public ThreadBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ThreadBuilder daemon(Boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadBuilder priority(Integer priority) {
        this.priority = priority;
        return this;
    }

}
