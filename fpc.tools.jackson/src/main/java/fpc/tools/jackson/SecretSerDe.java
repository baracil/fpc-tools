package fpc.tools.jackson;

import fpc.tools.lang.Secret;

public class SecretSerDe extends ScalarStringSerDe<Secret> {

    public SecretSerDe() {
        super(
                Secret.class,
                Secret::value,
                Secret::of
        );
    }
}
