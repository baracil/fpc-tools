package fpc.tools.fx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.NonNull;
import net.femtoparsec.tools.fx.SimpleStyleManager;

public interface StyleManager {

    static StyleManager simple() {
        return new SimpleStyleManager();
    }

    Scene addStylable(Scene scene);

    void addStylable(Parent parent);

    void applyTheme(Theme theme);

    void refresh();

}
