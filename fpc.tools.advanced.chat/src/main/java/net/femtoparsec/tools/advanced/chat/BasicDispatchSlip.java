package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.advanced.chat.Command;
import fpc.tools.advanced.chat.DispatchSlip;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Delegate;

import java.time.Instant;

@Value
public class BasicDispatchSlip<M> implements DispatchSlip<M> {

    @Delegate
    @NonNull AdvancedIO<M> advancedIO;

    @NonNull Instant dispatchingTime;

    @NonNull Command sentCommand;

}
