package fpc.tools.fx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.NonNull;
import net.femtoparsec.tools.fx.SimpleStyleManager;

public interface StyleManager {

    @NonNull
    static StyleManager simple() {
        return new SimpleStyleManager();
    }

    @NonNull
    Scene addStylable(@NonNull Scene scene);

    void addStylable(@NonNull Parent parent);

    void applyTheme(@NonNull Theme theme);

    void refresh();

}
