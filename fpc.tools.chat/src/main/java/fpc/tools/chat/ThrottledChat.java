package fpc.tools.chat;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.TokensInheritanceStrategy;
import io.github.bucket4j.local.LocalBucket;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ThrottledChat implements Chat {

    public static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(0, new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Bucket-%d").build());

    @NonNull
    private final Chat delegate;

    @NonNull
    private final LocalBucket bucket;

    private final AtomicReference<BandwidthLimits> bandwidthLimits;

    public ThrottledChat(@NonNull Chat delegate, @NonNull BandwidthLimits bandwidthLimits) {
        this.delegate = delegate;
        this.bandwidthLimits = new AtomicReference<>(bandwidthLimits);
        this.bucket = this.bandwidthLimits.get().addLimits(Bucket.builder()).build();
    }

    @Synchronized
    public void updateBandwidthType(@NonNull BandwidthLimits bandwidthType) {
        final var oldType = this.bandwidthLimits.getAndSet(bandwidthType);
        if (oldType == bandwidthType) {
            return;
        }
        bucket.replaceConfiguration(
                bandwidthType.addLimits(BucketConfiguration.builder()).build(),
                TokensInheritanceStrategy.AS_IS
        );
    }

    @Override
    public void connect() {
        delegate.connect();
    }

    @Override
    public boolean isRunning() {
        return delegate.isRunning();
    }

    @Override
    public void requestDisconnection() {
        delegate.requestDisconnection();
    }

    @Override
    public void postMessage(@NonNull String message) {
        try {
            bucket.asScheduler().consume(1, EXECUTOR_SERVICE).get();
        } catch (Exception e) {
            bucket.addTokens(1);
            ThrowableTool.interruptIfCausedByInterruption(e);
            throw new MessagePostingFailure("Post has failed du to bucket error", e);
        }

        delegate.postMessage(message);
        LOG.debug("Throttling : {} tokens left", bucket.getAvailableTokens());
    }

    @Override
    public @NonNull Subscription addChatListener(@NonNull ChatListener listener) {
        return delegate.addChatListener(listener);
    }
}
