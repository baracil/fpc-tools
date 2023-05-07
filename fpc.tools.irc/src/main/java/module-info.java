import fpc.tools.irc.IRCParserFactory;
import net.femtoparsec.tools.irc.FPCIRCParserFactory;

module fpc.tools.irc {
    uses IRCParserFactory;

    requires static lombok;
    requires java.desktop;
    requires fpc.tools.lang;
    requires org.slf4j;
  requires fpc.tools.annotation;


  provides IRCParserFactory with FPCIRCParserFactory;

    exports fpc.tools.irc;
}