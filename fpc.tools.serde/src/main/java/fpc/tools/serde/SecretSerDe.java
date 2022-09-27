package fpc.tools.serde;

import fpc.tools.lang.Secret;
import fpc.tools.micronaut.EagerInit;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.inject.Singleton;
import lombok.NonNull;

import java.io.IOException;

@Singleton
@EagerInit
@SerdeImport(Secret.class)
@Introspected(classes = Secret.class)
public class SecretSerDe implements Serde<Secret> {

    @Override
    public Secret deserialize(@NonNull Decoder decoder, @NonNull DecoderContext context, @NonNull Argument<? super Secret> type) throws IOException {
        final var value = decoder.decodeString();
        return Secret.of(value);
    }

    @Override
    public void serialize(@NonNull Encoder encoder, @NonNull EncoderContext context, @NonNull Argument<? extends Secret> type, @NonNull Secret secret) throws IOException {
        encoder.encodeString(secret.value());
    }
}
