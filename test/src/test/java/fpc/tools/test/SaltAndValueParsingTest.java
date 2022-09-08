package fpc.tools.test;

import lombok.NonNull;
import net.femtoparsec.tools.cipher.Salt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SaltAndValueParsingTest {

    public static Stream<Arguments> saltAndValues() {
        return Stream.of(
                Arguments.of(":12:asabcdefghihASDAS812038asjkdhauiwehy","asabcdefghih","ASDAS812038asjkdhauiwehy"),
                Arguments.of(":2:abHELLO","ab","HELLO"),
                Arguments.of(":1:aHELLO","a","HELLO"),
                Arguments.of(":5:12345HELLO","12345","HELLO")
        );
    }

    @ParameterizedTest
    @MethodSource("saltAndValues")
    public void shouldHaveRightSalt(@NonNull String saltAndValue, @NonNull String salt, @NonNull String value) {
        final var s = Salt.extractSalt(saltAndValue);
        Assertions.assertEquals(salt,s.v1().salt());
    }

    @ParameterizedTest
    @MethodSource("saltAndValues")
    public void shouldHaveRightValue(@NonNull String saltAndValue, @NonNull String salt, @NonNull String value) {
        final var s = Salt.extractSalt(saltAndValue);
        Assertions.assertEquals(value,s.v2());
    }
}
