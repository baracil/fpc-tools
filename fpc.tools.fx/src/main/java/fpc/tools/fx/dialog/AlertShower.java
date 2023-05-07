package fpc.tools.fx.dialog;

import fpc.tools.i18n.Dictionary;
import net.femtoparsec.tools.fx.FPCAlertShower;

/**
 * @author Bastien Aracil
 */
public interface AlertShower {

    static AlertShower create(Dictionary dictionary) {
        return new FPCAlertShower(dictionary);
    }

    void showAlert(AlertInfo parameters);

    void showAlertAndWait(AlertInfo parameters);

}
