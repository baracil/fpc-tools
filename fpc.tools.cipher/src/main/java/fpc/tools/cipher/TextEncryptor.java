package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import net.femtoparsec.tools.cipher.AESCipherFactory;
import net.femtoparsec.tools.cipher.AESTextEncryptor;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;

public interface TextEncryptor {

    @NonNull String encrypt(@NonNull Secret value);

    static @NonNull TextEncryptor createAES(@NonNull Secret password) {
        return new AESTextEncryptor(new AESCipherFactory(password));
    }

}
