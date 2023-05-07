package fpc.tools.lang;

import lombok.NonNull;

import java.util.ServiceLoader;

public interface OSVersionFactory {

    OSVersion create();

    static OSVersionFactory getInstance() {
        return OSVersionFactory.Holder.FACTORY;
    }

    class Holder {
        static final OSVersionFactory FACTORY;

        static {
            FACTORY = ServiceLoaderHelper.load(ServiceLoader.load(OSVersionFactory.class));
        }
    }
}
