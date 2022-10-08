package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.advanced.chat.Request;
import fpc.tools.advanced.chat.RequestAnswerMatcher;
import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * @author Bastien Aracil
 **/
public class RequestPostData<A, M> extends AbstractPostData<ReceiptSlip<A>, Request<A>, M> {

    @NonNull
    private final RequestAnswerMatcher<M> matcher;

    private Instant dispatchingTime = null;

    public RequestPostData(@NonNull Request<A> request, @NonNull RequestAnswerMatcher<M> matcher) {
        super(request);
        this.matcher = matcher;
    }

    @Override
    public @NonNull Optional<RequestPostData<?, M>> asRequestPostData() {
        return Optional.of(this);
    }

    @Override
    public void onMessagePosted(@NonNull Instant dispatchingTime) {
        this.dispatchingTime = dispatchingTime;
    }

    public void onRequestTimeout(@NonNull Duration duration) {
        completeExceptionallyWith(new TimeoutException("Timeout after " + duration));
    }

    public boolean tryToCompleteWith(@NonNull M incomingMessage, @NonNull Instant receptionTime) {
        try {
            final var match = matcher.performMatch(message(), incomingMessage).orElse(null);
            if (match != null) {
                final var map = match.map(a -> buildReceiptSlip(a, receptionTime));
                map.accept(this::completeExceptionallyWith, this::completeWith);
            }
            return match != null;
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }

    @NonNull
    private BasicReceiptSlip<A> buildReceiptSlip(@NonNull A answer, @NonNull Instant receptionTime) {
        return new BasicReceiptSlip<>(dispatchingTime, receptionTime, message(), answer);
    }

}
