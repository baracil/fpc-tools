package fpc.tools.cipher;

import fpc.tools.lang.Secret;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CipherHelper {

    public static String toBase64(byte [] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte [] fromBase64(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    public static String bytes2String(byte [] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte [] string2Bytes(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    public static byte [] secret2Bytes(Secret secret) {
        return secret.value().getBytes(StandardCharsets.UTF_8);
    }

    public static Secret bytes2Secret(byte [] bytes) {
        return Secret.of(new String(bytes, StandardCharsets.UTF_8));
    }

}
