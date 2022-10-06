package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.*;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.concurrent.CompletionStage;

@Value
public class BasicReceiptSlip<A,M> implements ReceiptSlip<A,M> {

    @NonNull AdvancedIO<M> advancedIO;

    @NonNull Instant dispatchingTime;

    @NonNull Instant receptionTime;

    @NonNull Request<A> sentRequest;

    @NonNull A answer;


    @Override
    public @NonNull CompletionStage<DispatchSlip<M>> sendCommand(@NonNull Command command) {
        return advancedIO.sendCommand(command);
    }

    @Override
    public @NonNull <C> CompletionStage<ReceiptSlip<C, M>> sendRequest(@NonNull Request<C> request) {
        return advancedIO.sendRequest(request);
    }
}
