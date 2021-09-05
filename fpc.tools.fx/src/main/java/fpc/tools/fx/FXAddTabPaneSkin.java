package fpc.tools.fx;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.skin.TabPaneSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import lombok.NonNull;


/**
 * Custom TabPaneSkin with add Tab Button.
 *
 *  The size of the add button can be configured in the CSS file using the "add-button" keyword and the "-fx-pref-width"
 *  and "-fx-pref-height" parameters
 *
 */
public class FXAddTabPaneSkin extends TabPaneSkin {

    private static final double BUTTON_INSET = 1.;


    /**
     * Reference to the FXAddTabPane for this skin.
     */
    private final FXAddTabPane fxAddTabPane;

    /**
     * Reference to the fxAddTabPane Header area.
     */
    private final StackPane headerArea;

    /**
     * Reference to the stackPane that contains the tabs.
     */
    private final StackPane tabHeaderRegion;

    /**
     * Reference to the fxAddTabPane that contain the button down for masked tab.
     */
    private final StackPane controlButtonTab;

    /**
     * the add button used to add new tab.
     */
    private Button addTabButton;

    private ButtonSize buttonSize;

    /**
     * initial tab header region clip used to managed visible part of header region.
     */
    private final Rectangle initialHeadersRegionClip;

    /**
     * custom tab header region clip that consider addButton.
     * Replace the initial region clip in {@link #tabHeaderRegion}.
     */
    private final Rectangle newHeadersRegionClip = new Rectangle();

    private Double tabHeaderRegionInitialInset = Double.NaN;


    public FXAddTabPaneSkin(@NonNull FXAddTabPane fxAddTabPane) {
        super(fxAddTabPane);
        this.fxAddTabPane = fxAddTabPane;
        this.headerArea = (StackPane) fxAddTabPane.lookup(".tab-header-area");
        this.tabHeaderRegion = (StackPane) fxAddTabPane.lookup(".headers-region");
        this.controlButtonTab = (StackPane) fxAddTabPane.lookup(".control-buttons-tab");
        this.initialHeadersRegionClip = (Rectangle) this.tabHeaderRegion.getClip();


        //Replace tabHeaderRegion clip with custom clip
        this.tabHeaderRegion.setClip(this.newHeadersRegionClip);
        this.newHeadersRegionClip.xProperty().bind(Bindings.createDoubleBinding(this::updateNewHeadersRegionClipXProperty, this.initialHeadersRegionClip.xProperty()));
        this.newHeadersRegionClip.yProperty().bind(this.initialHeadersRegionClip.yProperty());
        this.newHeadersRegionClip.heightProperty().bind(this.initialHeadersRegionClip.heightProperty());
        this.newHeadersRegionClip.widthProperty().bind(Bindings.createDoubleBinding(this::updateNewHeadersRegionClipWidth, this.initialHeadersRegionClip.widthProperty()));


        this.createAddButton();
        this.headerArea.getChildren().add(this.addTabButton);
        this.addTabButton.layoutXProperty().bind(Bindings.createDoubleBinding(this::updateAddButtonLayoutX, this.tabHeaderRegion.layoutXProperty(), this.newHeadersRegionClip.widthProperty()));
        this.addTabButton.layoutYProperty().bind(this.tabHeaderRegion.layoutYProperty());
    }

    /**
     * If prefWidth and prefHeight of add button are set in css file, the size of add button is initialized with {@link #computeCSSAddButtonSize()}
     * otherwise it is initialized with {@link #computeDefaultAddButtonSize()}
     *
     * @param x
     * @param y
     * @param w
     * @param h
     */
    @Override
    protected void layoutChildren(final double x, final double y,
                                  final double w, final double h) {
        super.layoutChildren(x, y, w, h);
        if ( this.buttonSize == null ) {
            if(this.addTabButton.getPrefWidth() != -1) {
                this.computeCSSAddButtonSize();
            } else {
                this.tabHeaderRegion.heightProperty().addListener( (changed) -> this.computeDefaultAddButtonSize());
                this.computeDefaultAddButtonSize();
            }
        }
        this.layoutAddButton();
    }

    /**
     * Resize addTabButton by calling {@link Button#resize(double, double) Button.resize()}
     */
    private void layoutAddButton() {
        this.addTabButton.resize(this.buttonSize.getWidth(), this.buttonSize.getHeight());
    }


    /**
     * Create a button which sits in tab pane and allows user to add a new tab.
     * This button can be styled with CSS using ".add-button".
     */
    private void createAddButton(){
        this.addTabButton = new Button();
        this.addTabButton.getStyleClass().add("add-button");
        this.setAddTabButtonOnAction(e -> {
            this.fxAddTabPane.getTabs().add(new Tab("New Tab"));
            this.headerArea.layout();
        });
    }

    public final void setAddTabButtonOnAction(EventHandler<ActionEvent> var1) {
        this.addTabButton.setOnAction(var1);
    }

