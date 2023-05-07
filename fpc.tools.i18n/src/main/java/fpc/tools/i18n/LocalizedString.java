package fpc.tools.i18n;

import lombok.NonNull;
import net.femtoparsec.tools.i18n.ConstantLocalizedString;

import java.util.Locale;

/**
 * @author Bastien Aracil
 */
public interface LocalizedString {

    /**
     * @return a localized string that returns the provided value in all locale
     */
    static LocalizedString constant(String value) {
        return new ConstantLocalizedString(value);
    }

    /**
     * @return the value of this localized string in the provided locale
     */
    String getValue(Locale locale);

    /**
     * @return the value of this localized string in the provided locale
     */
    default String getValue() {
        return getValue(Locale.getDefault());
    }

    boolean hasValue(Locale locale);

    /**
     * @return the value of this localized string in the provided locale
     */
    default boolean hasValue() {
        return hasValue(Locale.getDefault());
    }


}
