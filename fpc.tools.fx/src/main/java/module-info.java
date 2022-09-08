import fpc.tools.fx.KeyTrackerFactory;
import net.femtoparsec.tools.fx.FPCKeyTrackerFactory;

module fpc.tools.fx {
    requires static lombok;
    requires java.desktop;

    requires fpc.tools.fp;
    requires fpc.tools.i18n;
    requires fpc.tools.validation;
    requires fpc.tools.lang;

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires org.controlsfx.controls;
    requires org.slf4j;

    exports fpc.tools.fx;
    exports fpc.tools.fx.dialog;

    uses fpc.tools.fx.KeyTrackerFactory;
    provides KeyTrackerFactory with FPCKeyTrackerFactory;

}