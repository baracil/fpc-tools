import fpc.tools.chat.ChatFactory;
import net.femtoparsec.tool.chat.WebSocketChatFactory;

module fpc.tools.chat {
    requires static lombok;
    requires java.desktop;
    requires fpc.tools.lang;
    requires jakarta.websocket;

    exports fpc.tools.chat;
    exports fpc.tools.chat.event;

    provides ChatFactory with WebSocketChatFactory;

}