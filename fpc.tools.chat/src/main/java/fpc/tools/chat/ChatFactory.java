package fpc.tools.chat;

import fpc.tools.lang.Instants;
import fpc.tools.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.net.URI;
import java.util.ServiceLoader;

public interface ChatFactory {

    Chat create(URI address,
                ReconnectionPolicy reconnectionPolicy,
                Instants instants);

    static ChatFactory getInstance() {
        return Holder.INSTANCE;
    }

    class Holder {
        private static final ChatFactory INSTANCE = ServiceLoaderHelper.load(ServiceLoader.load(ChatFactory.class));
    }

}
