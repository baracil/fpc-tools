module fpc.tools.serde {
  requires static lombok;
  requires java.desktop;

  requires transitive fpc.tools.lang;
  requires transitive fpc.tools.micronaut;

  requires jakarta.inject;

  requires io.micronaut.core;
  requires io.micronaut.inject;
  requires io.micronaut.serde.serde_api;
  requires fpc.tools.annotation;
  requires jsr305;

  exports fpc.tools.serde;
}