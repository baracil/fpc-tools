package fpc.tools.advanced.chat;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface AdvancedIO<M> {

    /**
     * Send a command
     * @param command the command to send
     * @return a {@link CompletionStage} that completes when the command is sent
     */
    @NonNull
    CompletionStage<DispatchSlip<M>> sendCommand(@NonNull Command command);

    /**
     * Send a request
     * @param request the request to send
     * @return a {@link CompletionStage} that completes when the answer to the request
     * is received or the request timed out
     */
    @NonNull
    <A> CompletionStage<ReceiptSlip<A,M>> sendRequest(@NonNull Request<A> request);


}
