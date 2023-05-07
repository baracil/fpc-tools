package net.femtoparsec.tools.fx;

import fpc.tools.fx.FXDictionary;
import fpc.tools.fx.LocaleProperty;
import fpc.tools.i18n.Dictionary;
import fpc.tools.i18n.LocalizedString;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FPCFXDictionary implements FXDictionary {

    private final Dictionary dictionary;

    private final LocaleProperty localeProperty;

    /**
     * @return the localized string associated with the provided <code>i18nKey</code>
     */
    @Override
    public ObservableStringValue localizedString(String i18nKey) {
        return toObservable(dictionary.localizedString(i18nKey));
    }

    /**
     * @return the localized string with parameters associated with the provided <code>i18nKey</code>
     */
    @Override
    public ObservableStringValue localizedString(String i18nKey, Object... parameters) {
        return toObservable(dictionary.localizedString(i18nKey,parameters));
    }

    private ObservableStringValue toObservable(LocalizedString localizedString) {
        return Bindings.createStringBinding(() -> localizedString.getValue(localeProperty.getValue()), localeProperty);
    }

}
