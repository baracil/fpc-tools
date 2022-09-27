package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import net.femtoparsec.tools.cipher.AESCipherFactory;
import net.femtoparsec.tools.cipher.AESTextCipher;
import net.femtoparsec.tools.cipher.RSATextEncryptor;

public interface TextEncryptor {

    @NonNull String encrypt(@NonNull Secret value);

    static @NonNull TextEncryptor createAES(@NonNull Secret password) {
        return new AESTextCipher(new AESCipherFactory(password));
    }

    static @NonNull TextEncryptor createRSA(byte @NonNull [] encodedPublicKey) {
        return new RSATextEncryptor(encodedPublicKey);
    }

    default <T> @NonNull T encrypt(@NonNull Encryptable<T> encryptable) {
        return encryptable.encrypt(this);
    }


}
