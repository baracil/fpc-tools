package net.femtoparsec.tools.state;

import fpc.tools.lang.Disposer;
import fpc.tools.lang.Priority;
import fpc.tools.state.Identity;
import fpc.tools.state.IdentityFactory;
import fpc.tools.state.ProxyIdentity;
import lombok.NonNull;

@Priority(Integer.MIN_VALUE)
public class FPCIdentityFactory implements IdentityFactory {

    private static final Disposer DISPOSER = new Disposer("Identity");

    @Override
    public @NonNull <R> Identity<R> createIdentity(@NonNull R initialValue) {
        final Identity<R> proxyIdentity;
        {
            final FPCIdentity<R> identity = new FPCIdentity<>(initialValue);
            identity.start();
            proxyIdentity = new ProxyIdentity<>(identity);
            DISPOSER.add(proxyIdentity, identity::stop);
        }
        return proxyIdentity;
    }

}
