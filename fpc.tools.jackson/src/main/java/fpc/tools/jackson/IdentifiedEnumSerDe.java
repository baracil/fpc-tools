package fpc.tools.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fpc.tools.lang.IdentifiedEnum;
import fpc.tools.lang.IdentifiedEnumTools;
import fpc.tools.lang.IdentifiedEnumWithAlternateIdentifications;
import lombok.NonNull;

import java.io.IOException;

public class IdentifiedEnumSerDe {

    public static <E extends IdentifiedEnum> void addToModule(@NonNull SimpleModule simpleModule, @NonNull Class<E> enumType) {
        simpleModule.addSerializer(enumType,createSerializer());
        simpleModule.addDeserializer(enumType,createDeserializer(enumType));
    }

    public static <E extends IdentifiedEnum> @NonNull JsonDeserializer<E> createDeserializer(@NonNull Class<E> enumType) {
        return new JsonDeserializer<E>() {
            @Override
            public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                final var values = enumType.getEnumConstants();
                final var toDeserialize = p.getValueAsString();
                {
                    final var value = IdentifiedEnumTools.findEnum(toDeserialize, values);
                    if (value != null) {
                        return value;
                    }
                }

                if (IdentifiedEnumWithAlternateIdentifications.class.isAssignableFrom(enumType)) {
                    for (E value : values) {
                        final var id = ((IdentifiedEnumWithAlternateIdentifications) value).getAlternateIdentifications();
                        if (id.contains(toDeserialize)) {
                            return value;
                        }
                    }
                }

                throw new IllegalArgumentException("Could not convert '" + toDeserialize + "' to an enum " + enumType);
            }
        };
    }

    public static @NonNull JsonSerializer<IdentifiedEnum> createSerializer() {
        return new JsonSerializer<>() {
            @Override
            public void serialize(IdentifiedEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.getIdentification());
            }
        };
    }

}
