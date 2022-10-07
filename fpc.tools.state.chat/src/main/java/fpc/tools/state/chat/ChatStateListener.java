package fpc.tools.state.chat;

import fpc.tools.advanced.chat.AdvancedChat;
import lombok.NonNull;

public interface ChatStateListener<M>  {

    void onConnectionStarted();

    void onConnectionFailed(@NonNull RuntimeException error);

    @NonNull OnConnectedResult onConnected(@NonNull AdvancedChat<M> chat, int nbTries) throws InterruptedException;

    void onRetry();

    void onDisconnected();

}
