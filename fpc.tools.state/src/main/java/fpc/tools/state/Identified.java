package fpc.tools.state;

import lombok.NonNull;


public interface Identified<I> {

    @NonNull
    I getIdentification();

}
