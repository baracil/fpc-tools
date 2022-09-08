module fpc.tools.jackson {
    requires static lombok;
    requires java.desktop;

    requires transitive com.fasterxml.jackson.databind;

    requires transitive fpc.tools.lang;


    exports fpc.tools.jackson;
}