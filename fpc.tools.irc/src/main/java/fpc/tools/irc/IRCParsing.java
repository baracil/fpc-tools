package fpc.tools.irc;

import lombok.*;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 **/
@Value
@Builder(builderClassName = "Builder")
public class IRCParsing {

    /**
     * The original message used by the parser
     */
    String rawMessage;

    /**
     * A map of tags
     */
    Map<String,Tag> tags;

    /**
     * The optional prefix of the IRC message
     */
    @Getter(AccessLevel.NONE)
    @Nullable Prefix prefix;

    /**
     * The command of the IRC message
     */
    String command;

    /**
     * The parameters of the IRC message
     */
    Params params;

    public Optional<Prefix> getPrefix() {
        return Optional.ofNullable(prefix);
    }

    /**
     * @return the last parameter of the IRC message.
     * @throws IndexOutOfBoundsException if there is no such parameter
     */
    public String lastParameter() {
        return params.lastParameter();
    }

    /**
     * The last parameter split in token
     * @param sep
     * @return
     */
    public Stream<String> splitLastParameter(String sep) {
        return Stream.of(lastParameter().split(sep));
    }

    public Optional<String> tagValue(String tagName) {
        return Optional.ofNullable(tags.get(tagName)).map(Tag::getValue);
    }
}
