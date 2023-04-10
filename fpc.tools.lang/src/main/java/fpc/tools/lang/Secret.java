package fpc.tools.lang;

import lombok.NonNull;

import java.nio.charset.Charset;

public record Secret(@NonNull String value) {

    public static @NonNull Secret of(@NonNull String value) {
        return new Secret(value);
    }

    public byte @NonNull [] getBytes(@NonNull Charset charset) {
        return value.getBytes(charset);
    }

    @Override
    public String toString() {
        return "Secret{*******}";
    }

    public static @NonNull Secret empty() {
        return EMPTY;
    }

    private static final Secret EMPTY = new Secret("");


}
