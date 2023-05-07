package net.femtoparsec.tools.i18n;

import fpc.tools.i18n.LocalizedString;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;

/**
 * @author Bastien Aracil
 */
@Slf4j
public class LocalizedStringFromResource implements LocalizedString {

    @Getter
    private final ResourceReference resourceReference;

    private final Function<? super Locale, ? extends ResourceBundle> resourceBundleProviders;

    private final ParametersTranslator parametersTranslator;

    public LocalizedStringFromResource(
            Function<? super Locale,? extends ResourceBundle> resourceBundleProviders,
            ResourceReference resourceReference,
            List<Object> parameters) {
        this.resourceBundleProviders = resourceBundleProviders;
        this.resourceReference = resourceReference;
        this.parametersTranslator = ParametersTranslator.create(parameters);
    }

    public String getValue(Locale locale) {
        return findValue(locale).orElseGet(() -> resourceReference.getNotFoundValuePlaceholder(locale));
    }

    private Optional<String> findValue(Locale locale) {
        try {
            final ResourceBundle resourceBundle = resourceBundleProviders.apply(locale);
            if (resourceBundle != null && resourceBundle.containsKey(resourceReference.getI18nKey())) {
                final String value = String.valueOf(resourceBundle.getObject(resourceReference.getI18nKey()));
                final String message = applyArguments(value, locale);
                return Optional.of(message);
            }
            return Optional.empty();
        } catch (MissingResourceException msr) {
            return Optional.empty();
        }
    }

    @Override
    public boolean hasValue(Locale locale) {
        return findValue(locale).isPresent();
    }

    private String applyArguments(String message, Locale locale) {
        final Object[] parameters = parametersTranslator.translate(locale);
        if (parameters.length == 0) {
            return message;
        }
        try {
            return String.format(message, parameters);
        } catch (IllegalFormatException e) {
            return resourceReference.getErrorPlaceholder(locale,e);
        }
    }

    @Override
    public String toString() {
        return getValue();
    }


}
