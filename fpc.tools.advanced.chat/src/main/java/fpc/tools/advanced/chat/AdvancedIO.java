package fpc.tools.advanced.chat;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface AdvancedIO {

    /**
     * Send a command
     * @param command the command to send
     * @return a {@link CompletionStage} that completes when the command is sent
     */
    @NonNull
    CompletionStage<DispatchSlip> sendCommand(@NonNull Command command);

    /**
     * Send a request
     * @param request the request to send
     * @return a {@link CompletionStage} that completes when the answer to the request
     * is received or the request timed out
     */
    @NonNull
    <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull Request<A> request);


}
