package fpc.tools.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.NonNull;

public interface Registrable {

    void register(@NonNull SimpleModule simpleModule);
}
