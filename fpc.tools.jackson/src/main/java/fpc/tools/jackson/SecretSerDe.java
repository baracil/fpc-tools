package fpc.tools.jackson;

import fpc.tools.lang.Secret;

import java.util.Base64;

public class SecretSerDe extends ScalarStringSerDe<Secret> {

    public SecretSerDe() {
        super(
                Secret.class,
                s  -> Base64.getEncoder().encodeToString(s.bytes()),
                s -> new Secret(Base64.getDecoder().decode(s))
        );
    }
}
