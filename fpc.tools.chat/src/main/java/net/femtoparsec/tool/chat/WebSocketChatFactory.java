package net.femtoparsec.tool.chat;

import fpc.tools.chat.Chat;
import fpc.tools.chat.ChatFactory;
import fpc.tools.chat.ReconnectionPolicy;
import fpc.tools.lang.Instants;

import java.net.URI;

public class WebSocketChatFactory implements ChatFactory {

    @Override
    public Chat create(URI address, ReconnectionPolicy reconnectionPolicy, Instants instants) {
        return new WebSocketChat(address, instants);
    }

}
