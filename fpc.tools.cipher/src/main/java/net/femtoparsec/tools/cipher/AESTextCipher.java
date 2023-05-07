package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.CipherException;
import fpc.tools.cipher.CipherHelper;
import fpc.tools.cipher.TextCipher;
import fpc.tools.lang.Secret;
import lombok.RequiredArgsConstructor;

import java.security.GeneralSecurityException;

@RequiredArgsConstructor
public class AESTextCipher implements TextCipher {

    private final AESCipherFactory factory;

    @Override
    public Secret decrypt(String encryptedValue) {
        return CipherException.wrapCall(this::doDecrypt, encryptedValue);
    }

    @Override
    public String encrypt(Secret secret) {
        return CipherException.wrapCall(this::doEncrypt, secret);
    }

    private String doEncrypt(Secret secret) throws GeneralSecurityException {
        final var salt = Salt.random(32);
        final var cipher = factory.createForEncryption(salt);

        final var secretBytes = CipherHelper.secret2Bytes(secret);
        final var encrypted = cipher.doFinal(secretBytes);
        final var base64Encrypted = CipherHelper.toBase64(encrypted);

        return salt.prefixValue(base64Encrypted);
    }


    private Secret doDecrypt(String encryptedValue) throws GeneralSecurityException {
        final var saltAndValue = Salt.extractSalt(encryptedValue);
        final var salt = saltAndValue.v1();
        final var value = saltAndValue.v2();
        final var cipher = factory.createForDecryption(salt);

        final var encryptedBytes = CipherHelper.fromBase64(value);
        final var decryptedBytes = cipher.doFinal(encryptedBytes);

        return CipherHelper.bytes2Secret(decryptedBytes);
    }

}
