package net.femtoparsec.tools.i18n;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public class ResourceReference {

    @Getter
    private final String resourceBasename;

    @Getter
    private final String i18nKey;

    public String getNotFoundValuePlaceholder(Locale locale) {
        return "??"+getResourceReference(locale)+"??";
    }

    public String getErrorPlaceholder(Locale locale, Throwable throwable) {
        return "!!"+getResourceReference(locale)+" : "+throwable.getMessage()+"!!";
    }

    private String getResourceReference(Locale locale) {
        return locale+":"+resourceBasename+":"+i18nKey;
    }


}
