package net.femtoparsec.tools.fx;

import fpc.tools.fx.dialog.AlertInfo;
import fpc.tools.fx.dialog.AlertShower;
import fpc.tools.i18n.Dictionary;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.PrintWriter;
import java.io.StringWriter;

@RequiredArgsConstructor
public class FPCAlertShower implements AlertShower {

    private final Dictionary dictionary;


    public void showAlert(AlertInfo alertInfo) {
        prepare(alertInfo).show();
    }

    public void showAlertAndWait(AlertInfo alertInfo) {
        prepare(alertInfo).showAndWait();
    }

    private Alert prepare(AlertInfo alertInfo) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(dictionary.value("error-dialog.title"));
        alert.setHeaderText(dictionary.value("error-dialog.header-text"));
        alert.setContentText(alertInfo.getMessage(dictionary).getValue());

        final var stacktrace = this.prepareStacktrace(alertInfo.getError());
        alert.getDialogPane().setExpandableContent(stacktrace);
        return alert;
    }

    private Node prepareStacktrace(Throwable throwable) {

        final String exceptionText = printStackTrace(throwable);
        Label label = new Label(dictionary.value("error-dialog.stacktrace-label"));

        final TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        return expContent;
    }

    private String printStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
