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

    @Singular
    List<String> parameters;

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    public String parameterAt(int index) {
        return parameters.get(index);
    }

    public String lastParameter() {
        return parameterAt(parameters.size()-1);
    }
}
