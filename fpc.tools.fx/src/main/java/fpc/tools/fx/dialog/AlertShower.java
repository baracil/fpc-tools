package fpc.tools.fx.dialog;

import fpc.tools.i18n.Dictionary;
import lombok.NonNull;
import net.femtoparsec.tools.fx.FPCAlertShower;

/**
 * @author Bastien Aracil
 */
public interface AlertShower {

    @NonNull
    static AlertShower create(@NonNull Dictionary dictionary) {
        return new FPCAlertShower(dictionary);
    }

    void showAlert(@NonNull AlertInfo parameters);

    void showAlertAndWait(@NonNull AlertInfo parameters);

}
