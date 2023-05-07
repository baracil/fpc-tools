package fpc.tools.i18n;

import java.util.Locale;

public interface I18nKeyProvider {

    String getI18nKey();

    default I18nEntity toI18nEntity(Dictionary dictionary) {
        return new I18nEntity(this.getI18nKey(),dictionary);
    }

    default LocalizedString toLocalizedString(Dictionary dictionary) {
        return toI18nEntity(dictionary).getLocalizedString();
    }

    default String getLocalizedValue(Dictionary dictionary) {
        return toLocalizedString(dictionary).getValue();
    }

    default String getLocalizedValue(Dictionary dictionary, Locale locale) {
        return toLocalizedString(dictionary).getValue(locale);
    }

}
