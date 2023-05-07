package fpc.tools.chat;

import fpc.tools.chat.event.ChatEvent;
import lombok.NonNull;

public interface ChatListener {

    void onChatEvent(ChatEvent event);

}
