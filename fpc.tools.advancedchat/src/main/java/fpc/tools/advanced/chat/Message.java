package fpc.tools.advanced.chat;

import lombok.NonNull;

import java.time.Instant;

/**
 * @author Bastien Aracil
 **/
public interface Message {

    @NonNull
    String payload(@NonNull Instant dispatchInstant);
}
