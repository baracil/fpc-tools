package fpc.tools.irc;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

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
