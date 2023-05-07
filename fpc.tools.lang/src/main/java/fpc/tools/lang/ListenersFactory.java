package fpc.tools.lang;

import java.util.List;
import java.util.ServiceLoader;

public interface ListenersFactory {

    <L> Listeners<L> create();

    <L> Listeners<L> create(List<L> initialListeners);


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
