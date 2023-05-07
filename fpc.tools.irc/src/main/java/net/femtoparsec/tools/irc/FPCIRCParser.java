package net.femtoparsec.tools.irc;

import fpc.tools.irc.*;
import fpc.tools.lang.MapTool;
import lombok.RequiredArgsConstructor;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 **/
public class FPCIRCParser implements IRCParser {

    public static final Marker IRC_MARKER = MarkerFactory.getMarker("IRC_PARSER");

    public static final String TAGS_PREFIX = "@";
    public static final String PREFIX_PREFIX = ":";
    public static final String LAST_PARAMETER_PREFIX = ":";

    private final TagParser tagParser = new TagParser();
    private final PrefixParser prefixParser = new PrefixParser();

    @Override
    public IRCParsing parse(String message) {
        return new Execution(message).parse();
    }

    @RequiredArgsConstructor
    private final class Execution {

        private final String rawMessage;

        private @Nullable Prefix prefix;

        private Map<String, Tag> tags = Map.of();

        private @Nullable String command;

        private @Nullable Params params;

        public IRCParsing parse() {
            final ParsedString parsedString = new ParsedString(rawMessage);

            parsedString.extractToNextSpaceIfStartWith(TAGS_PREFIX).ifPresent(this::parseTags);
            parsedString.extractToNextSpaceIfStartWith(PREFIX_PREFIX).ifPresent(this::parsePrefix);

            parseCommand(parsedString);
            parseParams(parsedString);

            return IRCParsing.builder()
                             .rawMessage(rawMessage)
                             .prefix(prefix)
                             .tags(tags)
                             .params(params)
                             .command(command)
                             .build();
        }


        private void parseTags(String tagsAsString) {
            tags = Stream.of(tagsAsString.split(";"))
                         .map(tagParser::parse)
                         .flatMap(Optional::stream)
                         .filter(t -> !t.getValue().isEmpty())
                         .collect(MapTool.collector(Tag::getKeyName));
        }

        private void parsePrefix(String prefixAsString) {
            prefix = prefixParser.parse(prefixAsString).orElse(null);
        }

        private void parseCommand(ParsedString parsedString) {
            command = parsedString.extractToNextSpaceOrEndOfString();
        }

        private void parseParams(ParsedString parsedString) {
            final Params.Builder builder = Params.builder();

            while (!parsedString.isEmpty()) {
                if (parsedString.startsWith(LAST_PARAMETER_PREFIX)) {
                    builder.parameter(parsedString.moveByStringLength(LAST_PARAMETER_PREFIX).extractToEndOfString());
                } else {
                    builder.parameter(parsedString.extractToNextSpaceOrEndOfString());
                }
            }

            params = builder.build();
        }

    }
}
