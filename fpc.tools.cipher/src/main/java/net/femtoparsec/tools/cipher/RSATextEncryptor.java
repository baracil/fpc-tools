package net.femtoparsec.tools.cipher;

import fpc.tools.cipher.CipherException;
import fpc.tools.cipher.CipherHelper;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import lombok.NonNull;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class RSATextEncryptor implements TextEncryptor {

    private final PublicKey publicKey;

    public RSATextEncryptor(byte [] encodedPublicKey) {
        try {
            var keyFactory = KeyFactory.getInstance("RSA");
            var publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            publicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (GeneralSecurityException e) {
            throw new CipherException(e);
        }
    }

    @Override
    public String encrypt(Secret value) {
        return CipherException.wrapCall(this::doEncrypt, value);
    }

    private String doEncrypt(Secret secret) throws GeneralSecurityException {
        final var encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        final var secretBytes = CipherHelper.secret2Bytes(secret);
        final var encrypted = encryptCipher.doFinal(secretBytes);

        return CipherHelper.toBase64(encrypted);
    }

}
