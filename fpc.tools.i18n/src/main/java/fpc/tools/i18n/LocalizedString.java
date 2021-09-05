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
    @NonNull
    static LocalizedString constant(@NonNull String value) {
        return new ConstantLocalizedString(value);
    }

    /**
     * @return the value of this localized string in the provided locale
     */
    @NonNull
    String getValue(@NonNull Locale locale);

    /**
     * @return the value of this localized string in the provided locale
     */
    @NonNull
    default String getValue() {
        return getValue(Locale.getDefault());
    }

    @NonNull
    boolean hasValue(@NonNull Locale locale);

    /**
     * @return the value of this localized string in the provided locale
     */
    @NonNull
    default boolean hasValue() {
        return hasValue(Locale.getDefault());
    }


}
