package fpc.tools.cipher;

import lombok.NonNull;

public interface Decryptable<T> {

    T decrypt(TextDecryptor textDecryptor);
}
