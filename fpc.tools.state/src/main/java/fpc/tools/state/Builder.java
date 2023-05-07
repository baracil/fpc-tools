package fpc.tools.state;

public interface Builder<S,B> {

    B toBuilder(S state);

    S build(B builder);
}
