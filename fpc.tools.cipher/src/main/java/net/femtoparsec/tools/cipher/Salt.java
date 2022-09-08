package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.CipherException;
import fpc.tools.fp.Tuple2;
import lombok.NonNull;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Pattern;

public record Salt(@NonNull String salt) {


    public @NonNull String prefixValue(@NonNull String value) {
        final var s = toString();
        return String.format(":%d:%s%s", s.length(), s, value);
    }

    public byte @NonNull [] bytes() {
        return Base64.getDecoder().decode(salt);
    }

    @Override
    public String toString() {
        return salt;
    }

    public static final Random RANDOM = new SecureRandom();
    private static final Pattern SALT_LENGTH = Pattern.compile(":(?<size>[0-9]+):(?<content>.*)");


    public static @NonNull Tuple2<Salt, String> extractSalt(@NonNull String saltAndValue) {
        final var matcher = SALT_LENGTH.matcher(saltAndValue);
        if (!matcher.matches()) {
            throw new CipherException("Could not extract salt");
        }
        final var size = Integer.parseInt(matcher.group("size"));
        final var content = matcher.group("content");

        if (size <= 0) {
            throw new CipherException("Could not extract salt");
        }

        final var salt = content.substring(0, size);
        final var value = content.substring(size);


        return new Tuple2<>(new Salt(salt), value);
    }


    public static @NonNull Salt random(int length) {
        final var saltBytes = new byte[length];
        RANDOM.nextBytes(saltBytes);
        return new Salt(Base64.getEncoder().encodeToString(saltBytes));
    }

}
