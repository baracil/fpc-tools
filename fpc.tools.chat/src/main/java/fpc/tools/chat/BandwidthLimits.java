package fpc.tools.chat;

import io.github.bucket4j.ConfigurationBuilder;
import io.github.bucket4j.local.LocalBucketBuilder;
import lombok.NonNull;

public interface BandwidthLimits {


    LocalBucketBuilder addLimits(LocalBucketBuilder builder);

    ConfigurationBuilder addLimits(ConfigurationBuilder builder);


    BandwidthLimits NONE = new BandwidthLimits() {
        @Override
        public LocalBucketBuilder addLimits(LocalBucketBuilder builder) {
            return builder;
        }

        @Override
        public ConfigurationBuilder addLimits(ConfigurationBuilder builder) {
            return builder;
        }
    };

}
