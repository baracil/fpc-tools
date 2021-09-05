package fpc.tools.lang;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author Bastien Aracil
 */
public class CollectionTool {

    public static <T> Optional<T> getFirst(Collection<T> collection) {
        return collection.stream().findFirst();
    }

    public static <T> Optional<T> getLast(Collection<T> collection) {
        if (collection.isEmpty()) {
            return Optional.empty();
        }
        final int size = collection.size();
        if (collection instanceof List) {
            return Optional.of(((List<T>) collection).get(size-1));
        }
        else {
            final Iterator<T> itr = collection.iterator();
            T value = null;
            while (itr.hasNext()) {
                value = itr.next();
            }
            return Optional.ofNullable(value);
        }
    }
}
