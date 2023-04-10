import fpc.tools.validation.ValidationFactory;
import net.femtoparsec.tools.validation.FPCValidation;

module fpc.tools.validation {
    requires static lombok;
    requires java.desktop;

    requires fpc.tools.fp;
    requires fpc.tools.lang;

    exports fpc.tools.validation;
    exports net.femtoparsec.tools.validation;

    uses fpc.tools.validation.ValidationFactory;

    provides ValidationFactory with FPCValidation;

}