import fpc.tools.chat.ChatFactory;
import net.femtoparsec.tool.chat.WebSocketChatFactory;

module fpc.tools.chat {
    requires static lombok;
    requires java.desktop;

    requires java.net.http;
    requires org.slf4j;
    requires io.github.bucket4j.core;

    requires com.google.common;

    requires fpc.tools.lang;

    exports fpc.tools.chat;
    exports fpc.tools.chat.event;

    uses ChatFactory;
    provides ChatFactory with WebSocketChatFactory;

}