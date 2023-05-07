package fpc.tools.serde;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
@Introspected(classes = Secret.class)
public class SecretSerDe implements Serde<Secret> {

    @Override
    public Secret deserialize(Decoder decoder, DecoderContext context, Argument<? super Secret> type) throws IOException {
        final var value = decoder.decodeString();
        return Secret.of(value);
    }

    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends Secret> type, Secret secret) throws IOException {
        encoder.encodeString(secret.value());
    }
}
