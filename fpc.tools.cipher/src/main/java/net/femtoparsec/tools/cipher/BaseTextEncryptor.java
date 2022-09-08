package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.CipherException;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

import java.security.GeneralSecurityException;

@RequiredArgsConstructor
public abstract class BaseTextEncryptor implements TextEncryptor {

    @Override
    @Synchronized
    public @NonNull String encrypt(@NonNull Secret secret) {
        try {
            return doEncrypt(secret);
        } catch (GeneralSecurityException e) {
            throw new CipherException(e);
        }
    }

    protected abstract @NonNull String doEncrypt(@NonNull Secret secret) throws GeneralSecurityException;


}
