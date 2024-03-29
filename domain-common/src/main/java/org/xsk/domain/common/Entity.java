package org.xsk.domain.common;

import lombok.EqualsAndHashCode;

import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class Entity<T extends Id<?>> extends AggregateComponent {
    protected Long createAt;
    protected Long modifiedAt;

    protected Entity() {
        this(2);
    }

    protected Entity(Integer metaDataCap) {
        super(new ConcurrentHashMap<>(metaDataCap));
    }

    @EqualsAndHashCode.Include
    public abstract T id();

    void markAsCreate() {
        //may be auto in jpa
        createAt = System.currentTimeMillis();
    }

    public Long createAt() {
        return createAt;
    }

    void markAsModified() {
        //may be auto in jpa
        modifiedAt = System.currentTimeMillis();
    }

    public Long modifiedAt() {
        return modifiedAt;
    }

    protected Boolean isNew() {
        T id = id();
        if (id instanceof UniqueKey) {
            return ((UniqueKey<?>) id).isHolderNew;
        } else {
            return id == null;
        }
    }

    protected void markAsNotNew() {
        T id = id();
        if (id instanceof UniqueKey) {
            ((UniqueKey<?>) id).isHolderNew = false;
        }
    }

}
