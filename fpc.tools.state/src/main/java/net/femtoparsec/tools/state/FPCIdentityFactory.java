package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.lang.Disposer;
import fpc.tools.lang.Priority;
import fpc.tools.state.Identity;
import fpc.tools.state.IdentityFactory;
import fpc.tools.state.IdentityMutator;
import fpc.tools.state.ProxyIdentity;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Priority(Integer.MIN_VALUE)
public class FPCIdentityFactory implements IdentityFactory {

    private static final Disposer DISPOSER = new Disposer("Identity");

    @Override
    public <R> Identity<R> createIdentity(R initialValue) {
        final Identity<R> proxyIdentity;
        {
            final FPCIdentity<R> identity = new FPCIdentity<>(initialValue);
            identity.start();
            proxyIdentity = new ProxyIdentity<>(identity);
            DISPOSER.add(proxyIdentity, identity::stop);
        }
        return proxyIdentity;
    }

    @Override
    public <R> Identity<R> createIdentity(Function1<? super IdentityMutator<R>, ? extends R> initialValueFactory) {
        final ProxyIdentity<R> proxyIdentity;
        {
            final var holder = new AtomicReference<FPCIdentity<R>>();

            proxyIdentity = new ProxyIdentity<>(p -> {
                final var identity = new FPCIdentity<R>(initialValueFactory.apply(p));
                identity.start();
                holder.set(identity);
                return identity;
            });
            final var identity = Objects.requireNonNull(holder.get());
            DISPOSER.add(proxyIdentity, identity::stop);
        }
        return proxyIdentity;
    }

}
