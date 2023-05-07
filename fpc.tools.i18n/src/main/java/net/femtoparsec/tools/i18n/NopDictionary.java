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
    public LocalizedString localizedString(String i18nKey) {
        return new ConstantLocalizedString(i18nKey);
    }

    @Override
    public LocalizedString localizedString(String i18nKey, Object... parameters) {
        return new ConstantLocalizedString(i18nKey);
    }

    @Override
    public Dictionary withPrefix(String i18nPrefix) {
        return this;
    }

    @Override
    public ResourceBundle getResourceBundle(Locale locale) {
        return new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return key;
            }

            @Override
            public Enumeration<String> getKeys() {
                return new Vector<String>().elements();
            }
        };
    }
}
