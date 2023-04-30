package org.xsk.domain.common;

public abstract class Repository<E extends Entity<I>, I extends Id<?>> {

    public abstract E find(I id);

    public abstract void save(E entity);

    protected E findExclusive(I id) {
        throw new UnsupportedOperationException();
    }

    protected void delete(I id) {
        throw new UnsupportedOperationException();
    }

    public E findNotNone(I id) {
        E entity = find(id);
        if (entity == null)
            throw notFoundException();
        return entity;
    }

    protected abstract DomainException notFoundException();
}
