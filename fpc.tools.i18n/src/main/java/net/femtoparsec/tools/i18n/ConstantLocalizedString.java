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

    private final String value;

    @Override
    public String getValue(Locale locale) {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean hasValue(Locale locale) {
        return true;
    }
}
