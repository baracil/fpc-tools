package fpc.tools.fx.dialog;

import fpc.tools.i18n.Dictionary;
import fpc.tools.i18n.LocalizedString;
import lombok.Getter;

public class AlertInfo {

    private final Throwable error;

    @Getter
    private final String msgI18nKey;

    @Getter
    private final Object[] parameters;

    public AlertInfo(Throwable error, String msgI18nKey) {
        this.error = error;
        this.msgI18nKey = msgI18nKey;
        this.parameters = new Object[0];
    }

    public AlertInfo(Throwable error, String msgI18nKey, Object... parameters) {
        this.error = error;
        this.msgI18nKey = msgI18nKey;
        this.parameters = parameters;
    }

    public Throwable getError() {
        return error;
    }

    public LocalizedString getMessage(Dictionary dictionary) {
        if (parameters.length == 0) {
            return dictionary.localizedString(msgI18nKey);
        }
        return dictionary.localizedString(msgI18nKey,parameters);
    }

}
