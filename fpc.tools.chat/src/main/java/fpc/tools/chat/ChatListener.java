package fpc.tools.chat;

import fpc.tools.chat.event.ChatEvent;

public interface ChatListener {

    void onChatEvent(ChatEvent event);

}
