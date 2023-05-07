package fpc.tools.i18n;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class I18nEntity {

    @Getter
    private final String i18nKey;

    private final Dictionary dictionary;

    public LocalizedString getLocalizedString() {
        return dictionary.localizedString(getI18nKey());
    }

}
