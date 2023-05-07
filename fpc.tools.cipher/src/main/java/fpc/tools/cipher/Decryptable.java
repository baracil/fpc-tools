package fpc.tools.cipher;

public interface Decryptable<T> {

    T decrypt(TextDecryptor textDecryptor);
}
