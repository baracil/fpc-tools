package fpc.tools.persistence;

import javax.persistence.*;
import java.io.Serial;
import java.util.function.Function;

/**
 * @author Bastien Aracil
 */
@MappedSuperclass
public abstract class SimplePersistentObjectWithIdentityId extends PersistentObject<Long> {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String ID_COLUMN_NAME = "ID";

    public static final Function<SimplePersistentObjectWithIdentityId, Long> ID_GETTER = SimplePersistentObjectWithIdentityId::getId;

    /**
     * Id of the entity
     */
    @Id
    @Column(name= ID_COLUMN_NAME, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    protected void setId(Long id) {
        this.id = id;
    }
}
