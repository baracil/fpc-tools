package net.femtoparsec.tools.i18n;

import fpc.tools.i18n.Dictionary;
import fpc.tools.i18n.LocalizedString;
import lombok.RequiredArgsConstructor;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
public class PrefixedDictionary implements Dictionary {

    private final Dictionary delegate;

    private final String prefix;

    @Override
    public LocalizedString localizedString(String i18nKey) {
        return delegate.localizedString(prefix + i18nKey);
    }

    @Override
    public LocalizedString localizedString(String i18nKey, Object... parameters) {
        return delegate.localizedString(prefix + i18nKey, parameters);
    }

    @Override
    public Dictionary withPrefix(String i18nPrefix) {
        return new PrefixedDictionary(delegate,prefix+i18nPrefix);
    }

    @Override
    public ResourceBundle getResourceBundle(Locale locale) {
        final ResourceBundle resourceBundle = delegate.getResourceBundle(locale);
        return new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return resourceBundle.getObject(prefix+key);
            }

            @Override
            public Enumeration<String> getKeys() {
                final Enumeration<String> enumeration = resourceBundle.getKeys();
                final Vector<String> vector = new Vector<>();
                while (enumeration.hasMoreElements()) {
                    final String element = enumeration.nextElement();
                    if (element.startsWith(prefix)) {
                        vector.add(element.substring(prefix.length()));
                    }
                }
                return vector.elements();
            }
        };
    }
}
