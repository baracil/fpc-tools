package fpc.tools.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.function.Function;

@RequiredArgsConstructor
public class ScalarStringSerDe<S> implements Registrable {

    private final Class<S> type;
    private final Function<? super S, ? extends String> toString;
    private final Function<? super String, ? extends S> fromString;

    public void register(SimpleModule simpleModule) {
        simpleModule.addSerializer(type, new Serializer<>(toString));
        simpleModule.addDeserializer(type, new Deserializer<>(fromString));
    }


    @RequiredArgsConstructor
    public static class Serializer<S> extends JsonSerializer<S> {
        private final Function<? super S, ? extends String> toString;

        @Override
        public void serialize(S value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            final var serialized = toString.apply(value);
            jsonGenerator.writeString(serialized);
        }
    }

    @RequiredArgsConstructor
    public static class Deserializer<S> extends JsonDeserializer<S> {
        private final Function<? super String, ? extends S> fromString;

        @Override
        public S deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            final var value = jsonParser.getValueAsString();
            return fromString.apply(value);
        }
    }
}
