package fpc.tools.advanced.chat;

import lombok.NonNull;

/**
 * @author Bastien Aracil
 **/
public interface Request<A> extends Message {

    /**
     * @return the type of the answer of this request.
     */
    @NonNull
    Class<A> getAnswerType();
}
