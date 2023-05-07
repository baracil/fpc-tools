package fpc.tools.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Bastien Aracil
 */
public interface Dictionary {

    ResourceBundle getResourceBundle(Locale locale);

    /**
     * @return the localized string associated with the provided <code>i18nKey</code>
     */
    LocalizedString localizedString(String i18nKey);

    /**
     * @return the localized string with parameters associated with the provided <code>i18nKey</code>
     */
    LocalizedString localizedString(String i18nKey, Object... parameters);

    /**
     * @return the value of the localized string associated with the provided i18nkey in the default Locale
     */
    default String value(String i18nKey) {
        return localizedString(i18nKey).getValue();
    }

    /**
     * @return the value of the localized string with parameters associated with the provided i18nKey in the default Locale
     */
    default String value(String i18nKey, Object... parameters) {
        return localizedString(i18nKey, parameters).getValue();
    }

    /**
     * @return the value of the localized string associated with the provided i18nKey in the provided Locale
     */
    default String value(Locale locale, String i18nKey) {
        return localizedString(i18nKey).getValue(locale);
    }

    /**
     * @return the value of the localized string with parameters associated with the provided i18nKey in the provided Locale
     */
    default String value(Locale locale, String i18nKey, Object... parameters) {
        return localizedString(i18nKey, parameters).getValue(locale);
    }

    Dictionary withPrefix(String i18nPrefix);

}
