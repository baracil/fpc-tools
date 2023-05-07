package fpc.tools.irc;

import fpc.tools.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author Bastien Aracil
 **/
public abstract class IRCParserFactory {

    public abstract IRCParser create();

    static IRCParserFactory getInstance() {
        return ServiceLoaderHelper.load(ServiceLoader.load(IRCParserFactory.class));
    }

    static IRCParserFactory getInstance(ModuleLayer moduleLayer) {
        return ServiceLoaderHelper.load(ServiceLoader.load(moduleLayer, IRCParserFactory.class));
    }

}
