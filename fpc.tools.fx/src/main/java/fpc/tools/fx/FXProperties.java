package fpc.tools.fx;

import javafx.application.HostServices;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FXProperties {

    @Getter
    private final Stage primaryStage;

    @Getter
    private final HostServices hostServices;



}
