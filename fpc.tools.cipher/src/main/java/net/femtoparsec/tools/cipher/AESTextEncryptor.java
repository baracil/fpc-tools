package net.femtoparsec.tools.cipher;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@RequiredArgsConstructor
public class AESTextEncryptor extends BaseTextEncryptor {

    private final @NonNull AESCipherFactory factory;


    @Override
    public @NonNull String doEncrypt(@NonNull Secret value) throws GeneralSecurityException {
        final var salt = Salt.random(32);

        final var cipher = factory.createForEncryption(salt);

        final var encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(value.bytes()));

        return salt.prefixValue(encrypted);
    }

}
