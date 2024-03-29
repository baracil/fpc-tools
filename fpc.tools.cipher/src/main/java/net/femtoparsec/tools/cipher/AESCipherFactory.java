package net.femtoparsec.tools.cipher;

import fpc.tools.lang.Secret;
import lombok.RequiredArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
public class AESCipherFactory {

    public static final String ALGORITHM = "AES";

    private final Secret password;

    public Cipher createForEncryption(Salt salt) throws GeneralSecurityException {
         return createCipher(Cipher.ENCRYPT_MODE,salt);
    }

    public Cipher createForDecryption(Salt salt) throws GeneralSecurityException {
         return createCipher(Cipher.DECRYPT_MODE,salt);
    }

    private Cipher createCipher(int cipherMode, Salt salt) throws GeneralSecurityException {
        final var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        final var spec = new PBEKeySpec(password.value().toCharArray(), salt.bytes(), 65536, 256);
        final var secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        final var cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(cipherMode,secret);
        return cipher;
    }

}
