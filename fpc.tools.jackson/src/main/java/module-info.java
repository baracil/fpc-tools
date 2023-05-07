module fpc.tools.jackson {
    requires static lombok;
    requires java.desktop;

    requires transitive com.fasterxml.jackson.databind;

    requires transitive fpc.tools.lang;
  requires fpc.tools.annotation;


  exports fpc.tools.jackson;
}