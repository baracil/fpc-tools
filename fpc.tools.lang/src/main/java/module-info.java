import fpc.tools.lang.ListenersFactory;
import fpc.tools.lang.OSVersionFactory;
import net.femtoparsec.tools.lang.FPCListeners;
import net.femtoparsec.tools.lang.FPCOSVersion;

module fpc.tools.lang {
    requires static lombok;
    requires java.desktop;

    requires transitive fpc.tools.fp;

    requires org.slf4j;
  requires fpc.tools.annotation;

  exports fpc.tools.lang;

    uses OSVersionFactory;
    uses ListenersFactory;

    provides OSVersionFactory with FPCOSVersion;
    provides ListenersFactory with FPCListeners;

}