package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.Command;
import fpc.tools.advanced.chat.DispatchSlip;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class BasicDispatchSlip implements DispatchSlip {

    @NonNull Instant dispatchingTime;

    @NonNull Command sentCommand;

}
