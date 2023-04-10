package fpc.tools.lang;

import lombok.NonNull;

import java.util.List;
import java.util.ServiceLoader;

public interface ListenersFactory {

    @NonNull <L> Listeners<L> create();

    @NonNull <L> Listeners<L> create(@NonNull List<L> initialListeners);


    static ListenersFactory getInstance() {
        return Holder.FACTORY;
    }

    class Holder {
        static final ListenersFactory FACTORY;

        static {
            FACTORY = ServiceLoaderHelper.load(ServiceLoader.load(ListenersFactory.class));
        }
    }
}
