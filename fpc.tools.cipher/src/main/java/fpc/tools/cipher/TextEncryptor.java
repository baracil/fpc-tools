package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import net.femtoparsec.tools.cipher.AESCipherFactory;
import net.femtoparsec.tools.cipher.AESTextCipher;
import net.femtoparsec.tools.cipher.RSATextEncryptor;

public interface TextEncryptor {

    String encrypt(Secret value);

    static TextEncryptor createAES(Secret password) {
        return new AESTextCipher(new AESCipherFactory(password));
    }

    static TextEncryptor createRSA(byte [] encodedPublicKey) {
        return new RSATextEncryptor(encodedPublicKey);
    }

    default <T> T encrypt(Encryptable<T> encryptable) {
        return encryptable.encrypt(this);
    }


}
