module fpc.tools.spring {
    uses fpc.tools.spring.SpringModule;
    requires static lombok;
    requires java.desktop;
    requires fpc.tools.lang;
    requires spring.context;
    requires spring.boot;


    requires org.apache.logging.log4j;
    requires spring.beans;

    exports fpc.tools.spring;
}