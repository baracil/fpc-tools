package fpc.tools.advanced.chat;

import lombok.NonNull;

import java.time.Instant;

/**
 * The slip for the request sent to the chat
 * @param <A>
 */
public interface ReceiptSlip<A> {

    /**
     * @return the instant when the request was sent
     */
    @NonNull
    Instant getDispatchingTime();

    /**
     * @return the instant when the answer of the request was received
     */
    @NonNull
    Instant getReceptionTime();

    /**
     * @return the request sent
     */
    @NonNull
    Request<A> getSentRequest();

    /**
     * @return the received answer
     */
    @NonNull
    A getAnswer();
}
