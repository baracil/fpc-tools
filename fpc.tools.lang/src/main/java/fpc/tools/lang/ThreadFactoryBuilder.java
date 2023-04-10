package fpc.tools.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadFactoryBuilder {

  private String nameFormat;
  private Boolean daemon;
  private Integer priority;

  public ThreadFactory build() {
    return new Factory(nameFormat,daemon,priority);
  }

  public ThreadFactoryBuilder setDaemon(Boolean daemon) {
    this.daemon = daemon;
    return this;
  }

  public ThreadFactoryBuilder setPriority(Integer priority) {
    this.priority = priority;
    return this;
  }

  public ThreadFactoryBuilder setNameFormat(String nameFormat) {
    final var test = nameFormat.formatted(0L);
    this.nameFormat = nameFormat;
    return this;
  }

  @RequiredArgsConstructor
  private class Factory implements ThreadFactory {

    private final AtomicLong id = new AtomicLong();

    private final String nameFormat;
    private final Boolean daemon;
    private final Integer priority;

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
      final var thread = new Thread(runnable);
      Optional.ofNullable(priority).ifPresent(thread::setPriority);
      Optional.ofNullable(nameFormat).map(n -> n.formatted(id.incrementAndGet())).ifPresent(thread::setName);
      Optional.ofNullable(daemon).ifPresent(thread::setDaemon);

      return thread;
    }
  }
}
