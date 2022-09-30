package fpc.tools.chat;

public interface Chat extends ChatIO {

    /**
     * Connect the chat. The method must return only when the connection
     * is complete.
     */
    void connect();

    /**
     * Request the disconnection of the chat. There is no
     * obligation the method returns after that the chat
     * is completely disconnected
     */
    void requestDisconnection();
}
