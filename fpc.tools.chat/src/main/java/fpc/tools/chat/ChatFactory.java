package fpc.tools.chat;

import fpc.tools.lang.Instants;

import java.net.URI;

public interface ChatFactory {

    Chat create(URI address,
                ReconnectionPolicy reconnectionPolicy,
                Instants instants);

}
