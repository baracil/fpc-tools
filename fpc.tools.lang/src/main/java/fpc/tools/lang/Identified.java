package fpc.tools.lang;

import lombok.NonNull;

public interface Identified<I> {

    @NonNull
    I getIdentification();

}
