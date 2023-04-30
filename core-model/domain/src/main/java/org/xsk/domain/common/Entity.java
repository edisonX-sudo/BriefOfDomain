package org.xsk.domain.common;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Entity<T extends Id<?>> {
    protected Long createAt;
    protected Long modifiedAt;

    @EqualsAndHashCode.Include
    public abstract T id();

    public boolean isNew() {
        return id() == null;
    }

    protected void markAsCreate() {
        createAt = System.currentTimeMillis();
    }

    public Long createAt() {
        return createAt;
    }

    protected void markAsModified() {
        modifiedAt = System.currentTimeMillis();
    }

    public Long modifiedAt() {
        return modifiedAt;
    }
}
