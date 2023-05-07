package fpc.tools.lang;

import fpc.tools.fp.Function0;

public class SubscriptionHolder {

    private Subscription subscription = Subscription.NONE;

    public void replace(Function0<Subscription> subscriber) {
        subscription.unsubscribe();
        subscription = subscriber.apply();
    }

    public void append(Subscription subscription) {
        this.subscription = this.subscription.then(subscription);
    }

    public void unsubscribe() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
    }
}
