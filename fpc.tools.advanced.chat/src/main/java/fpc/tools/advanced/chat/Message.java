package fpc.tools.advanced.chat;

import java.time.Instant;

/**
 * @author Bastien Aracil
 **/
public interface Message {

    String payload(Instant dispatchInstant);
}
