package fpc.tools.cipher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ProxyTextCipher implements TextCipher {

    @Delegate
    private final TextCipher delegate;
}
