package fpc.tools.advanced.chat;

import lombok.NonNull;

import java.time.Instant;

/**
 * @author Bastien Aracil
 **/
public interface Message {

    String payload(Instant dispatchInstant);
}
