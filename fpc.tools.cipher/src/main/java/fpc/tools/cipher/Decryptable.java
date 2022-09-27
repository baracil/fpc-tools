package fpc.tools.cipher;

import lombok.NonNull;

public interface Decryptable<T> {

    @NonNull T decrypt(@NonNull TextDecryptor textDecryptor);
}
