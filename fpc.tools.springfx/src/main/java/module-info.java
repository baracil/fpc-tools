module fpc.tools.springfx {
    requires static lombok;
    requires java.desktop;
    requires spring.context;

    requires javafx.graphics;
    requires fpc.tools.spring;
    requires fpc.tools.fx;
    requires fpc.tools.fp;
    requires fpc.tools.i18n;

    requires spring.beans;
    requires spring.boot;

    requires org.slf4j;
    requires fpc.tools.fxmodel;
  requires fpc.tools.annotation;

  exports fpc.tools.springfx;

    opens fpc.tools.springfx to spring.beans, spring.context, spring.core;
}