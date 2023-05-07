package net.femtoparsec.tools.irc;

import fpc.tools.irc.IRCParser;
import fpc.tools.irc.IRCParserFactory;
import fpc.tools.lang.Priority;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 **/
@Priority(Integer.MIN_VALUE)
public class FPCIRCParserFactory extends IRCParserFactory {

    @Override
    public IRCParser create() {
        return new FPCIRCParser();
    }

}
