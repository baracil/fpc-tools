package fpc.tools.lang;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import net.femtoparsec.tools.lang.MultiSubscriptions;

public interface Subscription {

    void unsubscribe();

    @NonNull
    default Subscription then(@NonNull Subscription other) {
        return () -> {
            this.unsubscribe();
            other.unsubscribe();
        };
    }

    @NonNull
    static Subscription multi(@NonNull Subscription... subscriptions) {
        return new MultiSubscriptions(ImmutableList.copyOf(subscriptions));
    }

    @NonNull
    Subscription NONE = new Subscription() {
        @Override
        public void unsubscribe() {}

        @Override
        public @NonNull Subscription then(@NonNull Subscription other) {
            return other;
        }
    };

}
