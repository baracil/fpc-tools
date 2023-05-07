package fpc.tools.fx;

import fpc.tools.i18n.Dictionary;
import javafx.beans.value.ObservableValueBase;
import lombok.NoArgsConstructor;
import net.femtoparsec.tools.fx.FPCFXDictionary;

import java.util.Locale;

@NoArgsConstructor
public class LocaleProperty extends ObservableValueBase<Locale> {

    private Locale value = Locale.getDefault();

    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        value = locale;
        fireValueChangedEvent();
    }

    @Override
    public Locale getValue() {
        return value;
    }

    public FXDictionary wrapDictionary(Dictionary dictionary) {
        return new FPCFXDictionary(dictionary,this);
    }
}
