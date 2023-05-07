package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import net.femtoparsec.tools.cipher.AESCipherFactory;
import net.femtoparsec.tools.cipher.AESTextCipher;
import net.femtoparsec.tools.cipher.CompositeTextCipher;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public interface TextCipher extends TextEncryptor, TextDecryptor {

    static TextCipher with(TextDecryptor decrypter, TextEncryptor encrypter) {
        return new CompositeTextCipher(decrypter, encrypter);
    }

    static TextCipher createAES(Secret secret) {
        return new AESTextCipher(new AESCipherFactory(secret));
    }

    static KeyPair generateRSAKeyPair() {
        try {
            final var generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (GeneralSecurityException e) {
            throw new CipherException(e);
        }
    }
}
