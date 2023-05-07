package net.femtoparsec.tools.i18n;

import fpc.tools.i18n.LocalizedString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;

/**
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
public class WithLocalizedStringInParameters implements ParametersTranslator {

    private final List<Object> parameters;

    @Override
    public Object[] translate(Locale locale) {
        return new Translator(locale).translate();
    }

    @RequiredArgsConstructor
    private class Translator {

        private final Locale locale;

        public Object[] translate() {
            return parameters.stream()
                             .map(this::transformObjectParameters)
                             .toArray();
        }

        private Object transformObjectParameters(Object parameter) {
            if (parameter instanceof LocalizedString) {
                return ((LocalizedString) parameter).getValue(locale);
            }
            return parameter;
        }

    }
}
