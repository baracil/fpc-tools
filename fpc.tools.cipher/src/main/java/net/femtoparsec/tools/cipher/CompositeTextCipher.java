package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.TextCipher;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompositeTextCipher implements TextCipher {

    private final TextDecryptor textDecryptor;
    private final TextEncryptor textEncryptor;

    @Override
    public Secret decrypt(String encryptedValue) {
        return textDecryptor.decrypt(encryptedValue);
    }

    @Override
    public String encrypt(Secret value) {
        return textEncryptor.encrypt(value);
    }
}
