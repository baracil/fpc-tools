package fpc.tools.validation;

import fpc.tools.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

public interface ValidationFactory {

    @NonNull
    Validation create();

    static ValidationFactory getInstance() {
        return Holder.FACTORY;
    }

    class Holder {
        static final ValidationFactory FACTORY;

        static {
            FACTORY = ServiceLoaderHelper.load(ServiceLoader.load(ValidationFactory.class));
        }
    }

}
