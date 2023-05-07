module fpc.tools.persistence {
    requires static lombok;

    requires org.slf4j;
    requires java.persistence;
  requires fpc.tools.annotation;

  exports fpc.tools.persistence;
}