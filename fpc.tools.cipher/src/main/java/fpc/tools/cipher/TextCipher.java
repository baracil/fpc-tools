package fpc.tools.cipher;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import net.femtoparsec.tools.cipher.CompositeTextCipher;

public interface TextCipher extends TextEncryptor, TextDecryptor {


    static @NonNull TextCipher with(@NonNull TextDecryptor decrypter, @NonNull TextEncryptor encrypter) {
        return new CompositeTextCipher(decrypter,encrypter);
    }

    static @NonNull TextCipher createAES(@NonNull Secret secret) {
        return CompositeTextCipher.createAES(secret);
    }

}
