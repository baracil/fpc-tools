package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.Message;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Internal structure that add some data to a {@link fpc.tools.advanced.chat.Command}
 *
 * @author Bastien Aracil
 **/
public interface PostData<A, M> {

    /**
     * @return the message to send
     */
    Message message();

    /**
     * @return the completion stage that complete when the post is completed.
     * If an error occurred, this completion stage complete exceptionally. If the message
     * is a command (that does not expect an answer from the chat), the completion stage completes
     * when the message is sent. If the message is a request (that expected an answer from the chat),
     * the completion stage completes when the answer from the chat is received (within the time out limit).
     */
    CompletionStage<A> completionStage();

    /**
     * A {@link PostData} can either be a {@link RequestPostData} or a {@link CommandPostData}, this method is a shortcut to
     * the instanceOf test for the <code>RequestPostData</code> type.
     *
     * @return this in an optionnal as a {@link RequestPostData} if this is a <code>RequestPostData</code>, an empty
     * optional otherwise.
     */
    Optional<RequestPostData<?, M>> asRequestPostData();

    /**
     * Method called by the dispatcher when this has been posted.
     * @param dispatchingTime the time of the post
     */
    void onMessagePosted(Instant dispatchingTime);

    /**
     * Method called by the dispatcher when the post of this has failed.
     * @param t the error that occurred when posting this
     */
    void onMessagePostFailure(Throwable t);

    default String messagePayload(Instant dispatchInstant) {
        return message().payload(dispatchInstant);
    }

}
