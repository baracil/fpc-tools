package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.CipherException;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.security.GeneralSecurityException;

@RequiredArgsConstructor
public abstract class BaseTextDecryptor implements TextDecryptor {
    @Override
    public final @NonNull Secret decrypt(@NonNull String encryptedValue) {
        return CipherException.wrapCall(this::doDecrypt, encryptedValue);
    }

    protected abstract @NonNull Secret doDecrypt(@NonNull String value) throws GeneralSecurityException;

    //    @Override
//    @Synchronized
//    public @NonNull Secret decrypt(@NonNull String value) {
//        try {
//            final var decryptionData = this.createDecryptionData(value);
//            final var decrypted = decryptionData.decrypt();
//            return new Secret(decrypted);
//        } catch (GeneralSecurityException e) {
//            throw new EncryptException(e);
//        }
//    }
//
//    protected abstract @NonNull BaseTextDecryptor.DecryptionData createDecryptionData(@NonNull String value) throws GeneralSecurityException;
//
//    @RequiredArgsConstructor
//    public static class DecryptionData {
//        private final @NonNull Cipher cipher;
//        private final @NonNull String value;
//
//        private byte @NonNull [] decrypt() throws IllegalBlockSizeException, BadPaddingException {
//            final var decoded = Base64.getDecoder().decode(value);
//            return cipher.doFinal(decoded);
//        }
//    }

}
