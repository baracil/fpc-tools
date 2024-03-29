package fpc.tools.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


/**
 * TabPane with add button
 * The size of the add button can be configured in the CSS file using the "add-button" keyword and the "-fx-pref-width"
 * and "-fx-pref-height" parameters
 *
 */
public class FXAddTabPane  extends TabPane {

    private FXAddTabPaneSkin fxAddTabPaneSkin;


    public FXAddTabPane() {
        super();
        this.fxAddTabPaneSkin = new FXAddTabPaneSkin(this);
    }

    public FXAddTabPane(Tab... arg0) {
        super(arg0);
        this.fxAddTabPaneSkin = new FXAddTabPaneSkin(this);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        if ( this.fxAddTabPaneSkin == null ) {
            this.fxAddTabPaneSkin = new FXAddTabPaneSkin(this);
        }
        return this.fxAddTabPaneSkin;
    }

    public final void setAddTabButtonOnAction(EventHandler<ActionEvent> var1) {
        this.fxAddTabPaneSkin.setAddTabButtonOnAction(var1);
    }

}
