package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.lang.ServiceLoaderHelper;

import java.util.ServiceLoader;

public interface IdentityFactory {

    <R> Identity<R> createIdentity(R initialValue);

    <R> Identity<R> createIdentity(Function1<? super IdentityMutator<R>, ? extends R> initialValueFactory);

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
