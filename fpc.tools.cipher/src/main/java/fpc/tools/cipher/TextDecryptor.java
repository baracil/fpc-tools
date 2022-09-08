package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import net.femtoparsec.tools.cipher.AESCipherFactory;
import net.femtoparsec.tools.cipher.AESTextDecryptor;

public interface TextDecryptor {

    @NonNull Secret decrypt(@NonNull String encryptedValue);


    static @NonNull TextDecryptor createAES(@NonNull Secret password) {
        return new AESTextDecryptor(new AESCipherFactory(password));
    }

}
