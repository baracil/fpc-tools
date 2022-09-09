package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.CipherException;
import fpc.tools.cipher.CipherHelper;
import fpc.tools.cipher.TextCipher;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.security.GeneralSecurityException;

@RequiredArgsConstructor
public class AESTextCipher implements TextCipher {

    private final @NonNull AESCipherFactory factory;

    @Override
    public @NonNull Secret decrypt(@NonNull String encryptedValue) {
        return CipherException.wrapCall(this::doDecrypt, encryptedValue);
    }

    @Override
    public @NonNull String encrypt(@NonNull Secret secret) {
        return CipherException.wrapCall(this::doEncrypt, secret);
    }

    private @NonNull String doEncrypt(@NonNull Secret secret) throws GeneralSecurityException {
        final var salt = Salt.random(32);
        final var cipher = factory.createForEncryption(salt);

        final var secretBytes = CipherHelper.secret2Bytes(secret);
        final var encrypted = cipher.doFinal(secretBytes);
        final var base64Encrypted = CipherHelper.toBase64(encrypted);

        return salt.prefixValue(base64Encrypted);
    }


    private @NonNull Secret doDecrypt(@NonNull String encryptedValue) throws GeneralSecurityException {
        final var saltAndValue = Salt.extractSalt(encryptedValue);
        final var salt = saltAndValue.v1();
        final var value = saltAndValue.v2();
        final var cipher = factory.createForDecryption(salt);

        final var encryptedBytes = CipherHelper.fromBase64(value);
        final var decryptedBytes = cipher.doFinal(encryptedBytes);

        return CipherHelper.bytes2Secret(decryptedBytes);
    }

}
