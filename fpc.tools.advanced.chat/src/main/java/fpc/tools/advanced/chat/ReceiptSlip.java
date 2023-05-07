package fpc.tools.advanced.chat;

import lombok.NonNull;

import java.time.Instant;

/**
 * The slip for the request sent to the chat
 * @param <A>
 */
public interface ReceiptSlip<A>{

    /**
     * @return the instant when the request was sent
     */
    Instant getDispatchingTime();

    /**
     * @return the instant when the answer of the request was received
     */
    Instant getReceptionTime();

    /**
     * @return the request sent
     */
    Request<A> getSentRequest();

    /**
     * @return the received answer
     */
    A getAnswer();
}
