import fpc.tools.state.IdentityFactory;
import net.femtoparsec.tools.state.FPCIdentityFactory;

module fpc.tools.state {
    requires static lombok;
    requires java.desktop;

    requires fpc.tools.lang;
    requires fpc.tools.fp;

    requires org.apache.logging.log4j;
    requires javafx.base;
    requires com.google.common;
    requires javafx.graphics;

    exports fpc.tools.state;

    uses IdentityFactory;
    provides IdentityFactory with FPCIdentityFactory;

}