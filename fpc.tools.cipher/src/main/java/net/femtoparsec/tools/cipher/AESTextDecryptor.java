package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.TextDecryptor;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.security.GeneralSecurityException;
import java.util.Base64;

@RequiredArgsConstructor
public class AESTextDecryptor extends BaseTextDecryptor implements TextDecryptor {

    private final @NonNull AESCipherFactory aesCipherFactory;

    @Override
    protected @NonNull Secret doDecrypt(@NonNull String encryptedValue) throws GeneralSecurityException {
        final var saltAndValue = Salt.extractSalt(encryptedValue);
        final var salt = saltAndValue.v1();
        final var value = saltAndValue.v2();

        final var cipher = aesCipherFactory.createForDecryption(salt);

        final var decrypted = cipher.doFinal(Base64.getDecoder().decode(value));

        return new Secret(decrypted);
    }

}
