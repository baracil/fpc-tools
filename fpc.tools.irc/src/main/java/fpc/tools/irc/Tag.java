package fpc.tools.irc;

import lombok.*;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@Value
@Builder(builderClassName = "Builder")
public class Tag {

    boolean client;

    @Getter(AccessLevel.NONE)
    @Nullable String vendor;

    String keyName;

    String value;


    public Optional<String> vendor() {
        return Optional.ofNullable(vendor);
    }
}
