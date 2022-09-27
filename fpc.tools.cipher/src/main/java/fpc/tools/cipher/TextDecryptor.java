package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import net.femtoparsec.tools.cipher.AESCipherFactory;
import net.femtoparsec.tools.cipher.AESTextCipher;
import net.femtoparsec.tools.cipher.RSATextDecryptor;

import java.security.PrivateKey;

public interface TextDecryptor {

    @NonNull Secret decrypt(@NonNull String encryptedValue);

    static @NonNull TextDecryptor createAES(@NonNull Secret password) {
        return new AESTextCipher(new AESCipherFactory(password));
    }

    static @NonNull TextDecryptor createRSA(@NonNull PrivateKey privateKey) {
        return new RSATextDecryptor(privateKey);
    }


    default <T> @NonNull T decrypt(@NonNull Decryptable<T> decryptable) {
        return decryptable.decrypt(this);
    }
}
