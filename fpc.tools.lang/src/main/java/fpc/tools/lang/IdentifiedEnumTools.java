package fpc.tools.lang;

import fpc.tools.fp.Function1;

import javax.annotation.Nullable;
import java.util.Optional;

public class IdentifiedEnumTools {


    public static <E extends IdentifiedEnum> Function1<String, E> mapper(Class<E> type) {
        final var values = type.getEnumConstants();
        return s -> getEnum(s, values);
    }


    public static <E extends IdentifiedEnum> E getEnum(String id, Class<E> type) {
        return getEnum(id, type.getEnumConstants());
    }

    public static <E extends IdentifiedEnum> Optional<E> findEnum(String id, Class<E> type) {
        return Optional.ofNullable(findEnum(id, type.getEnumConstants()));
    }


    private static <E extends IdentifiedEnum> E getEnum(String id, E[] values) {
        final var value = findEnum(id, values);
        if (value != null) {
            return value;
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("Could not convert '" + id + "' to an enum");
        }
        throw new IllegalArgumentException("Could not convert '" + id + "' to a " + values[0].getClass());
    }

    public static <E extends IdentifiedEnum> @Nullable E findEnum(String id, E[] values) {
        for (E value : values) {
            if (value.getIdentification().equals(id)) {
                return value;
            }
        }
        return null;
    }

//    public static <E extends IdentifiedEnum> JsonDeserializer<E> createDeserializer(Class<E> enumType) {
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
//    public static JsonSerializer<IdentifiedEnum> createSerializer() {
//        return new JsonSerializer<>() {
//            @Override
//            public void serialize(IdentifiedEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//                gen.writeString(value.getIdentification());
//            }
//        };
//    }
}
