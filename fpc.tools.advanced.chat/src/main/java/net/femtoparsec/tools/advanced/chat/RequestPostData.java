package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.advanced.chat.Request;
import fpc.tools.advanced.chat.RequestAnswerMatcher;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * @author Bastien Aracil
 **/
public class RequestPostData<A, M> extends AbstractPostData<ReceiptSlip<A>, Request<A>, M> {

    private final RequestAnswerMatcher<M> matcher;

    private Instant dispatchingTime = null;

    public RequestPostData(Request<A> request, RequestAnswerMatcher<M> matcher) {
        super(request);
        this.matcher = matcher;
    }

    @Override
    public Optional<RequestPostData<?, M>> asRequestPostData() {
        return Optional.of(this);
    }

    @Override
    public void onMessagePosted(Instant dispatchingTime) {
        this.dispatchingTime = dispatchingTime;
    }

    public void onRequestTimeout(Duration duration) {
        completeExceptionallyWith(new TimeoutException("Timeout after " + duration));
    }

    public boolean tryToCompleteWith(M incomingMessage, Instant receptionTime) {
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

    private BasicReceiptSlip<A> buildReceiptSlip(A answer, Instant receptionTime) {
        return new BasicReceiptSlip<>(dispatchingTime, receptionTime, message(), answer);
    }

}
