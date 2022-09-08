package fpc.tools.lang;

import lombok.NonNull;

import java.nio.charset.StandardCharsets;

public record Secret(byte@NonNull[] bytes) {

    public static @NonNull Secret of(@NonNull String value) {
        return new Secret(value.getBytes(StandardCharsets.UTF_8));
    }

    public @NonNull String asString() {
        return new String(bytes,StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "Secret{*******}";
    }

    public static @NonNull Secret empty() {
        return EMPTY;
    }

    private static final Secret EMPTY = new Secret(new byte[0]);


}
