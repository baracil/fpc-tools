package fpc.tools.lang;

import lombok.NonNull;

import java.nio.charset.Charset;

public record Secret(String value) {

    public static Secret of(String value) {
        return new Secret(value);
    }

    public byte [] getBytes(Charset charset) {
        return value.getBytes(charset);
    }

    @Override
    public String toString() {
        return "Secret{*******}";
    }

    public static Secret empty() {
        return EMPTY;
    }

    private static final Secret EMPTY = new Secret("");


}
