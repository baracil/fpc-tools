package fpc.tools.hibernate;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class ImmutableScalarStringUserType<T> extends ImmutableUserType {

    private final int SQL_TYPE = Types.VARCHAR;

    private final Class<T> returnedClass;
    private final Function<? super T, ? extends String> toString;
    private final Function<? super String, ? extends T> fromString;

    @Override
    public int[] sqlTypes() {
        return new int[]{SQL_TYPE};
    }

    @Override
    public Class returnedClass() {
        return returnedClass;
    }

    @Override
    public @Nullable Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        final String name = names[0];
        final String value = name == null ? null: rs.getString(name);
        return value == null ? null:fromString.apply(value);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (returnedClass.isInstance(value)) {
            final var str = toString.apply(returnedClass.cast(value));
            st.setString(index,str);
        } else {
            st.setNull(index, SQL_TYPE);
        }

    }
}

