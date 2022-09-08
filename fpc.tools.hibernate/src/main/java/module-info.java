module fpc.tools.hibernate {
    requires static lombok;

    requires org.hibernate.orm.core;
    requires fpc.tools.lang;
    requires java.sql;

    exports fpc.tools.hibernate;
}