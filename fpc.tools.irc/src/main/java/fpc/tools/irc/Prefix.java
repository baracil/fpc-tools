package fpc.tools.irc;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@Value
@Builder(builderClassName = "Builder")
public class Prefix {

    String nickOrServerName;

    @Nullable String user;

    @Nullable String host;

    public Optional<String> user() {
        return Optional.ofNullable(user);
    }

    public Optional<String> host() {
        return Optional.ofNullable(host);
    }
}
