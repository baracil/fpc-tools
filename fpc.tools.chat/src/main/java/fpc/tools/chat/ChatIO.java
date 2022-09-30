package fpc.tools.chat;

import fpc.tools.lang.Subscription;

public interface ChatIO {

    void postMessage(String message);

    Subscription addChatListener(ChatListener listener);

    boolean isRunning();
}
