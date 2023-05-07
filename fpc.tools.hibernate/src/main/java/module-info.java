module fpc.tools.hibernate {
  requires static lombok;
  requires java.desktop;

  requires fpc.tools.annotation;
  requires fpc.tools.lang;

  requires org.hibernate.orm.core;
  requires java.sql;

  exports fpc.tools.hibernate;
}