package fpc.tools.cipher;

import lombok.NonNull;

public interface Encryptable<T> {

    @NonNull T encrypt(@NonNull TextEncryptor textEncryptor);
}
