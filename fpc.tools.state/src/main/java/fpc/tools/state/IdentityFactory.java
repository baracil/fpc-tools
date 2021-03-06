package fpc.tools.state;

import fpc.tools.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

public interface IdentityFactory {

    @NonNull
    <R> Identity<R> createIdentity(@NonNull R initialValue);

    static IdentityFactory getInstance() {
        return Holder.FACTORY;
    }

    class Holder {
        static final IdentityFactory FACTORY;

        static {
            FACTORY = ServiceLoaderHelper.load(ServiceLoader.load(IdentityFactory.class));
        }
    }
}
