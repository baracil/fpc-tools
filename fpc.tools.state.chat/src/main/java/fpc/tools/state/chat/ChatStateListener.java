package fpc.tools.state.chat;

import fpc.tools.advanced.chat.AdvancedChat;
import lombok.NonNull;

public interface ChatStateListener<M>  {

    void onConnectionStarted();

    void onConnectionFailed(RuntimeException error);

    OnConnectedResult onConnected(AdvancedChat<M> chat, int nbTries) throws InterruptedException;

    void onRetry();

    void onDisconnected();

}
