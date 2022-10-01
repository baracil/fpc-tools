package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.Command;
import fpc.tools.advanced.chat.DispatchSlip;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
public class CommandPostData<M> extends AbstractPostData<DispatchSlip, Command, M> {

    public CommandPostData(@NonNull Command message) {
        super(message);
    }

    @Override
    public @NonNull Optional<RequestPostData<?,M>> asRequestPostData() {
        return Optional.empty();
    }

    @Override
    public void onMessagePosted(@NonNull Instant dispatchingTime) {
        completeWith(new BasicDispatchSlip(dispatchingTime,message()));
    }

}
