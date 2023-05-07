package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import net.femtoparsec.tools.cipher.AESCipherFactory;
import net.femtoparsec.tools.cipher.AESTextCipher;
import net.femtoparsec.tools.cipher.RSATextDecryptor;

import java.security.PrivateKey;

public interface TextDecryptor {

    Secret decrypt(String encryptedValue);

    static TextDecryptor createAES(Secret password) {
        return new AESTextCipher(new AESCipherFactory(password));
    }

    static TextDecryptor createRSA(PrivateKey privateKey) {
        return new RSATextDecryptor(privateKey);
    }


    default <T> T decrypt(Decryptable<T> decryptable) {
        return decryptable.decrypt(this);
    }
}
