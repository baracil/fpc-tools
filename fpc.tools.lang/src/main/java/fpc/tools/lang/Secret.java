package fpc.tools.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Secret {

    public static @NonNull Secret of(@NonNull String value) {
        return new Secret(value);
    }

    private final @NonNull String value;

    public @NonNull String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Secret{*******}";
    }
}
