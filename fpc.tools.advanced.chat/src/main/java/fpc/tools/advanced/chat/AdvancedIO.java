package fpc.tools.advanced.chat;

import java.util.concurrent.CompletionStage;

public interface AdvancedIO {

    /**
     * Send a command
     * @param command the command to send
     * @return a {@link CompletionStage} that completes when the command is sent
     */
    CompletionStage<DispatchSlip> sendCommand(Command command);

    /**
     * Send a request
     * @param request the request to send
     * @return a {@link CompletionStage} that completes when the answer to the request
     * is received or the request timed out
     */
    <A> CompletionStage<ReceiptSlip<A>> sendRequest(Request<A> request);


}
