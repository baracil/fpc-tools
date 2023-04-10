package fpc.tools.serde;

import fpc.tools.lang.IdentifiedEnum;
import fpc.tools.lang.IdentifiedEnumWithAlternateIdentifications;
import io.micronaut.context.annotation.Primary;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.util.NullableSerde;
import jakarta.inject.Singleton;
import lombok.NonNull;

import java.io.IOException;

@Singleton
@Primary
public class IdentifiedEnumSerde<E extends Enum<E>> implements NullableSerde<E> {


    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends E> type, E value) throws IOException {
        if (value instanceof IdentifiedEnum identifiedEnum) {
            if (identifiedEnum.useNameForSerialization()) {
                encoder.encodeString(identifiedEnum.name());
            } else {
                encoder.encodeString(identifiedEnum.getIdentification());
            }
        } else {
            encoder.encodeString(value.name());
        }
    }

    @Override
    public @NonNull E deserializeNonNull(Decoder decoder, DecoderContext decoderContext, Argument<? super E> type) throws IOException {
        final var str = decoder.decodeString();
        final Class<?> type1 = type.getType();

        final var enumConstants = type1.getEnumConstants();

        for (Object e : enumConstants) {
            if (e instanceof IdentifiedEnum identifiedEnum) {
                if (identifiedEnum.getIdentification().equals(str)) {
                    return (E) e;
                }
            }
            if (e instanceof IdentifiedEnumWithAlternateIdentifications identifiedEnum) {
                if (identifiedEnum.getAlternateIdentifications().contains(str)) {
                    return (E) e;
                }
            }
            if (e instanceof Enum enu) {
                if (enu.name().equals(str)) {
                    return (E)e;
                }
            }
        }
        throw decoder.createDeserializationException("unknown identification '"+str+"'",str);
    }
}
