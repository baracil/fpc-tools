package fpc.tools.lang;

import lombok.NonNull;


public class NotImplemented extends FPCException {

    public NotImplemented(String method) {
        super("Not Implemented : "+method);
    }

    public NotImplemented() {
        super("Not Implemented");
    }
}
