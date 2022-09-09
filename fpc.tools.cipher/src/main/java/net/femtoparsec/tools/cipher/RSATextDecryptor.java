package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.CipherException;
import fpc.tools.cipher.CipherHelper;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

@RequiredArgsConstructor
public class RSATextDecryptor implements TextDecryptor {

    private final @NonNull PrivateKey privateKey;


    @Override
    public @NonNull Secret decrypt(@NonNull String encryptedValue) {
        return CipherException.wrapCall(this::doDecrypt, encryptedValue);
    }

    private @NonNull Secret doDecrypt(@NonNull String encryptedValue) throws GeneralSecurityException {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        final var encrypted = CipherHelper.fromBase64(encryptedValue);
        final var secretBytes = decryptCipher.doFinal(encrypted);

        return CipherHelper.bytes2Secret(secretBytes);

    }

}
