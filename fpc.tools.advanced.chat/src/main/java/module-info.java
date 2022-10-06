import fpc.tools.advanced.chat.AdvancedChatFactory;
import net.femtoparsec.tools.advanced.chat.FPCAdvancedChatFactory;

module fpc.tools.advanced.chat {
    uses fpc.tools.advanced.chat.AdvancedChatFactory;
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires transitive fpc.tools.chat;
    requires transitive fpc.tools.lang;

    exports fpc.tools.advanced.chat;
    exports fpc.tools.advanced.chat.event;

    provides AdvancedChatFactory with FPCAdvancedChatFactory;
}