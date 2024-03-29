package net.femtoparsec.tools.irc;

import fpc.tools.irc.Tag;
import fpc.tools.lang.ThrowableTool;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static net.femtoparsec.tools.irc.FPCIRCParser.IRC_MARKER;

/**
 * @author Bastien Aracil
 **/
@Slf4j
public class TagParser {

    public static final String CLIENT_PREFIX = "+";

    private final TagValueUnescaper tagValueUnescaper = new TagValueUnescaper();

    public Optional<Tag> parse(String tagAsString) {
        try {
            return Optional.ofNullable(performParsing(tagAsString));
        } catch (Exception e) {
            LOG.warn(IRC_MARKER, "Fail to parse tag '" + tagAsString + "'", e);
            ThrowableTool.interruptIfCausedByInterruption(e);
            return Optional.empty();
        }
    }

    private @Nullable Tag performParsing(String tagAsString) {
        final String[] token = tagAsString.split("=",2);
        if (token.length != 2 || token[0].isEmpty() || token[1].isBlank()) {
            return null;
        }
        final Tag.Builder builder = Tag.builder();
        final ParsedString key = new ParsedString(token[0]);
        final String value = tagValueUnescaper.unescape(token[1]);

        if (value.isEmpty()) {
            return null;
        }

        builder.value(value);

        if (key.startsWith(CLIENT_PREFIX)) {
            builder.client(true);
            key.moveBy(CLIENT_PREFIX.length());
        } else {
            builder.client(false);
        }

        final int lastSlashIndex = key.lastIndexOf("/");
        if (lastSlashIndex>=0) {
            builder.vendor(key.extractUpTo(lastSlashIndex));
            key.moveBy(1);
        }

        builder.keyName(key.extractToEndOfString());

        return builder.build();
    }
}
