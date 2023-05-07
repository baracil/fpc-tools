package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.event.ReceivedMessage;
import fpc.tools.lang.LoopAction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

/**
 * @author Bastien Aracil
 **/
@Slf4j
@RequiredArgsConstructor
public class ChatReceiver<M> implements LoopAction {

    private final Predicate<? super M> shouldPerformMatching;

    private final BlockingDeque<RequestPostData<?,M>> requestPostData;

    private final BlockingDeque<ReceivedMessage<M>> incomingMessages = new LinkedBlockingDeque<>();

    private final List<RequestPostData<?,M>> pending = new LinkedList<>();

    @Override
    public NextState performOneIteration() throws Exception {
        final ReceivedMessage<M> reception = incomingMessages.takeFirst();

        final M message = reception.getMessage();
        if (shouldPerformMatching.test(message)) {
            performRendezvousWithRequests(message,reception.getReceptionTime());
        }

        return NextState.CONTINUE;
    }

    @Override
    public boolean shouldStopOnError(Throwable error) {
        return false;
    }

    private void performRendezvousWithRequests(M message, Instant receptionTime) {
        this.preparePendingRequests();
        for (RequestPostData<?,M> postData : pending) {
            if (postData.tryToCompleteWith(message, receptionTime)) {
                return;
            }
        }
        LOG.debug("Message without request : {}", message);
    }

    private void preparePendingRequests() {
        requestPostData.drainTo(pending);
        pending.removeIf(RequestPostData::isCompleted);
    }

    public void onMessageReception(ReceivedMessage<M> message) {
        incomingMessages.add(message);
    }

}
