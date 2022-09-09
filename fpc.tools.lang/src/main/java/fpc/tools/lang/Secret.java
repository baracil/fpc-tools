package fpc.tools.lang;

import lombok.NonNull;

public record Secret(@NonNull String value) {

    public static @NonNull Secret of(@NonNull String value) {
        return new Secret(value);
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
