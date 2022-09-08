module fpc.tools.cipher {
    requires static lombok;
    requires java.desktop;

    requires transitive fpc.tools.lang;

    exports fpc.tools.cipher;

    exports net.femtoparsec.tools.cipher to fpc.tools.test;
}