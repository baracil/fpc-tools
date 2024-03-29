package net.femtoparsec.tools.i18n;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.spi.ResourceBundleProvider;

@RequiredArgsConstructor
public class FPCResourceBundle extends ResourceBundle {



    public static FPCResourceBundle create(ResourceBundleProvider bundleProvider, String resourceBasename, Locale locale, Locale fallbackLocale) {
        final ResourceBundle mainBundle = bundleProvider.getBundle(resourceBasename,locale);
        final ResourceBundle fallbackBundle = bundleProvider.getBundle(resourceBasename,fallbackLocale);
        return new FPCResourceBundle(resourceBasename,locale, mainBundle,fallbackBundle);
    }

    private final String resourceBasename;

    private final Locale mainLocale;

    private final ResourceBundle mainBundle;

    private final ResourceBundle fallbackBundle;

    private final boolean noFallback = Boolean.getBoolean("no-i18n-fallback");

    @Override
    public boolean containsKey(String key) {
        return true;
    }

    @Override
    protected Object handleGetObject(String key) {
        if (mainBundle != null && mainBundle.containsKey(key)) {
            return mainBundle.getObject(key);
        }
        if (!noFallback && fallbackBundle != null && fallbackBundle.containsKey(key)) {
            return fallbackBundle.getObject(key);
        }

        return new ResourceReference(resourceBasename,key).getNotFoundValuePlaceholder(mainLocale);
    }

    @Override
    public Enumeration<String> getKeys() {
        final Set<String> keys = new HashSet<>();
        keys.addAll(Collections.list(mainBundle.getKeys()));
        keys.addAll(Collections.list(fallbackBundle.getKeys()));
        return Collections.enumeration(keys);
    }
}
