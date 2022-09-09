package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import lombok.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CipherHelper {

    public static @NonNull String toBase64(byte @NonNull [] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte @NonNull [] fromBase64(@NonNull String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    public static @NonNull String bytes2String(byte @NonNull [] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte @NonNull [] string2Bytes(@NonNull String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    public static byte @NonNull [] secret2Bytes(@NonNull Secret secret) {
        return secret.value().getBytes(StandardCharsets.UTF_8);
    }

    public static @NonNull Secret bytes2Secret(byte @NonNull [] bytes) {
        return Secret.of(new String(bytes, StandardCharsets.UTF_8));
    }

}