    /**
     * compute layoutX of addButton.<br/>
     * <br/>
     * For TOP and RIGHT tab pane :<br/>
     *    X layout is placed on the right border of tab-header-region clip.<br/>
     *    Corresponding to tab-header-region layoutX plus tab-header-region-clip width.<br/>
     * <br/>
     * For BOTTOM and LEFT tab pane :<br/>
     *    X layout corresponds to the left border of tab-header-region clip.<br/>
     *    Corresponding to tab-header-region layoutX plus tab-header-region width minus tab-header-region-clip width<br/>
     * <br/>
     * @return layoutX value
     *
     * @see Node#layoutXProperty()
     */
    private Double updateAddButtonLayoutX() {
        if ( this.fxAddTabPane.getSide() == Side.TOP || this.fxAddTabPane.getSide() == Side.RIGHT ) {
            final double buttonInset = this.controlButtonTab.isVisible() ? 3 * BUTTON_INSET : 0.;
            return this.tabHeaderRegion.getLayoutX() + this.newHeadersRegionClip.getWidth() + buttonInset;
        } else {
            final double buttonInset = this.controlButtonTab.isVisible() ? 3 * BUTTON_INSET : BUTTON_INSET;
            return this.tabHeaderRegion.getLayoutX() + this.tabHeaderRegion.getWidth() - this.newHeadersRegionClip.getWidth() - buttonInset;
        }
    }

    /**
     * Compute addButton size with CSS parameters.
     */
    private void computeCSSAddButtonSize() {
        this.buttonSize = new ButtonSize(this.addTabButton.getPrefWidth(), this.addTabButton.getPrefHeight());
        this.updateTabHeaderRegionPadding();
    }

    /**
     * Compute addButton size based on tab-header-Region height.
     */
    private void computeDefaultAddButtonSize() {
        final double tabHeaderRegionHeight = this.tabHeaderRegion.getHeight();
        this.buttonSize = new ButtonSize(tabHeaderRegionHeight, tabHeaderRegionHeight);
        this.updateTabHeaderRegionPadding();
    }

    /**
     * Add an extra padding on tab-header-region based on addButton width.
     *
     */
    private void updateTabHeaderRegionPadding() {
        final Insets inset = this.tabHeaderRegion.getPadding();
        if ( this.tabHeaderRegionInitialInset.isNaN() ) {
            this.tabHeaderRegionInitialInset = inset.getRight();
        }
        final double newInset = this.tabHeaderRegionInitialInset + this.buttonSize.getWidth();
        this.tabHeaderRegion.setPadding(new Insets(inset.getTop(), newInset, inset.getBottom(), inset.getLeft()));
        this.fxAddTabPane.layout();
    }

    /**
     * Compute X position of custom headers region clip ({@link #newHeadersRegionClip}.<br/>
     * <br/>
     * For TOP and RIGHT tab pane :<br/>
     *   X layout is placed on the left border of tab-header-region clip.<br/>
     *   Same as usual tapPane.<br/>
     * <br/>
     * For BOTTOM and LEFT tab pane :<br/>
     *   X layout corresponds to the right border of tab-header-region clip.<br/>
     *   It is necessary to add tab-Header-Region padding (size of add button) to shift the clip in order to have padding
     *   on the button side.
     *   Corresponding to tab-header-region layoutX plus tab-header-region width minus tab-header-region-clip width<br/>
     * <br/>
     * @return X position of new-headers-region-clip
     *
     * @see Rectangle#xProperty()
     */
    private Double updateNewHeadersRegionClipXProperty() {
        if ( this.fxAddTabPane.getSide() == Side.TOP || this.fxAddTabPane.getSide() == Side.RIGHT ) {
            return this.initialHeadersRegionClip.getX();
        } else {
            return this.initialHeadersRegionClip.getX() + this.tabHeaderRegion.getPadding().getRight();
        }
    }

    /**
     * Compute Width of custom headers region clip ({@link #newHeadersRegionClip}.<br/>
     * <br/>
     * For TOP and RIGHT tab pane :<br/>
     *   Width of new-headers-region-clip correspond to initial-headers-region-clip minus tab-headers-region right padding.<br/>
     * <br/>
     * For BOTTOM and LEFT tab pane :<br/>
     *   Same as usual tapPane<br/>
     * <br/>
     * @return Width of new-headers-region-clip
     *
     * @see Rectangle#widthProperty()
     */
    private Double updateNewHeadersRegionClipWidth() {
        if ( this.fxAddTabPane.getSide() == Side.TOP || this.fxAddTabPane.getSide() == Side.RIGHT ) {
            return this.initialHeadersRegionClip.getWidth() - this.tabHeaderRegion.getPadding().getRight();
        } else {
            return this.initialHeadersRegionClip.getWidth();
        }
    }


    /**
     * used to keep the addTabButton size parameters.
     */
    private static class ButtonSize {

        private final double width;

        private final double height;

        ButtonSize(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double getWidth() {
            return this.width;
        }

        public double getHeight() {
            return this.height;
        }
    }
}
