package fpc.tools.cipher;

import lombok.NonNull;

public interface Encryptable<T> {

    T encrypt(TextEncryptor textEncryptor);
}
