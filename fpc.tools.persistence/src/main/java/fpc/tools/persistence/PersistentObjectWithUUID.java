package fpc.tools.persistence;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author Bastien Aracil
 */
@MappedSuperclass
@EqualsAndHashCode(of = {"uuid"},callSuper = false)
public abstract class PersistentObjectWithUUID extends SimplePersistentObject {

    private static final long serialVersionUID = 1L;

    @Column(name= "EXTERNAL_ID", nullable = false, unique = true)
    protected UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public PersistentObjectWithUUID() {
    }

    public PersistentObjectWithUUID(UUID uuid) {
        this.uuid = uuid;
    }

    protected void setUuid(UUID uuid) {
        this.uuid = uuid;
    }



}
