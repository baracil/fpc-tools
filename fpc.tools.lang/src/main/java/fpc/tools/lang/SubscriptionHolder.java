package fpc.tools.lang;

import fpc.tools.fp.Function0;
import lombok.NonNull;

public class SubscriptionHolder {

    @NonNull
    private Subscription subscription = Subscription.NONE;

    public void replace(@NonNull Function0<Subscription> subscriber) {
        subscription.unsubscribe();
        subscription = subscriber.f();
    }

    public void append(Subscription subscription) {
        this.subscription = this.subscription.then(subscription);
    }

    public void unsubscribe() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
    }
}
