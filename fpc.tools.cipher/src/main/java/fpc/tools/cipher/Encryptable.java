package fpc.tools.cipher;

public interface Encryptable<T> {

    T encrypt(TextEncryptor textEncryptor);
}
