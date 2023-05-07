package fpc.tools.i18n;

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

    private final ResourceBundleProvider resourceBundleProvider;

    private final String baseResourceName;

    public BaseDictionary(ResourceBundleProvider resourceBundleProvider, String baseResourceName) {
        this.resourceBundleProvider = resourceBundleProvider;
        this.baseResourceName = baseResourceName;
    }

    public BaseDictionary(BaseResourceBundleProvider resourceBundleProvider) {
        this.resourceBundleProvider = resourceBundleProvider;
        this.baseResourceName = getClass().getName();
    }

    public LocalizedString localizedString(String i18nKey) {
        final var resourceReference = new ResourceReference(baseResourceName,i18nKey);
        return new LocalizedStringFromResource(this::getResourceBundle, resourceReference, List.of());
    }

    public LocalizedString localizedString(String i18nKey, Object... parameters) {
        final ResourceReference resourceReference = new ResourceReference(baseResourceName,i18nKey);
        return new LocalizedStringFromResource(this::getResourceBundle, resourceReference, List.of(parameters));
    }

    public Dictionary withPrefix(String i18nPrefix) {
        return new PrefixedDictionary(this, i18nPrefix);
    }

    @Override
    public ResourceBundle getResourceBundle(Locale locale) {
        return FPCResourceBundle.create(resourceBundleProvider,baseResourceName,locale,Locale.ENGLISH);
    }
}
