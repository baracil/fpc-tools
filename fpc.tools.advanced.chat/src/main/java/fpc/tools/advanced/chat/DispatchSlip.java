package fpc.tools.advanced.chat;

import lombok.NonNull;

import java.time.Instant;

/**
 * Slip for command sent to the chat
 */
public interface DispatchSlip {

    /**
     * @return the command sent
     */
    Command getSentCommand();

    /**
     * @return the time when the command was sent
     */
    Instant getDispatchingTime();

}
