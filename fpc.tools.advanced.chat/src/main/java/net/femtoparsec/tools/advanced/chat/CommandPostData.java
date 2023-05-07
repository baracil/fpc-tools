package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.Command;
import fpc.tools.advanced.chat.DispatchSlip;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
public class CommandPostData<M> extends AbstractPostData<DispatchSlip, Command, M> {

    public CommandPostData(Command message) {
        super(message);
    }

    @Override
    public Optional<RequestPostData<?,M>> asRequestPostData() {
        return Optional.empty();
    }

    @Override
    public void onMessagePosted(Instant dispatchingTime) {
        completeWith(new BasicDispatchSlip(dispatchingTime,message()));
    }

}
