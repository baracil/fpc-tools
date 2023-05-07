package fpc.tools.lang;

import fpc.tools.fp.Function1;
import lombok.extern.slf4j.Slf4j;
import net.femtoparsec.tools.lang.BasicCaster;

import java.util.Optional;

@SuppressWarnings("unused")
@Slf4j
public class CastTool {

    public static <T> Caster<T> caster(Class<T> type) {
        return new BasicCaster<>(type);
    }

    public static <T> Optional<T> as(Class<T> type, Object value) {
        return caster(type).cast(value);
    }


    /**
     * Try to convert a string to an integer
     * @param string the string to convert
     * @return an optional contain the conversion result, an empty optional if the conversion could not be done
     */
    public static Optional<Integer> castToInt(String string) {
        return castToNumber(string,Integer::parseInt);
    }

    public static int castToInt(String string, int defaultValue) {
        return castToInt(string).orElse(defaultValue);
    }

    public static Optional<Double> castToDouble(String string) {
        return castToNumber(string,Double::parseDouble);
    }

    public static double castToDouble(String string, double defaultValue) {
        return castToDouble(string).orElse(defaultValue);
    }

    public static Optional<Long> castToLong(String string) {
        return castToNumber(string,Long::parseLong);
    }

    public static double castToLong(String string, long defaultValue) {
        return castToLong(string).orElse(defaultValue);
    }

    private static <N extends Number> Optional<N> castToNumber(String string, Function1<? super String, ? extends N> caster) {
        try {
            return Optional.of(caster.apply(string));
        } catch (NumberFormatException nfe) {
            LOG.warn(String.format("Fail to cast '%s' into number",string));
            return Optional.empty();
        }
    }

}
