module fpc.tools.action {
    requires static lombok;
    requires java.desktop;
    requires fpc.tools.fp;
    requires javafx.base;
    requires javafx.controls;
    requires fpc.tools.lang;
    requires fpc.tools.state;

    requires org.apache.logging.log4j;
    requires com.google.common;


    exports fpc.tools.action;
    exports net.femtoparsec.tools.action;
}