import fpc.tools.state.IdentityFactory;
import net.femtoparsec.tools.state.FPCIdentityFactory;

module fpc.tools.state {
    requires static lombok;
    requires java.desktop;

    requires transitive fpc.tools.lang;
    requires transitive fpc.tools.fp;

    requires org.slf4j;

    requires transitive javafx.base;
    requires transitive javafx.graphics;
  requires fpc.tools.annotation;

  exports fpc.tools.state;

    uses IdentityFactory;

    provides IdentityFactory with FPCIdentityFactory;

}