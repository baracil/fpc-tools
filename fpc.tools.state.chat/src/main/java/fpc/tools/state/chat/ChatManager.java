package fpc.tools.state.chat;

import fpc.tools.advanced.chat.AdvancedChat;
import lombok.NonNull;

public interface ChatManager<M>  {

    void onConnected(@NonNull AdvancedChat<M> chat) throws InterruptedException;

    void onDisconnected();
}
