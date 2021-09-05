package net.femtoparsec.tools.i18n;

import fpc.tools.i18n.Dictionary;
import fpc.tools.i18n.LocalizedString;
import lombok.NonNull;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

public class NopDictionary implements Dictionary {

    @Override
    public @NonNull LocalizedString localizedString(@NonNull String i18nKey) {
        return new ConstantLocalizedString(i18nKey);
    }

    @Override
    public @NonNull LocalizedString localizedString(@NonNull String i18nKey, @NonNull Object... parameters) {
        return new ConstantLocalizedString(i18nKey);
    }

    @Override
    public @NonNull Dictionary withPrefix(@NonNull String i18nPrefix) {
        return this;
    }

    @Override
    public @NonNull ResourceBundle getResourceBundle(@NonNull Locale locale) {
        return new ResourceBundle() {
            @Override
            protected Object handleGetObject(@NonNull String key) {
                return key;
            }

            @NonNull
            @Override
            public Enumeration<String> getKeys() {
                return new Vector<String>().elements();
            }
        };
    }
}
