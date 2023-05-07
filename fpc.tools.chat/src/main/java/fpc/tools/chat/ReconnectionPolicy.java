package fpc.tools.chat;

import lombok.NonNull;

import java.time.Duration;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

/**
 * @author Bastien Aracil
 **/
public interface ReconnectionPolicy {

    /**
     * @param nbAttemptsSoFar the number of attempts made before calling this method (starts from 0 during a reconnection process)
     * @return true if the reconnection should be attempted again
     */
    boolean shouldReconnect(int nbAttemptsSoFar);

    /**
     * @param nextAttemptIndex the next attempts index (starts from 1 during a reconnection process)
     * @return the delay before trying a connection
     */
    Duration delayBeforeNextAttempt(int nextAttemptIndex);

    ReconnectionPolicy NO_RECONNECTION = with(i -> false, i -> Duration.ZERO);

    static ReconnectionPolicy with(IntPredicate shouldReconnectPredicate, IntFunction<Duration> durationProvider) {
        return new ReconnectionPolicy() {
            @Override
            public boolean shouldReconnect(int nbAttemptsSoFar) {
                return shouldReconnectPredicate.test(nbAttemptsSoFar);
            }

            @Override
            public Duration delayBeforeNextAttempt(int nextAttemptIndex) {
                return durationProvider.apply(nextAttemptIndex);
            }
        };
    }

    static ReconnectionPolicy withMaximalNumberOfAttempts(int maxAttempts, IntFunction<Duration> durationGetter) {
        return with(i -> i<maxAttempts, durationGetter);
    }

    static ReconnectionPolicy withMaximalNumberOfAttemptsAndFixDelay(int maxAttempts, Duration fixDuration) {
        return with(i -> i<maxAttempts, i -> fixDuration);
    }
}
