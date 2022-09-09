package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.TextCipher;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompositeTextCipher implements TextCipher {

    private final @NonNull TextDecryptor textDecryptor;
    private final @NonNull TextEncryptor textEncryptor;

    @Override
    public @NonNull Secret decrypt(@NonNull String encryptedValue) {
        return textDecryptor.decrypt(encryptedValue);
    }

    @Override
    public @NonNull String encrypt(@NonNull Secret value) {
        return textEncryptor.encrypt(value);
    }
}
