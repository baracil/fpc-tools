package fpc.tools.advanced.chat;

import lombok.NonNull;

import java.time.Instant;

/**
 * Slip for command sent to the chat
 */
public interface DispatchSlip<M> extends AdvancedIO<M> {

    /**
     * @return the command sent
     */
    @NonNull
    Command getSentCommand();

    /**
     * @return the time when the command was sent
     */
    @NonNull
    Instant getDispatchingTime();

}