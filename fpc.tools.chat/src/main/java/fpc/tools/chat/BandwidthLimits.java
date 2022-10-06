package fpc.tools.chat;

import io.github.bucket4j.ConfigurationBuilder;
import io.github.bucket4j.local.LocalBucketBuilder;
import lombok.NonNull;

public interface BandwidthLimits {


    @NonNull LocalBucketBuilder addLimits(@NonNull LocalBucketBuilder builder);

    ConfigurationBuilder addLimits(@NonNull ConfigurationBuilder builder);


    BandwidthLimits NONE = new BandwidthLimits() {
        @Override
        public @NonNull LocalBucketBuilder addLimits(@NonNull LocalBucketBuilder builder) {
            return builder;
        }

        @Override
        public ConfigurationBuilder addLimits(@NonNull ConfigurationBuilder builder) {
            return builder;
        }
    };

}
