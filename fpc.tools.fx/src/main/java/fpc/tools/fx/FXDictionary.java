package fpc.tools.fx;

import javafx.beans.value.ObservableStringValue;
import lombok.NonNull;

public interface FXDictionary {

    /**
     * @return the localized string associated with the provided <code>i18nKey</code>
     */
    ObservableStringValue localizedString(String i18nKey);

    /**
     * @return the localized string with parameters associated with the provided <code>i18nKey</code>
     */
    ObservableStringValue localizedString(String i18nKey, Object... parameters);


}
