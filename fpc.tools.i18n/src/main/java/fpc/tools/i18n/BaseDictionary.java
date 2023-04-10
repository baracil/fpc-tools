package fpc.tools.i18n;

import lombok.NonNull;
import net.femtoparsec.tools.i18n.FPCResourceBundle;
import net.femtoparsec.tools.i18n.LocalizedStringFromResource;
import net.femtoparsec.tools.i18n.PrefixedDictionary;
import net.femtoparsec.tools.i18n.ResourceReference;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.ResourceBundleProvider;

/**
 * @author Bastien Aracil
 */
public class BaseDictionary implements Dictionary {

    @NonNull
    private final ResourceBundleProvider resourceBundleProvider;

    private final String baseResourceName;

    public BaseDictionary(@NonNull ResourceBundleProvider resourceBundleProvider, @NonNull String baseResourceName) {
        this.resourceBundleProvider = resourceBundleProvider;
        this.baseResourceName = baseResourceName;
    }

    public BaseDictionary(@NonNull BaseResourceBundleProvider resourceBundleProvider) {
        this.resourceBundleProvider = resourceBundleProvider;
        this.baseResourceName = getClass().getName();
    }

    @NonNull
    public LocalizedString localizedString(@NonNull String i18nKey) {
        final var resourceReference = new ResourceReference(baseResourceName,i18nKey);
        return new LocalizedStringFromResource(this::getResourceBundle, resourceReference, List.of());
    }

    @NonNull
    public LocalizedString localizedString(@NonNull String i18nKey, @NonNull Object... parameters) {
        final ResourceReference resourceReference = new ResourceReference(baseResourceName,i18nKey);
        return new LocalizedStringFromResource(this::getResourceBundle, resourceReference, List.of(parameters));
    }

    @NonNull
    public Dictionary withPrefix(@NonNull String i18nPrefix) {
        return new PrefixedDictionary(this, i18nPrefix);
    }

    @Override
    public @NonNull ResourceBundle getResourceBundle(@NonNull Locale locale) {
        return FPCResourceBundle.create(resourceBundleProvider,baseResourceName,locale,Locale.ENGLISH);
    }
}
