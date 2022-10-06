module fpc.tools.state.chat {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires transitive fpc.tools.advanced.chat;
    requires transitive fpc.tools.lang;
    requires transitive fpc.tools.state;


    exports fpc.tools.state.chat;
    exports fpc.tools.state.chat.state;
    exports fpc.tools.state.chat.state.impl;
}