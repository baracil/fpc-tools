module fpc.tools.fxmodel {
    requires static lombok;
    requires java.desktop;

    requires fpc.tools.fx;
    requires fpc.tools.state;
    requires fpc.tools.fp;

    requires org.slf4j;
    requires javafx.graphics;
    requires com.google.common;

    exports fpc.tools.fxmodel;
}