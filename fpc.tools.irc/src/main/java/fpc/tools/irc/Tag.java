package fpc.tools.irc;

import lombok.*;

import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@Value
@Builder(builderClassName = "Builder")
public class Tag {

    boolean client;

    @Getter(AccessLevel.NONE)
    String vendor;

    @NonNull String keyName;

    @NonNull String value;


    @NonNull
    public Optional<String> vendor() {
        return Optional.ofNullable(vendor);
    }
}
