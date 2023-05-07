package fpc.tools.irc;

import lombok.NonNull;

/**
 * @author Bastien Aracil
 **/
public interface IRCParser {

    static IRCParser create() {
        return IRCParserFactory.getInstance().create();
    }

    static IRCParser createForPlugin() {
        return IRCParserFactory.getInstance(IRCParser.class.getModule().getLayer()).create();
    }

    IRCParsing parse(String message);

}
