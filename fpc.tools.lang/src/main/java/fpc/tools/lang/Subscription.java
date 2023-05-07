package fpc.tools.lang;

import net.femtoparsec.tools.lang.MultiSubscriptions;

import java.util.List;

public interface Subscription {

    void unsubscribe();

    default Subscription then(Subscription other) {
        return () -> {
            this.unsubscribe();
            other.unsubscribe();
        };
    }

    static Subscription multi(Subscription... subscriptions) {
        return new MultiSubscriptions(List.of(subscriptions));
    }

    Subscription NONE = new Subscription() {
        @Override
        public void unsubscribe() {}

        @Override
        public Subscription then(Subscription other) {
            return other;
        }
    };

}
