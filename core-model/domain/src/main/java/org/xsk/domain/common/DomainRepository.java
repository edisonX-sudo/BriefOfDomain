package org.xsk.domain.common;

public abstract class DomainRepository<E extends Entity<I>, I extends Id<?>> {

    public abstract E find(I id);

    public void save(E entity) {
        refreshEntityTs(entity);
        saveInternal(entity);
    }

    protected void refreshEntityTs(E entity) {
        EntityTsRefresher.refreshTs(entity);
    }

    public abstract void saveInternal(E entity);

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
