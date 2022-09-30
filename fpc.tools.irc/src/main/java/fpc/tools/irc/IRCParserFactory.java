package fpc.tools.irc;

import fpc.tools.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author Bastien Aracil
 **/
public abstract class IRCParserFactory {

    @NonNull
    public abstract IRCParser create();

    @NonNull
    static IRCParserFactory getInstance() {
        return ServiceLoaderHelper.load(ServiceLoader.load(IRCParserFactory.class));
    }

    @NonNull
    static IRCParserFactory getInstance(@NonNull ModuleLayer moduleLayer) {
        return ServiceLoaderHelper.load(ServiceLoader.load(moduleLayer, IRCParserFactory.class));
    }

}
