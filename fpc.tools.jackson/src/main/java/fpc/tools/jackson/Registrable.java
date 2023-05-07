package fpc.tools.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

public interface Registrable {

    void register(SimpleModule simpleModule);
}
