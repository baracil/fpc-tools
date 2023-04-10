package net.femtoparsec.tools.lang;

import fpc.tools.lang.ThreadFactoryBuilder;
import lombok.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class Loopers {

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool(createThreadFactory("looper-%d"));
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1, createThreadFactory("scheduled-looper-%d"));

    private static ThreadFactory createThreadFactory(@NonNull String nameFormat) {
        return new ThreadFactoryBuilder().setDaemon(true).setNameFormat(nameFormat).build();
    }
}
