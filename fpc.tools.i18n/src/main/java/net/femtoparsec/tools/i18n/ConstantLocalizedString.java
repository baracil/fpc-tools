package net.femtoparsec.tools.i18n;

import fpc.tools.i18n.LocalizedString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

/**
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
public class ConstantLocalizedString implements LocalizedString {

    @NonNull
    private final String value;

    @Override
    public @NonNull String getValue(@NonNull Locale locale) {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public @NonNull boolean hasValue(@NonNull Locale locale) {
        return true;
    }
}
