package net.femtoparsec.tools.i18n;

import com.google.common.collect.ImmutableList;
import fpc.tools.i18n.LocalizedString;
import lombok.NonNull;

import java.util.Locale;

/**
 * @author Bastien Aracil
 */
public interface ParametersTranslator {

    Object[] EMPTY = new Object[0];

    @NonNull
    Object[] translate(@NonNull Locale locale);

    @NonNull
    static ParametersTranslator create(@NonNull ImmutableList<Object> parameters) {
        if (parameters.isEmpty()) {
            return locale ->  EMPTY;
        }
        else if (parameters.stream().anyMatch(p -> p instanceof LocalizedString)) {
            return new WithLocalizedStringInParameters(parameters);
        } else {
            final Object[] result = parameters.toArray(Object[]::new);
            return l -> result;
        }
    }


}
