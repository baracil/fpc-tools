package fpc.tools.lang;

import lombok.NonNull;

import java.util.ServiceLoader;

public interface ListenersFactory {

    @NonNull <L> Listeners<L> create();


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
