package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.advanced.chat.Command;
import fpc.tools.advanced.chat.DispatchSlip;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
public class CommandPostData<M> extends AbstractPostData<DispatchSlip<M>, Command, M> {

    private final @NonNull AdvancedIO<M> advancedChat;

    public CommandPostData(@NonNull AdvancedIO<M> advancedChat, @NonNull Command message) {
        super(message);
        this.advancedChat = advancedChat;
    }

    @Override
    public @NonNull Optional<RequestPostData<?,M>> asRequestPostData() {
        return Optional.empty();
    }

    @Override
    public void onMessagePosted(@NonNull Instant dispatchingTime) {
        completeWith(new BasicDispatchSlip<>(advancedChat, dispatchingTime,message()));
    }

}
