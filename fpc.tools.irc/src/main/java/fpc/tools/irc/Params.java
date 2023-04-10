package fpc.tools.irc;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * @author Bastien Aracil
 **/
@Value
@Builder(builderClassName = "Builder")
public class Params {

    @NonNull
    @Singular
    List<String> parameters;

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    @NonNull
    public String parameterAt(int index) {
        return parameters.get(index);
    }

    @NonNull
    public String lastParameter() {
        return parameterAt(parameters.size()-1);
    }
}
