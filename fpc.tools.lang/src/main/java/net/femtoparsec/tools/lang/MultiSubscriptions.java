package net.femtoparsec.tools.lang;

import com.google.common.collect.ImmutableList;
import fpc.tools.fp.Consumer0;
import fpc.tools.fp.TryResult;
import fpc.tools.lang.ListTool;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.UnsubscriptionError;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MultiSubscriptions implements Subscription {

    @NonNull
    private final ImmutableList<Subscription> subscriptions;

    @Override
    public void unsubscribe() {
        final ImmutableList<Throwable> errors =
                subscriptions.stream()
                             .map(s -> Consumer0.of(s::unsubscribe))
                             .map(Consumer0::acceptSafe)
                             .map(TryResult::getException)
                             .flatMap(Optional::stream)
                             .collect(ListTool.collector());

        if (!errors.isEmpty()) {
            throw new UnsubscriptionError("Error during unsubscription",errors);
        }
    }
}
