module fpc.tools.serde {
    requires static lombok;
    requires java.desktop;

    requires transitive fpc.tools.lang;
    requires jakarta.inject;

    requires io.micronaut.core;
    requires io.micronaut.inject;
    requires io.micronaut.serde.serde_api;

    exports fpc.tools.serde;
}