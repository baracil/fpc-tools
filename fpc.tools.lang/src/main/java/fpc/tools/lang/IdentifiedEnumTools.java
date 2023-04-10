package fpc.tools.lang;

import fpc.tools.fp.Function1;
import lombok.NonNull;

import java.util.Optional;

public class IdentifiedEnumTools {


    public static <E extends IdentifiedEnum> Function1<String, E> mapper(@NonNull Class<E> type) {
        final var values = type.getEnumConstants();
        return s -> getEnum(s, values);
    }


    public static <E extends IdentifiedEnum> @NonNull E getEnum(@NonNull String id, @NonNull Class<E> type) {
        return getEnum(id, type.getEnumConstants());
    }

    public static <E extends IdentifiedEnum> @NonNull Optional<E> findEnum(@NonNull String id, @NonNull Class<E> type) {
        return Optional.ofNullable(findEnum(id, type.getEnumConstants()));
    }


    private static <E extends IdentifiedEnum> @NonNull E getEnum(@NonNull String id, @NonNull E[] values) {
        final var value = findEnum(id, values);
        if (value != null) {
            return value;
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("Could not convert '" + id + "' to an enum");
        }
        throw new IllegalArgumentException("Could not convert '" + id + "' to a " + values[0].getClass());
    }

    public static <E extends IdentifiedEnum> E findEnum(@NonNull String id, @NonNull E[] values) {
        for (E value : values) {
            if (value.getIdentification().equals(id)) {
                return value;
            }
        }
        return null;
    }

//    public static <E extends IdentifiedEnum> @NonNull JsonDeserializer<E> createDeserializer(@NonNull Class<E> enumType) {
//        return new JsonDeserializer<E>() {
//            @Override
//            public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//                final var values = enumType.getEnumConstants();
//                final var toDeserialize = p.getValueAsString();
//                {
//                    final var value = IdentifiedEnumTools.findEnum(toDeserialize, values);
//                    if (value != null) {
//                        return value;
//                    }
//                }
//
//                if (IdentifiedEnumWithAlternateIdentification.class.isAssignableFrom(enumType)) {
//                    for (E value : values) {
//                        final var id = ((IdentifiedEnumWithAlternateIdentification) value).getAlternateIdentification();
//                        if (toDeserialize.equals(id)) {
//                            return value;
//                        }
//                    }
//                }
//
//                throw new IllegalArgumentException("Could not convert '" + toDeserialize + "' to an enum " + enumType);
//            }
//        };
//    }
//
//    public static @NonNull JsonSerializer<IdentifiedEnum> createSerializer() {
//        return new JsonSerializer<>() {
//            @Override
//            public void serialize(IdentifiedEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//                gen.writeString(value.getIdentification());
//            }
//        };
//    }
}
